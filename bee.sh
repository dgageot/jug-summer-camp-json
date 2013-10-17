#!/bin/bash

mvn clean install && bees app:deploy \
 -a retour1024/jug \
 -t java \
 -R class=legacy.InnWeb \
 -R java_version=1.8 \
 -R JAVA_OPTS="-Dhttp.disable.classpath=true -DPROD_MODE=true" \
 -R classpath='classes:lib/*' target/distribution.zip