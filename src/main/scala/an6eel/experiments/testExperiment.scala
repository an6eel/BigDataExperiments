package main.scala.an6eel.experiments

import java.io.PrintWriter

import main.scala.an6eel._

object testExperiment {
  def main(arg: Array[String]): Unit = {

    val jobName = "testExperiment"
    val errorLevel = "ERROR"
    val classifierName = "DecisionTree"
    val balancerName = "default"
    val overRate = 0.7

    // Default Params
    val classifierParams = Map[String, String](

    )
    val classifier = Classifier(classifierName, classifierParams)
    val balancer = Preprocess(balancerName, overRate)

    val firstExperiment = Experiment(jobName, errorLevel, pathTrain, pathTest, classifier, balancer, sample)

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
