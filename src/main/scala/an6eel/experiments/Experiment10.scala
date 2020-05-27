package main.scala.an6eel.experiments

import java.io.PrintWriter

import main.scala.an6eel._

object Experiment10 {
  def main(arg: Array[String]): Unit = {

    val jobName = "RF-RUS+FCNN-full-5D-32B-900T-K10"
    val errorLevel = "ERROR"
    val classifierName = "RandomForest"
    val balancerName = "RUS+FCNN"

    val classifierParams = Map[String, String](
      "depth" -> "10",
      "bins" -> "32",
      "trees" -> "900"
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