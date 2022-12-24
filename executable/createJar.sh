#! /bin/sh

# $1 - path to the maven executable

if [ $# -ne 1 ];
then
   echo "**********$0: Missing arguments"
  exit 1
fi

rm enron_to_csv-1.0.jar
cd ../enron_to_csv/
$1 package -f pom.xml
cp target/enron_to_csv-1.0.jar ../executable/