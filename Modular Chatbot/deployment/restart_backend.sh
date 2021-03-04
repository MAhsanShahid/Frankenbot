#!/bin/bash

# restart adminbackend

SCRIPT=$(readlink -f "$0")
BASEDIR=$(dirname "$SCRIPT")
cd $BASEDIR
./stop_backend.sh
./start_backend.sh

