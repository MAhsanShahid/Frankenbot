#!/bin/bash

# start frankenbot admin backend on server

SCRIPT=$(readlink -f "$0")
BASEDIR=$(dirname "$SCRIPT")
cd $BASEDIR

#java -jar adminbackend-0.1-SNAPSHOT.jar 
nohup java -jar adminbackend-0.1-SNAPSHOT.jar > /dev/null 2>&1 &
echo $! > pid.txt

# process_with_pid_is_running=0
# if [ -f pid.txt ]
# then
#     pid=$( cat pid.txt )
#     for i in {0..200..1}
#             do

#         if ps -p $pid > /dev/null
#         then
#         process_with_pid_is_running=1
#         break
#         fi
#         sleep 0.1
#     done
# fi

# if [ $process_with_pid_is_running -eq 1 ]
#     then
#     echo "Started FREME"
# else
#     echo "FREME failed to start. Please check logs."
# fi
