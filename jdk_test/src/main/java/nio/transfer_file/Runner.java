package nio.transfer_file;

import java.nio.file.Path;
import java.util.concurrent.LinkedBlockingQueue;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import nio.WatchDirectores;

public class Runner {

  public static void main(String[] args) {
    WatchDirectores watchDirectores = new WatchDirectores();
    LinkedBlockingQueue<Path> queue = new LinkedBlockingQueue<>();

    try {
      uploadFiles(queue);
      watchDirectores.waching(queue,
          "/root/Desktop/workspace/projects/live-experience/product/ui/target/oracle.cloud.product.ui-1.1-SNAPSHOT/assets");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void uploadFiles(LinkedBlockingQueue<Path> queue) {
    SshClientHelper clientHelper = new SshClientHelper();
    SSHClient sshClient = clientHelper.initClient("10.75.171.104", "root", "NextGen");
    Session session = clientHelper.startSession(sshClient);

    new Thread(() -> {
      Path path;
      try {
        while (true) {
          if(!session.isOpen()){
            session  = clientHelper.startSession(sshClient);
          }

          path = queue.take();
          if (path != null) {
            System.out.println("receive:" + path);
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }).start();
  }

}
