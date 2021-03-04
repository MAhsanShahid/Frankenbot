#!/bin/bash

# compile and start admin backend for continuous deployment on server

# build
echo "### BUILD FRANKENBOT ADMIN BACKEND"
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )/../adminbackend/"
destination_dir="$base_dir../../installation"
mkdir -p $destination_dir
destination_dir="$destination_dir/adminbackend/"
cd $base_dir
mvn package -DskipTests=true

# stop old
echo "### STOP APPLICATION"
pid="pid.txt"
if [ -f $destination_dir$pid  ]; then
 kill `cat $destination_dir$pid` || true
fi

#copy
echo "### COPY APPLICATION DATA"
rm -r $destination_dir
mkdir -p $destination_dir
cd $base_dir
cp target/adminbackend-0.1-SNAPSHOT.jar $destination_dir
cp src/main/resources/application.properties.live $destination_dir/application.properties
cp ../deployment/start_backend.sh $destination_dir
cp ../deployment/stop_backend.sh $destination_dir
cp ../deployment/restart_backend.sh $destination_dir
cd $destination_dir
ln -s /local/frankenbot/installation/adminfrontend/ public

#start
echo "### START APPLICATION"
cd $destination_dir
chmod +x ./start_backend.sh
chmod +x ./stop_backend.sh
chmod +x ./restart_backend.sh
./start_backend.sh
