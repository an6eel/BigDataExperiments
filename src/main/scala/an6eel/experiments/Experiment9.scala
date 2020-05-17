package main.scala.an6eel.experiments

import java.io.PrintWriter

import main.scala.an6eel._

object Experiment9 {
  def main(arg: Array[String]): Unit = {

    val jobName = "RF-ROS-HME-BD-full-1.0-20D-64B-300T"
    val errorLevel = "ERROR"
    val classifierName = "RandomForest"
    val balancerName = "ROS+HME"
    val overRate = 1.0

    val classifierParams = Map[String, String](
      "depth" -> "20",
      "bins" -> "64",
      "trees" -> "300"
    )
    val classifier = Classifier(classifierName, classifierParams)
    val preprocess = Preprocess(balancerName, overRate)

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