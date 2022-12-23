#! /bin/sh

rm enron_to_csv-1.0.jar
cd ../enron_to_csv/
/opt/homebrew/Cellar/maven/3.8.6/libexec/bin/mvn package -f pom.xml
cp target/enron_to_csv-1.0.jar ../executable/