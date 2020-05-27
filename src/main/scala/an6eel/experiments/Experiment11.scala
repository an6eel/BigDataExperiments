package main.scala.an6eel.experiments

import java.io.PrintWriter

import main.scala.an6eel._

object Experiment11 {
  def main(arg: Array[String]): Unit = {

    val jobName = "GBT-RUS-full-1.0-5D-300I"
    val errorLevel = "ERROR"
    val classifierName = "GradientBoosted"
    val balancerName = "RUS"

    val classifierParams = Map[String, String](
      "iters" -> "250"
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
