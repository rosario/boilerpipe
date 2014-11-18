#!/bin/sh
#lein localrepo coords target/boilerpipe-core-1.2.3.jar | xargs lein localrepo install
mvn package
lein localrepo install target/boilerpipe-core-1.2.3.jar de.l3s.boilerpipe/boilerpipe 1.2.3
cp pom.xml ~/.m2/repository/de/l3s/boilerpipe/boilerpipe/1.2.3/boilerpipe-1.2.3.pom