# Koristi sliku s Javom 19
FROM openjdk:19-jdk-alpine

ARG _MONGO_CLUSTER
ARG _MONGO_PASSWORD
ARG _MONGODB
ARG _MONGOUSER

# Postavi varijable okoline
ENV MONGO_DATABASE=$_MONGODB
ENV MONGO_USER=$_MONGOUSER
ENV MONGO_PASSWORD=$_MONGO_PASSWORD
ENV MONGO_CLUSTER=$_MONGO_CLUSTER

# Instaliraj Maven
RUN apk add --no-cache maven

# Postavi radni direktorij
WORKDIR /usr/src/app

# Kopiraj POM i sve potrebne datoteke za preuzimanje dependencija
COPY ./pom.xml .
COPY ./src ./src

# Izgradi aplikaciju
RUN mvn clean install

# Pokreni aplikaciju
CMD ["java", "-jar", "target/gcptest.jar"]
