#!/bin/bash

VERSION=${1:-1.0}

for i in {6..10}
do
  ./runExperiment.sh $i $VERSION
done
