#!/bin/sh

# $1 - the value of this argument specifies the upper limit of the no. of emails to be read and hence written to the output csv dataset file. -1 indicates no limit.
# $2 - the value of this argument specifies the upper limit of the no. of emails per user to be read and hence written to the output csv dataset file. -1 indicates no limit.

if [ $# -ne 2 ];
then
   echo "**********$0: Missing arguments"
   echo "Argument information"
   echo " \$1 - the value of this argument specifies the upper limit of the no. of emails to be read and hence written to the output csv dataset file. -1 indicates no limit."
   echo " \$2 - the value of this argument specifies the upper limit of the no. of emails per user to be read and hence written to the output csv dataset file. -1 indicates no limit."

  exit 1
fi

java -jar enron_to_csv-1.0.jar ../maildir ../structuredData/enronRawEmails.csv $1 $2