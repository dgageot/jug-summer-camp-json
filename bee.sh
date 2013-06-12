#!/bin/bash

mvn clean install && bees app:deploy -a retour1024/usi -t java -R class=legacy.InnWeb -R java_version=1.8 -R classpath='classes:lib/*' target/distribution.zip