package queues;

public interface IQueue
{
  boolean offer(Integer e);
  Integer poll();
  Integer peek();
  
  boolean add(Integer e);
  Integer remove();
  Integer element();
  
  int getSize();
  void print();
  void printRevert();
}
