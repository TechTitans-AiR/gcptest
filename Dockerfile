# Koristi sliku s Javom 19
FROM openjdk:19-jdk-alpine

ARG _MONGO_CLUSTER
ARG _MONGO_PASSWORD
ARG _MONGODB
ARG _MONGOUSER

#Pohrani varijable unutar slike
RUN echo "MONGO_DATABASE=$_MONGODB" >> .env
RUN echo "MONGO_USER=$_MONGO_USER" >> .env
RUN echo "MONGO_PASSWORD=$_MONGO_PASSWORD" >> .env
RUN echo "MONGO_CLUSTER=$_MONGO_CLUSTER" >> .env

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
