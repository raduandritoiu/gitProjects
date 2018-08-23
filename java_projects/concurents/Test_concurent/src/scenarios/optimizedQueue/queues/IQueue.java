package scenarios.optimizedQueue.queues;

public interface IQueue {
	
  public String name();
  public boolean insert(IInsertElem inPkt);
	public void consume();
	public IProcessedElem consumeOne();
	
	public void unlock();
//  public boolean running();
//  public void start();
//  public void stop();
	
	
  public static interface IInsertElem {}
  
	public static interface IQueueElem {}
	
	public static interface IProcessedElem {}
}
