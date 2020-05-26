package main.scala.an6eel.experiments

import java.io.PrintWriter

import main.scala.an6eel._

object Experiment2 {
  def main(arg: Array[String]): Unit = {

    val jobName = "DT-FCNN_MR-full-25D-32B"
    val errorLevel = "ERROR"
    val classifierName = "DecisionTree"
    val balancerName = "FCNN_MR"

    val classifierParams = Map[String, String](
      "depth" -> "25",
      "bins" -> "32"
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
