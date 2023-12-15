# Koristi sliku s Javom 19
FROM openjdk:19-jdk-alpine

ARG _MONGO_CLUSTER
ARG _MONGO_PASSWORD
ARG _MONGODB
ARG _MONGOUSER



# Instaliraj Maven
RUN apk add --no-cache maven

# Kopiraj POM i sve potrebne datoteke za preuzimanje dependencija
COPY ./pom.xml .
COPY ./src ./src

# Kreiraj .env datoteku unutar Docker kontejnera pomoću skripte
COPY create-env.sh .
RUN sh create-env.sh

# Izgradi aplikaciju
RUN mvn clean install

# Pokreni aplikaciju
CMD ["java", "-jar", "target/gcptest.jar"]
