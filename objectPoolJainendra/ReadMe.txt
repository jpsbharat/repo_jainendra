Design and implement a generic pool for storing objects (implementing poolable). The pool should support following:
	1. A support for creational pattern which would be used by the pool to create objects automatically when needed
	2. Maximum and Minimum number of objects which can be created and made available within the pool. Minimum number would be used to make sure that the number of objects are available in the pool, Maximum number would make sure that the pool will create a max of these objects
	3. Automatic clearing of objects not used in the pool, this clearing agent can run at a fixed time interval
	4. Once the object borrowed from the pool is used should support returning back

Class/Interface Definitions:

	public class ObjectPool<T extends Poolable> {
		public ObjectPool( int maxPoolSize, int minPoolSize) {
			...
			...	
		}

		protected void initialize() {
			...
			...
		}

		public T getObject() {
			T obj = null;
			...
			...
			return obj;
		}

		public void shutdown() {
			...
			...
		}
	}
	

	public interface Poolable {
		void close();
	}