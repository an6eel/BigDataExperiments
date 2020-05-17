package main.scala

import org.apache.spark.SparkContext
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

package object an6eel {
  type DataSet = RDD[LabeledPoint]
  type ClassifierParams = Map[String, String]
  type Model = AnyRef
  type Predictions = RDD[(Double, Double)]
  type Trainer = DataSet => Model
  type Predictor = (Model, DataSet, SparkContext) => Predictions
  type PreprocessFunction = RDD[LabeledPoint] => RDD[LabeledPoint]
}
