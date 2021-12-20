# MedicalCompany
Microservices medical application with a main application and appointments service

## Running as Docker Containers
Ideally both applications should be running as there are some API calls between the main app and appointments service
The main application must be exposed on port 8080 and the appointments app must be exposed on port 8081

Docker build for main application:
![](https://github.com/RavinderSian/MedicalCompany/blob/main/screenshots/Medical%20main%20docker%20build.JPG)

Command to run main application container with exposed port 8080:
```
sudo docker run -p 8080:8080 medicalmain
```

Expected output for docker running main application:
![](https://github.com/RavinderSian/MedicalCompany/blob/main/screenshots/Medical%20main%20docker%20output.JPG)


## Postman Requests
