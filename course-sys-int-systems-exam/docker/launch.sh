#!/bin/sh
cd /root/wildfly-9.0.1.Final
#bin/standalone.sh --admin-only &
#sleep 5
#bin/jboss-cli.sh -c --command='data-source add --name=ExaminationDS --jndi-name=java:jboss/datasources/ExaminationDS --use-java-context=true --driver-name=h2 --connection-url=jdbc:h2:tcp://localhost/~/exam --user-name=sa --password=sa --spy=true --pool-prefill=true --min-pool-size=10 --max-pool-size=20'
#bin/jboss-cli.sh -c --command='shutdown'
java -cp modules/system/layers/base/com/h2database/h2/main/h2*.jar org.h2.tools.Server -webAllowOthers &
bin/standalone.sh -b 0.0.0.0
