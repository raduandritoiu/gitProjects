package utils;

public class PrintUtils
{
  public static void FromString(String str)
  {
    // chars
    String out = ""; //"-Chars:\n";
    for (int i = 0; i < str.length(); i++)  
    {
      out += str.charAt(i);
    }
    System.out.println(out);
    
//    // bytes
//    byte[] bytes = str.getBytes();
//    out = "-Bytes:";
//    for (int i = 0; i < bytes.length; i++)
//    {
//      if (i % 8 == 0) out += "  ";
//      if (i % 16 == 0) out += "\n";
//      out += (0xFF & bytes[i]) + " ";
//    }
//    System.out.println(out);
//    
//    
//    // hexa
//    out = "-Hexa:";
//    for (int i = 0; i < bytes.length; i++)
//    {
//      if (i % 8 == 0) out += " ";
//      if (i % 16 == 0) out += " \n";
//      out += String.format("%02x", bytes[i]).toUpperCase() + " ";
//    }
//    System.out.println(out);
    
//    System.out.println("-------------------------------------------------------\n");
  }
  
  
  public static void FromByte(byte[] bytes)
  {
    // chars
    String out = ""; //"Chars:\n";
    for (int i = 0; i < bytes.length; i++)
    {
      char ch = (char) bytes[i];
      if (ch == 0)
        out += ".";
      else
        out += ch;
    }
    System.out.println(out + "\n");
//    
//    // bytes
//    out = "Bytes:";
//    for (int i = 0; i < bytes.length; i++)
//    {
//      if (i % 8 == 0) out += "  ";
//      if (i % 16 == 0) out += "\n";
//      out += (0xFF & bytes[i]) + " ";
//    }
//    System.out.println(out + "\n");
//    
//    
//    // hexa
//    out = "Hexa:";
//    for (int i = 0; i < bytes.length; i++)
//    {
//      if (i % 8 == 0) out += " ";
//      if (i % 16 == 0) out += " \n";
//      out += String.format("%02x", bytes[i]).toUpperCase() + " ";
//    }
//    System.out.println(out);
//    
//    System.out.println("-------------------------------------------------------\n");
  }
}
