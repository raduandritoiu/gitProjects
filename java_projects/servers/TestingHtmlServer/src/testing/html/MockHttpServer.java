package testing.html;

import http.HttpSocketHandler;
import tcp.server.TcpServer;
import testing.html.requests.TestGet;
import testing.html.requests.TestPost;
import testing.https.MockSocketHandler;


public class MockHttpServer
{
  private final TcpServer server1;
  private final TcpServer server2;
  
  public MockHttpServer()
  {
//    HttpSocketHandler serverHandler = new HttpSocketHandler();
//    serverHandler.addResourceHandler(new TestPost());
//    serverHandler.addResourceHandler(new TestGet());
    
    
    MockSocketHandler serverHandler = new MockSocketHandler();
    
    String localIp = "192.168.1.43";
    server1 = new TcpServer(localIp, 44400, 5);
    server2 = new TcpServer(localIp, 44444, 5);
    
    server1.setHandler(serverHandler);
    
    server2.setHandler(serverHandler);
  }
  
  public void start()
  {
    server1.start();
    server2.start();
  }
  
  public void stop()
  {
    server1.stopWait();
    server2.stopWait();
  }
}
