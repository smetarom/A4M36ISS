FROM ubuntu:vivid
RUN apt-get update
WORKDIR /root
RUN apt-get install -y openjdk-8-jdk unzip wget
RUN wget -O wildfly.zip http://download.jboss.org/wildfly/9.0.1.Final/wildfly-9.0.1.Final.zip
RUN unzip wildfly.zip
ADD launch.sh launch.sh
ADD config.cli config.cli
ADD examination.ear examination.ear
ADD keystore.jks wildfly-9.0.1.Final/standalone/configuration/keystore.jks
EXPOSE 9092 8080 8443
RUN echo 'CN\=localhost=ws' > wildfly-9.0.1.Final/standalone/configuration/ssl-roles.properties
RUN echo 'webuser=d08606f31be559c412c51fe8450c9e57' >> wildfly-9.0.1.Final/standalone/configuration/application-users.properties
RUN echo 'webuser=ws' >> wildfly-9.0.1.Final/standalone/configuration/application-roles.properties
RUN wildfly-9.0.1.Final/bin/jboss-cli.sh --file=config.cli
RUN rm -rf wildfly-9.0.1.Final/standalone/configuration/standalone_xml_history wildfly-9.0.1.Final/standalone/tmp wildfly-9.0.1.Final/standalone/log
ENTRYPOINT /bin/sh /root/launch.sh
