#!/bin/bash

if [ "$#" -eq 0 ] || [ "$1" = "help" ]
then
    echo "Usage: $0 <DEBUG[true/false]> <SOURCE[true/false]>"
fi

LIB=./java-lib

PROGRAM=\
./properties:\
./dbproxy.jar

MAINCLASS='appcloud.dbproxy.ProxyServer'

for file in ${LIB}/*
do
if test -f $file
then
    echo 'load jar 文件:' $file
    PROGRAM=${PROGRAM}:$file
fi
done

DEBUG="false";
SOURCE="true";
if [ -n $1 ]
then
    DEBUG="$1"
fi
if [ -n $2 ]
then
    $SOURCE = "$2"
fi

echo 'excuting runner'
java -Dtip.util.log.DEBUG=$DEBUG -Dtip.util.log.SOURCE=$SOURCE -cp ${PROGRAM} $MAINCLASS
