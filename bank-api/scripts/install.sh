#!/usr/bin/bash

# Install Redis

docker run --name redis -p 6379:6379 -d redis:7.4-alpine

# Connect to redis via cli
# redis-cli
# > KEYS *
# > GET Transaction:<id>

# Install ActiveMQ

docker run -d --name activemq -p 61616:61616 -p 8161:8161 --rm apache/activemq-artemis:latest-alpine

# Artemis Web Console

# http://localhost:8161/console/

# access artemis activemq

# docker exec -it mycontainer /var/lib/artemis-instance/bin/artemis shell --user artemis --password artemis

# Install RabbitMQ

# latest RabbitMQ 3.13
docker run -d --rm --name rabbitmq -p 5672:5672 -p 8080:15672 -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest -e RABBITMQ_DEFAULT_VHOST=/production rabbitmq:3.13.6-alpine

# enable plugins managements

docker exec [CONTAINER_NAME] rabbitmq-plugins enable rabbitmq_management

# access web console
# http://localhost:8080/
