package nio;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.*;

public class DirectoryWatcher {

  public static void main(String[] args) {
    watch(Paths.get(
        "/root/Desktop/workspace/projects/live-experience/product/ui/target/oracle.cloud.product.ui-1.1-SNAPSHOT"));
  }

  //only able to monitor the parent directory changes
  static void watch(Path path) {
    try {
      WatchService ws = FileSystems.getDefault().newWatchService();
      path.register(ws, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

      WatchKey watchKey;
      try {
        while ((watchKey = ws.take()) != null) {
          for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
            Kind kind = watchEvent.kind();

            // This key is registered only
            // for ENTRY_CREATE events,
            // but an OVERFLOW event can
            // occur regardless if events
            // are lost or discarded.
            if (kind == OVERFLOW) {
              continue;
            }
            Path filePath = (Path) watchEvent.context();
            System.out.println(
                "Event kind:" + kind
                    + ". File affected: " + filePath.toAbsolutePath().toString() + ".");
          }
          watchKey.reset();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }


    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
