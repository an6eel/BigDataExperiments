package main.scala.an6eel

import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint

class Experiment(
    context: SparkContext,
    trainPath: String,
    testPath: String,
    classifier: Classifier,
    preprocess: PreprocessFunction,
    sample: Double = 1.0
) {

  private def loadTrainData(): DataSet = {
    val rawDataTrain = sample match {
      case 1.0 => context.textFile(trainPath)
      case _ => context.textFile(trainPath).sample(false,sample)
    }

    val train = rawDataTrain.map { line =>
      val array = line.split(",")
      val arrayDouble = array.map(f => f.toDouble)
      val featureVector = Vectors.dense(arrayDouble.init)
      val label = arrayDouble.last
      LabeledPoint(label, featureVector)
    }.persist

    train.count
    train.first
    train
  }

  private def loadTestData(): DataSet = {
    val rawDataTest = context.textFile(testPath)

    val test = rawDataTest.map { line =>
      val array = line.split(",")
      val arrayDouble = array.map(f => f.toDouble)
      val featureVector = Vectors.dense(arrayDouble.init)
      val label = arrayDouble.last
      LabeledPoint(label, featureVector)
    }.persist

    test.count
    test.first
    test
  }

  private def getMetrics(predictions: Predictions) = {
    val metrics = new MulticlassMetrics(predictions)
    val tpr = metrics.truePositiveRate(1.0)
    val tnr = metrics.truePositiveRate(0.0)
    val cm = metrics.confusionMatrix
    val accuracy = metrics.accuracy
    (tpr * tnr, cm, accuracy)
  }

  def run() = {
    val train = loadTrainData()
    val test = loadTestData()
    val cleanedData = preprocess(train)
    val predictions: Predictions = classifier.trainAndPredict(cleanedData, test, context)
    getMetrics(predictions)
  }
}

object Experiment {

  def apply(jobName: String,
            logLevel: String,
            trainPath: String,
            testPath: String,
            classifier: Classifier,
            balancer: PreprocessFunction,
            sample: Double = 1.0) = {
    val conf = new SparkConf().setAppName(jobName)
    val context = new SparkContext(conf)
    context.setLogLevel(logLevel)

    new Experiment(context, trainPath, testPath, classifier, balancer, sample)
  }
}
