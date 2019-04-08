# Spring Boot Best Practices
Version: __v1.0.0__

```
                                          
                                           
+---------------------------------------+  ^
|        Global Error Handler           |  |
+---------------------------------------+  |
                                           |
+---------------------------------------+  |
|           Rest Controller             |  |
+---------------------------------------+  |
                                           |
+---------------------------------------+  |
|             Service                   |  |
+---------------------------------------+  |
                                           |
+---------------------------------------+  |
|         JPA Repository                |  |
+---------------------------------------+  |
                                           |
+---------------------------------------+  |
|          Database                     |  |
+---------------------------------------+  +

```

## Setup Project

__Import the Project__ _Import the Project to IntelliJ and run Gradle Auto Configuration_
```
Open the Project with InelliJ
Use "gradle" use autoimports => yes
```

__Start the Dev Env__ _Starting the Database and the SonarQube_
```
docker-comopose up
```

## SonarQube
After the `docker-compose up` you have a local sonarqube instance running at `http://localhost:9000`
* Username: admin
* Password: admin
```
gradle sonarqube -DsystemProp.sonar.host.url=http://localhost:9000  -DsystemProp.sonar.login={loginToken}
```

## Generic Endpoints

__/actuator/health__ _Simple health check method_
```
{ status: "UP" }
```

__/docs__ _Redirect to swagger ui_
```
http://localhost:8080/swagger-ui.html
```
