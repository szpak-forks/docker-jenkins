FROM jenkins/jenkins:lts-alpine

USER root

RUN apk add --update docker && rm -rf /var/cache/apk/*

COPY init.groovy.d /usr/share/jenkins/ref/init.groovy.d/
COPY default-config /usr/share/jenkins/ref/

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

COPY jenkins-boot.sh /usr/local/bin/jenkins-boot.sh
COPY jenkins-docker-group.sh /usr/local/bin/jenkins-docker-group.sh

RUN chmod 755 /usr/local/bin/jenkins-*.sh && chmod u+s /usr/local/bin/jenkins-docker-group.sh

USER jenkins

ENTRYPOINT ["/usr/local/bin/jenkins-boot.sh", "/sbin/tini", "--", "/usr/local/bin/jenkins.sh"]
