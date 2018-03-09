FROM jenkins/jenkins:lts-alpine

USER root

RUN apk add --update docker && rm -rf /var/cache/apk/* && addgroup -g 994 jenkins-docker && adduser jenkins jenkins-docker

USER jenkins

COPY init.groovy.d /usr/share/jenkins/ref/init.groovy.d/
COPY default-config /var/jenkins_home/
COPY jobs /var/jenkins_home/jobs/

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt; \
    chown -R jenkins:jenkins /var/jenkins_home
