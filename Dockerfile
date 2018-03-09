FROM jenkins/jenkins:lts-alpine

USER root

RUN apk add --update docker && rm -rf /var/cache/apk/* && addgroup -g 994 jenkins-docker && adduser jenkins jenkins-docker

USER jenkins

COPY executors.groovy /usr/share/jenkins/ref/init.groovy.d/executors.groovy
COPY setup-users.groovy /usr/share/jenkins/ref/init.groovy.d/setup-users.groovy
COPY simple-theme.xml /var/jenkins_home/org.codefirst.SimpleThemeDecorator.xml
COPY locale.xml /var/jenkins_home/locale.xml

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

COPY config.xml /var/jenkins_home/config.xml
