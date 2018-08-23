import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import tcp.client.TcpClient;

public class MainTcpClient
{
  public static void main(String[] args) throws Exception
  {
    String addr = (args.length > 0) ? args[0] : "192.168.100.3";
    int port = (args.length > 1) ? Integer.parseInt(args[1]) : 55555;
    int num = (args.length > 2) ? Integer.parseInt(args[2]) : 60;
    
    // start the tasks
    AtomicInteger id = new AtomicInteger(0);
    ExecutorService pool = Executors.newFixedThreadPool(10);
    for (int i = 0; i < num; i++)
    {
      pool.execute(new ReadClientTask(addr, port, id));
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
    final AtomicInteger id;
    
    public ReadClientTask(String nAddr, int nPort, AtomicInteger nId)
    {
      addr = nAddr;
      port = nPort;
      id = nId;
    }
    
    public void run()
    {
      TcpClient client = new TcpClient();
      client.connect(addr, port);
      
      try
      {
        String sendStr;
        String recvStr;
        int resp = 0;
        
        PrintWriter out = new PrintWriter(client.sock.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(client.sock.getInputStream()));
        
        //----
        sendStr = "Hello. I am the client.";
        out.println(sendStr);
        recvStr = br.readLine();
        if (recvStr != null)
          resp++;
        
        //----
        sendStr = "Give me the phrase.";
        out.println(sendStr);
        recvStr = br.readLine();
        if (recvStr != null)
          resp++;
        
        //----
        sendStr = "Thank you. Bye bye.";
        out.println(sendStr);
        recvStr = br.readLine();
        if (recvStr != null)
          resp++;
        
        System.out.println("** " + id.incrementAndGet() + " Client " + client.getLocalAddr() + " done with " + resp + " msgs");    
      }
      catch (Exception ex){}
    }
  }
}


