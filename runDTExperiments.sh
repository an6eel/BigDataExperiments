#!/bin/bash

VERSION=${1:-1.0}

for i in {1..5}
do
	./runExperiment.sh $i $VERSION
done
