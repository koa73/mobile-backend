#!/bin/bash

PIDPATH=/var/run/mobile-app/
START=""

# List of services

if [ ! -d /var/run/mobile-app ] ; then
mkdir /var/run/mobile-app
fi


array[0]="config"
array[1]="registry"
array[2]="auth-service"
array[3]="mobile"
array[4]="web"
array[5]="proxy"
#array[6]="oauth-service"


echo "Stopping ..."
for n in "${array[@]}"; do
     PIDFILE="$PIDPATH${n}.pid" 
     if [ ! -f $PIDFILE ]
        then
            echo -e '\E[31;40m'"\033[1m${n}\033[0m process was not runned"
        else
            PID=$(cat $PIDFILE)
            echo -ne "PID ${PID} "
            kill -6 ${PID} && rm  $PIDFILE
            while [ -x /proc/${PID} ]
            do
               sleep 1
            done
            echo -e '\E[36;40m'"\033[1m${n}\033[0m stopped"
        fi
done
echo
echo "Starting services ..."

for n in "${array[@]}"; do
    START="$START $AND /usr/local/mobile-app/${n}.sh start"
    AND=" &&"
done

eval $START