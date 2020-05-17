package main.scala.an6eel

import org.apache.spark.SparkContext
import org.apache.spark.mllib.tree.configuration.BoostingStrategy
import org.apache.spark.mllib.tree.{DecisionTree, GradientBoostedTrees, PCARD, RandomForest}

trait Classifier {
  def trainAndPredict(train: DataSet, test: DataSet, context: SparkContext): Predictions
}

class DTClassifier(params: ClassifierParams) extends Classifier {

  override def trainAndPredict(dataSet: DataSet, test: DataSet, context: SparkContext): Predictions = {
    val numClasses = params.getOrElse("numClasses", "2").toInt
    val categoricalFeaturesInfo = Map[Int, Int]()
    val impurity = params.getOrElse("impurity", "gini")
    val maxDepth = params.getOrElse("depth", "5").toInt
    val maxBins = params.getOrElse("bins", "32").toInt
    val model = DecisionTree.trainClassifier(dataSet, numClasses, categoricalFeaturesInfo, impurity, maxDepth, maxBins)

    val labelAndPredsDT = test.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    labelAndPredsDT
  }

}

class RFClassifier(params: ClassifierParams) extends Classifier {

  override def trainAndPredict(dataSet: DataSet, test: DataSet, context: SparkContext): Predictions = {
    val numClasses = params.getOrElse("numClasses", "2").toInt
    val categoricalFeaturesInfo = Map[Int, Int]()
    val numTrees = params.getOrElse("trees", "100").toInt
    val featureSubsetStrategy = params.getOrElse("subsetStrategy", "auto")
    val impurity = params.getOrElse("impurity", "gini")
    val maxDepth = params.getOrElse("depth", "5").toInt
    val maxBins = params.getOrElse("bins", "32").toInt
    val model = RandomForest.trainClassifier(
      dataSet, numClasses, categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins
    )

    val labelAndPredsRF = test.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    labelAndPredsRF
  }
}

class GBTClassifier(params: ClassifierParams) extends Classifier {

  override def trainAndPredict(train: DataSet, test: DataSet, context: SparkContext): Predictions = {
    val boostingStrategy = BoostingStrategy.defaultParams("Classification")
    boostingStrategy.numIterations = params.getOrElse("iters","100").toInt
    boostingStrategy.treeStrategy.numClasses = params.getOrElse("numClasses", "2").toInt
    boostingStrategy.treeStrategy.maxDepth = params.getOrElse("depth", "5").toInt
    boostingStrategy.treeStrategy.categoricalFeaturesInfo = Map[Int, Int]()

    val model = GradientBoostedTrees.train(train, boostingStrategy)

    val labelAndPredsGBT = test.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    labelAndPredsGBT
  }
}

object Classifier extends scala.AnyRef{

  def apply(name: String, params: ClassifierParams): Classifier = name match {
    case "DecisionTree" => new DTClassifier(params)
    case "RandomForest" => new RFClassifier(params)
    case "GradientBoosted" => new GBTClassifier(params)
    case _ =>
      throw new Exception("Classifier not defined")
  }
}