#!/bin/bash

# stop the admin backend

SCRIPT=$(readlink -f "$0")
BASEDIR=$(dirname "$SCRIPT")
cd $BASEDIR

if [ -f pid.txt  ]; then
 kill `cat pid.txt` || true
fi