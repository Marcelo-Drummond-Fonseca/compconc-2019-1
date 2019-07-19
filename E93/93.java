public class SimpleReadWriteLock implements ReadWriteLock {
	
	int readers;
	boolean writer;
	Object monitor;
	Lock readLock, writeLock;
	public SimpleReadWriteLock(){
		
		readers=0;
		writer=false;
		monitor=new Object();
		readLock=new ReadLock();
		writeLock=new WriteLock();		
	}
	public Lock readLock(){return readLock;}
	public Lock writeLock(){return writeLock;}
	
	class ReadLock implements Lock {
		
		synchronized(monitor) void lock() {
			try {
				while (writer) {
					monitor.wait();
				}
				readers++;
			}
		}
		
		synchronized(monitor) void unlock(){
			try {
				readers--;
				if (readers == 0){monitor.notifyAll();}
			} catch(InterruptedException exc){System.out.println("Interrupcao")}
		}
	}
	
	class WriteLock implements Lock {
		
		synchronized(monitor) void lock() {
			try {
				while (readers > 0 || writer==true) {
					monitor.wait();
				}
				writer = true;
			} catch(InterruptedException exc){System.out.println("Interrupcao")}
		}
		
		synchronized(monitor) void unlock() {
			writer = false;
			monitor.notifyAll();
		}
	}		
	
}