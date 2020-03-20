
import com.registry.Center;
import com.server.NettyServer;
import com.server.Server;

/**
 * @author fsq
 * 服务器一
 *
 */
public class MainServer {

  /**
   * 服务地址 127.0.0.1:8585
   * @param args
   */
  public static void main(String []args){


    Server server =new NettyServer(8585,"127.0.0.1",new Center("127.0.0.1:2181"));
    try {
      server.startIoc();
      server.start();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
