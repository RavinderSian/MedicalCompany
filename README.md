# MedicalCompany
Microservices medical application with a main application and appointments service

## Running applications as Docker containers
Ideally both applications should be running as there are some API calls between the main app and appointments service
The main application must be exposed on port 8080 and the appointments application must be exposed on port 8081

To send requests to the applications as containers the url must be in the following format:
http:ip_address:port/desired_url

## Running main application as Docker Container
Ideally both applications should be running as there are some API calls between the main app and appointments service
The main application must be exposed on port 8080 and the appointments application must be exposed on port 8081

The build command must be run from the "medicalcompany" directory
Docker build for main application:
![](https://github.com/RavinderSian/MedicalCompany/blob/main/screenshots/Medical%20main%20docker%20build.JPG)

Command to run main application container with exposed port 8080:
```
sudo docker run -p 8080:8080 container_name
```

Expected output for docker running main application:
![](https://github.com/RavinderSian/MedicalCompany/blob/main/screenshots/Medical%20main%20docker%20output.JPG)

## Running appointments application as Docker Container
The build command must be run from the "medicalcompany-appointments" directory
Docker build for appointments application:
![](https://github.com/RavinderSian/MedicalCompany/blob/main/screenshots/Appointments%20docker%20build.JPG)

Command to run appointments application container with exposed port 8081:
```
sudo docker run -p 8081:8081 container_name
```

Expected output for docker running appointments application:
![](https://github.com/RavinderSian/MedicalCompany/blob/main/screenshots/Appointments%20docker%20output.JPG)

## Postman Requests

