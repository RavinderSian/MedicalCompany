FROM alpine:3.14
RUN apk update && apk add maven
COPY . /src
WORKDIR /src
RUN mvn package
CMD mvn spring-boot:run
