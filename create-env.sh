#!/bin/sh

echo "MONGO_DATABASE=$_MONGODB" > src/main/resources/.env
echo "MONGO_USER=$_MONGOUSER" >> src/main/resources/.env
echo "MONGO_PASSWORD=$_MONGO_PASSWORD" >> src/main/resources/.env
echo "MONGO_CLUSTER=$_MONGO_CLUSTER" >> src/main/resources/.env
