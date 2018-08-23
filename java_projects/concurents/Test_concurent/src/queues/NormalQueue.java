package queues;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Queue;

public class NormalQueue implements IQueue
{
  private Node head, tail;
  private int size;
  
  public NormalQueue()
  {}
  
  
  public boolean offer(Integer e)
  {
    return add(e);
  }
  
  public Integer poll()
  {
    return remove();
  }
  
  public Integer peek()
  {
    return element();
  }
  
  
  public boolean add(Integer e)
  {
    Node node = new Node(e);
    if (head == null && tail == null)
    {
      head = node;
      tail = node;
    }
    else if (head == null || tail == null)
    {
      // wtf???
      return false;
    }
    else
    {
      node.prev = tail;
      tail.next = node;
      tail = node;
    }
    
    size ++;
    return true;
  }
  
  public Integer remove()
  {
    if (head == null) 
      return null;
    
    size --;
    
    Node tmp = head;
    if (head == tail)
    {
      head = null;
      tail = null;
    }
    else
    {
      head.next.prev = null;
      head = head.next;
      tmp.next = null;
    }
    return tmp.val;
  }
  
  public Integer element()
  {
    if (head != null)
      return head.val;
    return null;
  }
  
  
  public int getSize()
  {
    return size;
  }
  
  
  public void print()
  {
    System.out.print("Print: ");
    for (Node n = head; n != null; n = n.next)
    {
      System.out.print(n.val + ", ");
    }
    System.out.println("!");
  }
  
  public void printRevert()
  {
    System.out.print("Revers: ");
    for (Node n = tail; n != null; n = n.prev)
    {
      System.out.print(n.val + ", ");
    }
    System.out.println("!");
  }
}
