### Overview
  After a series of sprints during PI0 phase, we realize that a boilerplate project is required for next phase. 
This project can be easy to learn , easy to use. And it can contribute to speed up our development, 
no matter what project we would build like rest service, database-based application etc. 
This project shall provide some preset functionalities and the developers can easy to integrat with their features, 
especially they  don't need to pay much time and effort on such infrastructure construction.  
That's why we build this scaffold application,  and we will try our best to leverage the spring technology stack 
to build such application and it can be enhanced to adapt different requirements 
without changing much code of some infrastructure.

### About this web microservice
the web module is a web microservice built with spring boot framework, you can access the rest api via this format:
```
http://ip:8080/users
https://ip/users
```
BTW, both HTTP and HTTPS are supported.

### Production and dev profiles
Two spring profiles are created within 'src/main/resources/' directory  
```
application-production.yml
application-dev.yml
```
+ The application-production.yml is used for production environment, the application will try to connect to a remote Mysql server.  
+ The application-dev.yml is used for development, the application will start a in-memory H2 database, in this case the Mysql server isn't required.  
    By default, a administrator account is inserted to db for your need, the username is 'admin' and the password is '123'

Normally, this application shouldn't active the 'dev' profile in production environment. But you still can active a dev profile as long as you want. 
You can execute following command to start this application with a specific profile:  
``` 
 java -jar web.jar --spring.profiles.active=dev  
  (or production, the production mode is enabled by default)
```
If you want to active 'dev' profile during the development stage of your feature, you can modify the application.yml, 
change this settings to dev:
```
spring:
     profiles:
         active: dev　　  #currently only dev and production modes are supported
```


### Creating a CSR (Certificate Signing Request)
keytool -genkey -alias server -keyalg RSA -keysize 2048 -keystore com_oracle_communications_incubation.jks -dname "CN=com.oracle.communications.incubation, O=Oracle Corporation, L=, ST=, C=US" && keytool -certreq -alias server -file com_oracle_communications_incubation.csr -keystore com_oracle_communications_incubation.jks && echo Your certificate signing request is in com_oracle_communications_incubation.csr.  Your keystore file is com_oracle_communications_incubation.jks.  Thanks for using the DigiCert keytool CSR helper.

### Monitoring and management over HTTP (Spring Actuator)
Spring Boot includes a number of additional features to help you monitor and manage your application when it’s pushed to production.
You can choose to manage and monitor your application using HTTP endpoints. Currently the spring actuator feature is enabled, you can  monitor and interact with your application via following endpoints:
```
http://localhost:8080/swagger-ui.html

http://ip:8090/mgnt/health
http://ip:8090/mgnt/info
http://ip:8090/mgnt/metrics
http://ip:8090/mgnt/trace
http://ip:8090/mgnt/beans
http://ip:8090/mgnt/logfile  (view the log file)
......
```
For more information, you can read the spring doc via <a href="http://docs.spring.io/spring-boot/docs/1.5.4.RELEASE/reference/htmlsingle/#production-ready-monitoring">Spring Actuator</a>.
<br/>
For more endpoints, you can refer to <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html">Spring Boot Endpoints</a>

### Customizing banner
A banner.txt file is placed in resources directory to print more user friendly info to console while this applicaiton is running.  
You can generate the banner via this web tool:
```
http://patorjk.com/software/taag/#p=display&f=Big&t=Web%20microservice%20v0.1
```
Make sure to select the 'Big' font, 'Fitted' character width to generate the banner and paste it into banner.txt file.

### The technology stack
If you're the developer of this application or you just want to figure out what kind of technology stack get involved in ,  we recommend you to go through following documents qucickly.
```
spring security
           http://docs.spring.io/spring-security/site/docs/4.2.3.RELEASE/reference/htmlsingle/#getting-started
 spring boot
           http://docs.spring.io/spring-boot/docs/1.5.4.RELEASE/reference/htmlsingle/
spring mvc
           https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html
JPA(with hibernate implementation)
          http://techbus.safaribooksonline.com/book/programming/java/9781430249269
          http://download.oracle.com/otndocs/jcp/persistence-2_1-fr-eval-spec/index.html
spring data jpa
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

spring cache(with ehcache)
          https://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html
          http://www.ehcache.org/documentation/
spring actuator
          https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready
sl4fj + logback 
          https://www.slf4j.org/manual.html
          https://logback.qos.ch/documentation.html
gradle
          https://docs.gradle.org/3.3/userguide/userguide.html
Groovy DSL
         http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html
code quality control
         jacoco: http://www.jacoco.org/jacoco/trunk/doc/index.html
         checkstyle: http://checkstyle.sourceforge.net/
         PMD: https://pmd.github.io/pmd-5.8.1/usage/installing.html
         findbugs: http://findbugs.sourceforge.net/manual/index.html
 
```

### Compile the project
you can just compile the whole project:
```bash
  gradle clean build
```
Or compile a sub project like web module:
```bash
  gradle clean build -p web
```

### Run with the executable jar
you can run your application using java -jar, as shown in the following example:
```bash
 java -jar target/XXX.jar
 
 enable the debug property as follows:
 java -jar XXX.jar --debug
```
It is also possible to run a packaged application with remote debugging support enabled. Doing so lets you attach a debugger to your packaged application, as shown in the following example:
```bash
 java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n \
        -jar target/XXX.jar
```
You might also want to use the JAVA_OPTS operating system environment variable, as shown in the following example:
```bash
 export JAVA_OPTS=-Xmx1024m
```

### Port 
* registry-service: 9001, 9002
* rest-service-demo: 9010
* zuul-proxy: 9020
* auth: 9030

## To do:
1. cross
2. User/role/privilege
3. validation
4. cache
5. refactor package
6. security path
7. error code/result
8. Exception Filter
9. Conflunce page/Git link
