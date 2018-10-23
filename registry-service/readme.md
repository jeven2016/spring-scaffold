### Compile the project
you can just compile the whole project:
```bash
  gradle clean build
```

### Run a cluster with two eureka nodes
```bash
  /etc/hosts/
  
  127.0.0.1 node1 node2
```
run the two nodes
```bash
  java -jar XX.jar --spring.profiles.active=node1
  java -jar XX.jar --spring.profiles.active=node2
```
check the GUI:
```bash
  http://localhost:9000/   ---node1
  http://localhost:9001/   ---node2
```

### Tips
+ Need to specify what ip address should be registered for client
```bash
  java -jar client.jar --spring.cloud.inetutils.preferred-networks=192.168.3
```
