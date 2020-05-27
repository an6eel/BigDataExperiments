package main.scala.an6eel.experiments

import java.io.PrintWriter

import main.scala.an6eel._

object Experiment5 {
  def main(arg: Array[String]): Unit = {

    val jobName = "DT-RUS-BD-full-30D-64B"
    val errorLevel = "ERROR"
    val classifierName = "DecisionTree"
    val balancerName = "RUS"

    val classifierParams = Map[String, String](
      "depth" -> "30",
      "bins" -> "64"
    )
    val classifier = Classifier(classifierName, classifierParams)
    val preprocess = Preprocess(balancerName)

    val firstExperiment = Experiment(jobName, errorLevel, pathTrain, pathTest, classifier, preprocess, sample)

    // Run Experiment

    val results = firstExperiment.run()

    // Write Results

    val writer = new PrintWriter(PATH + jobName + ".txt")

    writer.write(
      "TPR*TNR: " + results._1 + "\n" +
        "Confusion Matrix " + results._2 + "\n" +
        "Accuracy" + results._3 + "\n"
    )
    writer.close()
  }
}