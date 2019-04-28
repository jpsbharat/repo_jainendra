package com.jainendra.objectPool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ObjectPool<T extends Poolable> {
	private int maxPoolSize;
	private int minPoolSize;

	private long crearingPeriod = 0;

	private volatile boolean alive = false;

	private Class<T> clazz = null;

	private Lock poolLock = new ReentrantLock(true);
	private ScheduledExecutorService clearingAgent = null;

	private Map<T, Long> freeObjectsMap = null;
	private Map<T, Long> usedObjectsMap = null;

	private enum ObjectCreator {
		INSTANCE;
		private ObjectCreator() {
		}

		public <T extends Poolable> T createObj(Class<T> clazz)
				throws Exception {
			try {
				return clazz.newInstance();
			} catch (InstantiationException e) {
				throw e;
			} catch (IllegalAccessException e) {
				throw e;
			}
		}
	}

	public ObjectPool(int maxPoolSize, int minPoolSize, Class<T> clazz,
			long crearingPeriod) {
		this.maxPoolSize = maxPoolSize;
		this.minPoolSize = minPoolSize;
		this.crearingPeriod = crearingPeriod;
		this.clazz = clazz;
		start();
	}

	protected void initialize() {
		try {
			this.poolLock.lock();
			this.freeObjectsMap = new HashMap<T, Long>();
			this.usedObjectsMap = new HashMap<T, Long>();
			this.clearingAgent = Executors.newSingleThreadScheduledExecutor();
			this.ensureMinSizeInVariant();
		} finally {
			this.poolLock.unlock();
		}
	}

	public void returnObject(T t) {
		try {
			this.poolLock.lock();
			if (t != null) {
				this.usedObjectsMap.remove(t);
				if (!t.isClosed()) {
					this.freeObjectsMap.put(t, System.currentTimeMillis());
				} else {
					this.freeObjectsMap.remove(t);
				}
				this.ensureMinSizeInVariant();
			}
		} finally {
			this.poolLock.unlock();
		}
	}

	public T getObject() throws Exception {
		try {
			this.poolLock.lock();
			if (this.alive) {
				long currentTime = System.currentTimeMillis();
				T t;
				if (!this.freeObjectsMap.isEmpty()) {
					for (Entry<T, Long> entry : this.freeObjectsMap.entrySet()) {
						t = entry.getKey();
						if (checkObjectStatus(t)) {
							this.freeObjectsMap.remove(t);
							this.usedObjectsMap.put(t, currentTime);
							return t;
						} else {
							if (this.freeObjectsMap.containsKey(t)) {
								this.freeObjectsMap.remove(t);
							} else if (this.usedObjectsMap.containsKey(t)) {
								this.usedObjectsMap.remove(t);
							}
						}
					}
				}

				if (this.getPoolSize() < this.maxPoolSize) {
					t = create();
					this.usedObjectsMap.put(t, currentTime);
					return t;
				} else {
					throw new NoObjectInThePoolException();
				}
			} else {
				throw new PoolShutDownException(
						"Pool is shut down. Please start it again to use.");
			}
		} catch (PoolShutDownException t) {
			throw t;
		} catch (NoObjectInThePoolException t) {
			throw t;
		} catch (Throwable t) {
			throw t;
		} finally {
			this.poolLock.unlock();
		}
	}

	protected T create() {
		try {
			return ObjectCreator.INSTANCE.createObj(this.clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected void kill(T t) {
		try {
			t.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean checkObjectStatus(T t) {
		try {
			return !t.isClosed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void shutdown() {
		try {
			this.poolLock.lock();
			this.freeObjectsMap = null;
			this.usedObjectsMap = null;
			this.alive = false;
			clearingAgent.shutdown();
		} finally {
			this.poolLock.unlock();
		}
	}

	public void start() {
		try {
			this.poolLock.lock();
			this.initialize();
			ScheduledFuture<?> scheduledFuture = this.clearingAgent
					.scheduleAtFixedRate(
							new Runnable() {
								public void run() {
									try {
										if (ObjectPool.this.alive) {

											ObjectPool.this.poolLock.lock();
											System.out
													.println("Clearing Agent is called ");
											int poolSize = ObjectPool.this
													.getPoolSize();
											Iterator<T> it = ObjectPool.this.freeObjectsMap
													.keySet().iterator();
											System.out
													.println("Clearing Agent is going to clear free objects");
											System.out
													.println("In Clearing Agent, current size of the pool is = "
															+ poolSize);
											while (poolSize > ObjectPool.this.maxPoolSize
													&& it.hasNext()) {
												T t = it.next();
												ObjectPool.this.kill(t);
												it.remove();
												System.out
														.println(t
																+ " has been cleared and closed.");
												poolSize--;
											}

											poolSize = ObjectPool.this
													.getPoolSize();
											System.out
													.println("In Clearing Agent, current size of the pool is = "
															+ poolSize);
										}
									} catch (Throwable t) {
										t.printStackTrace();
									} finally {
										ObjectPool.this.poolLock.unlock();
									}
								}
							}, this.crearingPeriod, this.crearingPeriod,
							TimeUnit.MILLISECONDS);
			System.out.println(scheduledFuture);
			this.alive = true;
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			this.poolLock.unlock();
		}
	}

	private void ensureMinSizeInVariant() {
		try {
			this.poolLock.lock();
			int poolSize = getPoolSize();
			while (poolSize < this.minPoolSize) {
				T t = this.create();
				this.freeObjectsMap.put(t, System.currentTimeMillis());
				poolSize++;
			}
		} finally {
			this.poolLock.unlock();
		}
	}

	public int getPoolSize() {
		try {
			this.poolLock.lock();
			if (this.alive) {
				return this.freeObjectsMap.size() + this.usedObjectsMap.size();
			} else {
				return -1;
			}
		} finally {
			this.poolLock.unlock();
		}
	}
}