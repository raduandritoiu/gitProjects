package dataStruct;

public class BinaryTree
{
  public Node base;
  public Node first;
  public Node last;
  
  
  public BinaryTree()
  {}
  
  public BinaryTree(INodeValue val)
  {
    base = new Node(val);
    first = base;
    last = base;
  }
  
  
  public Node add(INodeValue newValue)
  {
    Node newNode = new Node(newValue);
    if (base == null)
    {
      base = newNode;
      first = newNode;
      last = newNode;
      return newNode;
    }
    
    Node crtNode = base;
    Boolean chgFirst = true;
    Boolean chgLast = true;
    
    while (crtNode != null)
    {
      int compare = newValue.compare(crtNode.value);
      if (compare < 0)
      {
        chgLast = false;
        if (crtNode.left == null)
        {
          // found place
          crtNode.left = newNode;
          newNode.parent = crtNode;
          if (chgFirst)
          {
            first = newNode;
          }
          return newNode;
        }
        crtNode = crtNode.left;
      }
      else
      {
        chgFirst = false;
        if (crtNode.right == null)
        {
          // found place
          crtNode.right = newNode;
          newNode.parent = crtNode;
          if (chgLast)
          {
            last = newNode;
          }
          return newNode;
        }
        crtNode = crtNode.right;
      }
    }
    
    return newNode;
  }
  
  
  public TreeIterator getIterator()
  {
    return new TreeIterator(first);
  }
  
  
  public String printCrescator()
  {
    return printCrescatorRec(base);
  }
  public static String printCrescatorRec(Node n)
  {
    if (n == null) return "";
    return "(" + printCrescatorRec(n.left) + " " + n.value.toString() + " " + printCrescatorRec(n.right) + ")";
  }
  
  
  public String printDescresc()
  {
    return printDescrescRec(base);
  }
  public static String printDescrescRec(Node n)
  {
    if (n == null) return "";
    return "(" + printDescrescRec(n.right) + " " + n.value.toString() + " " + printDescrescRec(n.left) + ")";
  }
  
  
  public static String printCresc(Node n)
  {
    String str = "";
    Node prev = null;
    Node crt = n;
    while (crt != null)
    {
      // came from parent
      if (prev == crt.parent)
      {
        // can move left 
        if (crt.left != null)
        {
          // move to left
          prev = crt;
          crt = crt.left;
        }
        else
        {
          // simulate come from left
          prev = crt.left;
        }
      }
      // came from left
      if (prev == crt.left)
      {
        // do stuff
        str = str + crt.value.toString() +" ";
        
        // can move right
        if (crt.right != null)
        {
          // move to right
          prev = crt;
          crt = crt.right;
        }
        else
        {
          // simulate came from right
          prev = crt.right;
        }
      }
      // came from right
      if (prev == crt.right)
      {
        // move up
        prev = crt;
        crt = crt.parent;
      }
    }
    return str;
  }
  
  
  
  
  
  public static class Node
  {
    public Node left, right, parent;
    public INodeValue value;
    
    public Node()
    {}
    
    public Node(INodeValue v)
    {
      value = v;
    }
    
    public int compare(Node n)
    {
      return value.compare(n.value);
    }
    
    public Node next()
    {
      Node next = this;
      if (next.right != null)
      {
        next = next.right;
        while (next.left != null)
          next = next.left;
      }
      else
      {
        while (next.parent != null && next.parent.right == next)
          next = next.parent;
        next = next.parent;
      }
      
      return next;
    }
  }
  
  
  
  public static interface INodeValue
  {
    int compare(INodeValue node);
  }
  
  
  public static class TreeIterator
  {
    private Node crt;
    
    public TreeIterator(Node start)
    {
      crt = start;
    }
    
    public boolean hasNext()
    {
      return crt != null;
    }
    
    
    public Node next()
    {
      if (crt == null) 
        return null;
      
      Node tmp = crt;
      crt = crt.next();
      return tmp;
    }
  }
}
