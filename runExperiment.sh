#!/bin/bash

experiment=${1:-1}

VERSION=${2:-1.0}

DATE=`date +"%F_%R"`

if [ $LOGNAME == "x76592732" ]
then
  /opt/spark-2.2.0/bin/spark-submit \
  --total-executor-cores 15 --executor-memory 10g \
  --conf spark.network.timeout=1000 \
  --conf spark.executor.heartbeatInterval=10000000\
  --master spark://hadoop-master:7077 \
  --class main.scala.an6eel.experiments.Experiment$experiment \
  BDII-experiment-$VERSION-jar-with-dependencies.jar 2> logs/experiment_${experiment}_v_$VERSION_$DATE
else
  /opt/spark-2.2.0-bin-hadoop2.7/bin/spark-submit \
  --master local[*] --class main.scala.an6eel.experiments.Experiment$experiment \
  BDII-experiment-$VERSION-jar-with-dependencies.jar
fi
