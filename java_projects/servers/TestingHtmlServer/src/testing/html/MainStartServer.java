package testing.html;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import http.auth.AuthManager;
import testing.business.data.models.User;


public class MainStartServer
{
  public static void main(String[] args) throws Exception
  {
    Logger log = Logger.getLogger("tcp");
    log.setLevel(Level.ALL);
    log.addHandler(new ConsoleHandler());
    
    // set up  businelss logic
    setUpDB();
    
    // set up server 
    MockHttpServer httpServer = new MockHttpServer();
    httpServer.start();

    
    //
    boolean running = true;
    int paces = 0;
    while (running)
    {
      paces++;
    }
    
    httpServer.stop();
    System.out.println("Server CLOSED! " + paces);
  }
  
  
  private static void setUpDB()
  {
    AuthManager.get().users.add(new User("Radu", "radu", 30, 3000));
    AuthManager.get().users.add(new User("Ramo", "ramo", 30, 2813));
    AuthManager.get().users.add(new User("Rares", "rares", 1, 2134));
  }
}
