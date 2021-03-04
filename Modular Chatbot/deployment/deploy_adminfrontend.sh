#!/bin/bash

# create an dinstall admin frontend for continuous deployment on server

base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )/../adminfrontend"
destination_dir="$base_dir/../../installation/adminfrontend"
mkdir -p $destination_dir
cd $base_dir
rm src/config.json
cp src/config.live.json src/config.json
npm install
npm run build
git checkout src/
rm -r $destination_dir
cp -r "$base_dir/build/" $destination_dir
$destination_dir/../adminbackend/restart_backend.sh
