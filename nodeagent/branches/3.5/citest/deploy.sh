#!/bin/bash

ARCHIVE_URL='http://hudson.free4lab.com/job/appcloud-nodeagent-trunk/lastSuccessfulBuild/artifact/target/*zip*/target.zip'
SNAPSHOT_NAME='nodeagent-0.3.1.jar'
RUN_NAME='nodeagent.jar'

echo 'deleting old archives'
rm -rf ./target*

echo 'downloading archives'
wget $ARCHIVE_URL 

echo 'unzipping archives'
unzip target.zip

echo 'rename jars'
mv ./target/$SNAPSHOT_NAME $RUN_NAME -f

echo 'deleting old jars'
rm -rf ./java-lib/*

echo 'copying new jars'
mv ./target/dependency/* ./java-lib/

echo 'now, ready to to'
#echo 'excute running script'
#nohup sh run.sh &> tip.log &
