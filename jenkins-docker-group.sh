#!/bin/bash -x

DOCKER_SOCKET=/var/run/docker.sock
DOCKER_GROUP=docker

if [ -S ${DOCKER_SOCKET} ]; then
    DOCKER_GID=$(stat -c '%g' ${DOCKER_SOCKET})

    addgroup -g ${DOCKER_GID} ${DOCKER_GROUP}
    adduser jenkins ${DOCKER_GROUP}
fi
