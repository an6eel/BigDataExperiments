package main.scala.an6eel

import org.apache.spark.mllib.feature.{HME_BD, FCNN_MR, RNG_BD}

object Preprocess extends scala.AnyRef{

  def ROS(train: DataSet, overRate: Double): DataSet = {
    var oversample: DataSet = train.sparkContext.emptyRDD

    val train_positive = train.filter(_.label == 1)
    val train_negative = train.filter(_.label == 0)
    val num_neg = train_negative.count().toDouble
    val num_pos = train_positive.count().toDouble

    if (num_pos > num_neg) {
      val fraction = (num_pos * overRate) / num_neg
      oversample = train_positive.union(train_negative.sample(withReplacement = true, fraction))
    } else {
      val fraction = (num_neg * overRate) / num_pos
      oversample = train_negative.union(train_positive.sample(withReplacement = true, fraction))
    }
    oversample.repartition(train.getNumPartitions)
  }

  def RUS(train: DataSet): DataSet = {
    var undersample: DataSet = train.sparkContext.emptyRDD

    val train_positive = train.filter(_.label == 1)
    val train_negative = train.filter(_.label == 0)
    val num_neg = train_negative.count().toDouble
    val num_pos = train_positive.count().toDouble

    if (num_pos > num_neg) {
      val fraction = num_neg / num_pos
      undersample = train_negative.union(train_positive.sample(withReplacement = false, fraction))
    } else {
      val fraction = num_pos / num_neg
      undersample = train_positive.union(train_negative.sample(withReplacement = false, fraction))
    }
    undersample
  }

  def HME_BD(train: DataSet): DataSet = {
    val nTrees = 200
    val maxDepthRF = 5
    val partition = 10

    val model = new HME_BD(train, nTrees, partition, maxDepthRF, 48151623)
    val cleanData = model.runFilter()
    cleanData.persist
    cleanData.count
    cleanData
  }

  def RNG_BD(train: DataSet): DataSet = {
    val order = true
    val selType = order
    val model = new RNG_BD(train, order, selType)
    val cleanData = model.runFilter()
    cleanData.persist
    cleanData.count
    cleanData
  }

  def FCNN_MR(train: DataSet): DataSet = {
    val k = 5
    val model = new FCNN_MR(train, k)
    val cleanData = model.runPR()
    cleanData.persist
    cleanData.count
    cleanData
  }

  def ROS_HME_BD(train: DataSet, overRate: Double): DataSet = {
    val sampled = ROS(train, overRate)
    HME_BD(sampled)
  }

  def RUS_HME_BD(train: DataSet): DataSet = {
    val sampled = RUS(train)
    HME_BD(sampled)
  }

  def ROS_RNG_BD(train:DataSet, overRate: Double): DataSet = {
    val sampled = ROS(train, overRate)
    RNG_BD(sampled)
  }

  def RUS_RNG_BD(train: DataSet): DataSet = {
    val sampled = RUS(train)
    RNG_BD(sampled)
  }

  def ROS_FCNN(train:DataSet, overRate: Double): DataSet = {
    val sampled = ROS(train, overRate)
    FCNN_MR(sampled)
  }

  def RUS_FCNN(train: DataSet): DataSet = {
    val sampled = RUS(train)
    FCNN_MR(sampled)
  }

  def defaultPreprocess(train: DataSet): DataSet = train

  def apply(name: String, overRate: Double = 1.0) = (data: DataSet) => {
    name match {
      case "ROS" => ROS(data, overRate)
      case "RUS" => RUS(data)
      case "HME_BD" => HME_BD(data)
      case "RNG_ND" => RNG_BD(data)
      case "FCNN_MR" => FCNN_MR(data)
      case "ROS+HME" => ROS_HME_BD(data, overRate)
      case "RUS+HME" => RUS_HME_BD(data)
      case "ROS+RNG" => ROS_RNG_BD(data, overRate)
      case "RUS+RNG" => RUS_RNG_BD(data)
      case "ROS+FCNN" => ROS_FCNN(data, overRate)
      case "RUS+FCNN" => RUS_FCNN(data)
      case _ => defaultPreprocess(data)
    }
  }
}