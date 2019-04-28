package com.jainendra.objectPool.test;

import com.jainendra.objectPool.NoObjectInThePoolException;
import com.jainendra.objectPool.ObjectPool;

public class TestPool {
	public static volatile int count = 0;

	public static void main(String[] args) {
		try {
			ObjectPool<SampelPoolableObject> op = new ObjectPool<SampelPoolableObject>(
					12, 5, SampelPoolableObject.class, 50);

			Thread t = createThreadObject("Thread_0", op);
			Thread t1 = createThreadObject("Thread_1", op);
			Thread t2 = createThreadObjectWithoutReturn("Thread_2", op);
			Thread t3 = createThreadObjectWithoutReturn("Thread_3", op);
			Thread t4 = createThreadObjectForClosing("Thread_4", op);

			System.out.println("In thread = " + Thread.currentThread()
					+ ", current size of the pool is = " + op.getPoolSize());
			SampelPoolableObject spo = op.getObject();
			System.out.println(Thread.currentThread()
					+ " is using the object = " + spo);
			System.out.println(Thread.currentThread()
					+ " is using the object = " + spo);
			System.out.println("In thread = " + Thread.currentThread()
					+ ", current size of the pool is = " + op.getPoolSize());

			System.out.println(Thread.currentThread()
					+ " is going to return the object = " + spo
					+ " back to pool");
			System.out.println("In thread = " + Thread.currentThread()
					+ ", current size of the pool is = " + op.getPoolSize());
			op.returnObject(spo);
			System.out.println("In thread = " + Thread.currentThread()
					+ ", current size of the pool is = " + op.getPoolSize());
			System.out.println(Thread.currentThread()
					+ " is has returned the object = " + spo + " back to pool");

			t.start();
			t1.start();
			t2.start();
			t3.start();
			t4.start();

			t.join();
			t1.join();
			t2.join();
			t3.join();
			t4.join();

			op.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Thread createThreadObject(String name,
			final ObjectPool<SampelPoolableObject> op) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (count < 100) {
						try {
							System.out.println("In thread = "
									+ Thread.currentThread()
									+ ", current size of the pool is = "
									+ op.getPoolSize());

							System.out.println(Thread.currentThread()
									+ " is going to get the object from pool");
							SampelPoolableObject spo = op.getObject();
							System.out.println(Thread.currentThread()
									+ " is using the object = " + spo);

							System.out.println(Thread.currentThread()
									+ " is going to return the object = " + spo
									+ " back to pool");
							op.returnObject(spo);
							System.out.println(Thread.currentThread()
									+ " is has returned the object = " + spo
									+ " back to pool");
							System.out.println("In thread = "
									+ Thread.currentThread()
									+ ", current size of the pool is = "
									+ op.getPoolSize());
						} catch (NoObjectInThePoolException t) {
							System.out.println("There is no Object available in the Pool");
						}
						count++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, name);
		return t;
	}

	private static Thread createThreadObjectWithoutReturn(String name,
			final ObjectPool<SampelPoolableObject> op) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (count < 100) {
						try {
							System.out.println("In thread = "
									+ Thread.currentThread()
									+ ", current size of the pool is = "
									+ op.getPoolSize());

							System.out.println(Thread.currentThread()
									+ " is going to get the object from pool");
							SampelPoolableObject spo = op.getObject();
							System.out.println("In thread = "
									+ Thread.currentThread()
									+ ", current size of the pool is = "
									+ op.getPoolSize());
							System.out.println(Thread.currentThread()
									+ " is using the object = " + spo);
						} catch (NoObjectInThePoolException t) {
							System.out.println("There is no Object available in the Pool");
						}
						count++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, name);
		return t;
	}

	private static Thread createThreadObjectForClosing(String name,
			final ObjectPool<SampelPoolableObject> op) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (count < 100) {
						try {
							System.out.println("In thread = "
									+ Thread.currentThread()
									+ ", current size of the pool is = "
									+ op.getPoolSize());

							System.out.println(Thread.currentThread()
									+ " is going to get the object from pool");
							SampelPoolableObject spo = op.getObject();
							System.out.println("In thread = "
									+ Thread.currentThread()
									+ ", current size of the pool is = "
									+ op.getPoolSize());
							System.out.println(Thread.currentThread()
									+ " is using the object = " + spo);

							if (spo != null) {
								spo.setClosed(true);
							}
							count++;
						} catch (NoObjectInThePoolException t) {
							System.out.println("There is no Object available in the Pool");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, name);
		return t;
	}
}
