#!/bin/bash -x

echo "Adding jenkins to docker group..."

sudo /usr/local/bin/jenkins-docker-group.sh

echo "Running jenkins..."

/usr/local/bin/jenkins.sh
