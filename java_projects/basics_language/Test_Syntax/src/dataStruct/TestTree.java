package dataStruct;

import dataStruct.BinaryTree.INodeValue;
import dataStruct.BinaryTree.Node;
import dataStruct.BinaryTree.TreeIterator;

public class TestTree
{

  public static void test()
  {
    BinaryTree tree = new BinaryTree();
    tree.add(new NodeValue(10));
    tree.add(new NodeValue(25));
    
    tree.add(new NodeValue(8));
    tree.add(new NodeValue(7));
    tree.add(new NodeValue(2));
    tree.add(new NodeValue(3));
    tree.add(new NodeValue(1));
    tree.add(new NodeValue(4));
    tree.add(new NodeValue(5));
    tree.add(new NodeValue(6));
    
    tree.add(new NodeValue(21));
    tree.add(new NodeValue(12));
    tree.add(new NodeValue(14));
    tree.add(new NodeValue(16));
    tree.add(new NodeValue(18));
    tree.add(new NodeValue(20));
    tree.add(new NodeValue(11));
    tree.add(new NodeValue(13));
    tree.add(new NodeValue(15));
    tree.add(new NodeValue(17));
    
    tree.add(new NodeValue(38));
    tree.add(new NodeValue(37));
    tree.add(new NodeValue(36));
    tree.add(new NodeValue(35));
    tree.add(new NodeValue(34));
    tree.add(new NodeValue(33));

    tree.add(new NodeValue(48));
    tree.add(new NodeValue(50));
    tree.add(new NodeValue(46));
    tree.add(new NodeValue(44));
    tree.add(new NodeValue(42));
    tree.add(new NodeValue(40));
    tree.add(new NodeValue(47));
    tree.add(new NodeValue(45));
    tree.add(new NodeValue(43));
    tree.add(new NodeValue(41));

    
    System.out.println("Cresc: " + tree.printCrescator());
    System.out.println("Decresc: " + tree.printDescresc());
    
    System.out.println("Cresc: " + tree.printCrescatorRec(tree.base));
    System.out.println("Decresc: " + tree.printDescrescRec(tree.base));
    
    
    System.out.println("Pula mea 1 : " + tree.printCresc(tree.base));
    System.out.println("Pula mea 2 : " + tree.printCresc(tree.first));
    System.out.println("Pula mea 3 : " + tree.printCresc(tree.last));
    
    
    
    Node n;
    System.out.print("Pula mea 4 : ");
    for(n = tree.first; n != null; n = n.next())
    {
      System.out.print(n.value.toString() + " ");
    }
    System.out.println("");
    
    
    n = null;
    TreeIterator it = tree.getIterator();
    while (it.hasNext())
    {
      n = it.next();
      System.out.println(n.value.toString() + "  " + it.hasNext());
    }
    
    n = it.next();
    System.out.println(n + "  " + it.hasNext());
    n = it.next();
    System.out.println(n + "  " + it.hasNext());
    n = it.next();
    System.out.println(n + "  " + it.hasNext());
    n = it.next();
    System.out.println(n + "  " + it.hasNext());
    
    System.out.println("");
  }
  
  
  public static class NodeValue implements INodeValue
  {
    public int val;
    
    public NodeValue(int v)
    {
      val = v;
    }
    
    public int compare(INodeValue node)
    {
      int v = ((NodeValue) node).val;
      if (val < v) return -1;
      else if (val == v) return 0;
      return 1;
    }
    
    public int hashCode()
    {
      return val;
    }
    
    public String toString()
    {
      return val + "";
    }
  }
}
