#!/usr/bin/env bash
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000


