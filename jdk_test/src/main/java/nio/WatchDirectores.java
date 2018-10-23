package nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


public class WatchDirectores {

  private static Map<WatchKey, Path> keyPathMap = new HashMap<>();

  public void waching(BlockingQueue<Path> queue, String path) throws Exception {
    try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
      registerDir(Paths.get(path), watchService);
      startListening(queue, watchService);
    }
  }

  public void registerDir(Path path, WatchService watchService) throws
      IOException {

    if (!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
      return;
    }

    System.out.println("registering: " + path);

    WatchKey key = path.register(watchService,
        StandardWatchEventKinds.ENTRY_CREATE,
        StandardWatchEventKinds.ENTRY_MODIFY,
        StandardWatchEventKinds.ENTRY_DELETE);
    keyPathMap.put(key, path);

    for (File f : path.toFile().listFiles()) {
      registerDir(f.toPath(), watchService);
    }
  }

  public void startListening(BlockingQueue<Path> queue, WatchService watchService)
      throws Exception {
    while (true) {
      WatchKey queuedKey = watchService.take();
      for (WatchEvent<?> watchEvent : queuedKey.pollEvents()) {
        Path pathContext = ((Path) watchEvent.context());
        System.out.printf("Event... kind=%s, count=%d, context=%s Context type=%s%n",
            watchEvent.kind(),
            watchEvent.count(), watchEvent.context(),
            pathContext.getClass());
        boolean result = queue.offer(pathContext);
        if (!result) {
          System.out.println("failed to send a event for " + pathContext);
        }

        //do something useful here
        if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
          //this is not a complete path
          Path path = (Path) watchEvent.context();
          //need to get parent path
          Path parentPath = keyPathMap.get(queuedKey);
          //get complete path
          path = parentPath.resolve(path);

          registerDir(path, watchService);
        }
      }
      if (!queuedKey.reset()) {
        keyPathMap.remove(queuedKey);
      }
      if (keyPathMap.isEmpty()) {
        break;
      }
    }
  }
}