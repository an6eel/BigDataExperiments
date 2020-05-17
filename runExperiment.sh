#!/bin/bash

experiment=${1:-1}

VERSION=${2:-1.0}

if [ $LOGNAME == "x76592732H" ]
then
  /opt/spark-2.2.0/bin/spark-submit \
  --total-executor-cores 15 --executor-memory 10g \
  --master spark://hadoop-master:7077 \
  --class main.scala.an6eel.experiments.Experiment$experiment \
  BDII-experiment-$VERSION-jar-with-dependencies.jar
else
  /opt/spark-2.2.0-bin-hadoop2.7/bin/spark-submit \
  --master local[*] --class main.scala.an6eel.experiments.Experiment$experiment \
  BDII-experiment-$VERSION-jar-with-dependencies.jar
fi
