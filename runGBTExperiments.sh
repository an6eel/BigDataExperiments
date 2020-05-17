#!/bin/bash

VERSION=${1:-1.0}

for i in {10..15}
do
  ./runExperiment.sh $i $VERSION
done
