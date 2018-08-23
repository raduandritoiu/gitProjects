import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import udp.client.UdpClient;


public class MainUdpClient
{
  public static void main(String args[])
  {
    String addr = (args.length > 0) ? args[0] : "10.10.13.136";
    int port = (args.length > 1) ? Integer.parseInt(args[1]) : 44444;
    int num = (args.length > 2) ? Integer.parseInt(args[2]) : 1;
    
    // start the tasks
    ExecutorService pool = Executors.newFixedThreadPool(10);
    for (int i = 0; i < num; i++)
    {
      pool.execute(new ReadClientTask(addr, port));
    }
    
    // wait pool termination
    pool.shutdown();
    while (!pool.isTerminated())
    {
      try 
      {
        pool.awaitTermination(20, TimeUnit.MINUTES);
      }
      catch (Exception ex){}
    }
    
    System.out.println("===== END =========");
  }
  
  
  private static class ReadClientTask implements Runnable
  {
    final String addr;
    final int port;
    
    public ReadClientTask(String nAddr, int nPort)
    {
      addr = nAddr;
      port = nPort;
    }
    
    public void run()
    {
      int resp = 0;
      UdpClient client = new UdpClient();
      client.connect(addr, port);
      
      String sendStr, recvStr;
      
      //----
      sendStr = "Hello. I am the client.";
      client.write(sendStr);
      recvStr = client.read();
      if (recvStr != null)
        resp++;
      
      //----
      sendStr = "Give me the phrase.";
      client.write(sendStr);
      recvStr = client.read();
      if (recvStr != null)
        resp++;
      
      //----
      sendStr = "Thank you. Bye bye.";
      client.write(sendStr);
      recvStr = client.read();
      if (recvStr != null)
        resp++;
      
      System.out.println("** Client " + client.getLocalAddr() + " done with " + resp +" msgs");    
    }
  }
}
