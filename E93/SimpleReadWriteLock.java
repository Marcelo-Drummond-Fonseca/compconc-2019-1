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
}
