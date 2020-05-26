package main.scala.an6eel.experiments

import java.io.PrintWriter

import main.scala.an6eel._

object Experiment9 {
  def main(arg: Array[String]): Unit = {

    val jobName = "RF-RUS+RNG-full-10D-32B-700T"
    val errorLevel = "ERROR"
    val classifierName = "RandomForest"
    val balancerName = "RUS+RNG"

    val classifierParams = Map[String, String](
      "depth" -> "10",
      "bins" -> "32",
      "trees" -> "700"
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