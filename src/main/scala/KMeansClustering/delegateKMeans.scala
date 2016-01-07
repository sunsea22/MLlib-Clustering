package KMeansClustering

import org.apache.spark.mllib.clustering.{KMeansModel, KMeans}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD

/**
 * Created by Flyln on 16/1/7
 */
class delegateKMeans {


  def kmeansDemo(rdd: RDD[String]):RDD[String] = {
    val parsedTrainingData = rdd.map(s => Vectors.dense(s.split(",").map(_.toDouble))).cache()

    var clusterIndex:Int = 0 //类ID
    val numClusters = 0 //需要聚的类的个数
    val numIterations = 30 //方法单次运行最大的迭代次数
    val numRuns = 5 //算法被运行的次数


    val clusters = KMeans.train(parsedTrainingData,numClusters,numIterations,numRuns)

    println("Cluster Number:" + clusters.clusterCenters.length)

    println("Cluster Centers Information Overview:")
    clusters.clusterCenters.foreach(x => {
      println("Center Point of Cluster  " + clusterIndex + ":")

      println(x)
      clusterIndex += 1
    })

    parsedTrainingData.map(testDataLine => {
      val predictedClusterIndex:Int = clusters.predict(testDataLine)
      testDataLine.toString + "," + predictedClusterIndex
    })

  }

  /**
   * 计算K消耗值，确定聚类个数
   */
  def kValueCost(rdd: RDD[String]) = {
    val parsedTrainingData = rdd.map(s => Vectors.dense(s.split(",").map(_.toDouble))).cache()
    val ks:Array[Int] = (2 to 20).toArray
    val a = ks.map(cluster => {
      val model:KMeansModel = KMeans.train(parsedTrainingData,cluster,30,5)
      val ssd = model.computeCost(parsedTrainingData)
      cluster + "->" + ssd
    })
    println(a.mkString("\n"))
  }

}
