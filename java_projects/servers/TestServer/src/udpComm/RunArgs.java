package udpComm;

public class RunArgs
{
  int cnt = 4;
  
  String bindAddr = "192.168.100.2";
  int bindPort = 55001;
  int bindReuse = 1;
  
  boolean send = false;
  String sendAddr = "192.168.100.255";
  int sendPort = 55001;

  
  public RunArgs()
  {}
  
  public RunArgs(int n_cnt, String n_bindAddr, int n_bindPort, int n_bindReuse, boolean n_send, String n_sendAddr, int n_sendPort)
  {
    cnt = n_cnt;
    bindAddr = n_bindAddr;
    bindPort = n_bindPort;
    bindReuse = n_bindReuse;
    send = n_send;
    sendAddr = n_sendAddr;
    sendPort = n_sendPort;
  }
  
  
  public RunArgs copy()
  {
    return new RunArgs(cnt, bindAddr, bindPort, bindReuse, send, sendAddr, sendPort);
  }
  
  
  @Override
  public String toString()
  {
    return "Args: cnt ("+cnt+")   bind ("+bindAddr+":"+bindPort+") reuse ("+bindReuse+")   send (" + (send ? (sendAddr+":"+sendPort+")") : "NOT)");
  }
}
