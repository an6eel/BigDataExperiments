package main.scala.an6eel

package object experiments {
  val VM = false
  val sample = if (VM) 0.1 else 1.0
  val PATH = if (VM) "/home/spark/" else "/home/x76592732/experimentsResults/"

  val pathTrain = if (VM) {
    "file:///home/spark/datasets/susy-10k-tra.data"
  } else {
    "/user/datasets/master/higgs/higgsMaster-Train.data"
  }
  val pathTest = if(VM) {
    "file:///home/spark/datasets/susy-10k-tst.data"
  } else {
    "/user/datasets/master/higgs/higgsMaster-Test.data"
  }
}
