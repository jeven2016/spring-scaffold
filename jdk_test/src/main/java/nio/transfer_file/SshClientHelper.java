package nio.transfer_file;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.schmizz.keepalive.KeepAliveProvider;
import net.schmizz.sshj.DefaultConfig;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.FileSystemFile;

public class SshClientHelper {

  public SSHClient initClient(String host, String username, String password) {
    DefaultConfig defaultConfig = new DefaultConfig();
    defaultConfig.setKeepAliveProvider(KeepAliveProvider.KEEP_ALIVE);
    final SSHClient ssh = new SSHClient(defaultConfig);
    try {
      ssh.addHostKeyVerifier(new PromiscuousVerifier());
      ssh.connect(host);
      ssh.getConnection().getKeepAlive().setKeepAliveInterval(5); //every 60sec
      ssh.authPassword(username, password);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return ssh;
  }

  public Session startSession(SSHClient sshClient) {
    Session session = null;
    try {
      session = sshClient.startSession();
      session.allocateDefaultPTY();
    } catch (ConnectionException e) {
      e.printStackTrace();
    } catch (TransportException e) {
      e.printStackTrace();
    }
    return session;
  }

  public Command executeCommand(Session session, String commandLine) {
    //execute command
    try {
      Command cmd = session.exec("ls -l");
      System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
      cmd.join(5, TimeUnit.SECONDS);
      return cmd;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void uploadFile(String sourcePath, String destPath, SSHClient sshClient) {
    try (SFTPClient sftp = sshClient.newSFTPClient()) {
      sftp.put(
          new FileSystemFile("/root/Desktop/workspace/opensource/live-experience/Jenkinsfile"),
          "/home/opc/jeven");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void closeSession(Session session) {
    try {
      session.close();
    } catch (TransportException e) {
      e.printStackTrace();
    } catch (ConnectionException e) {
      e.printStackTrace();
    }

  }

  public void closeClient(SSHClient ssh) {
    try {
      ssh.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
