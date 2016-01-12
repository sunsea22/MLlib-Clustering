package BiseKMeans

import org.apache.spark.mllib.clustering.BisectingKMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD

/**
 * Created by Flyln on 16/1/11.
 * 二分K均值(bisecting k-means)是spark1.6新加进的聚类算法，主要是为了解决k-means算法对初始k个质心点得选取比较敏感这个缺点
 * 其主要思想是：首先将所有点作为一个簇，然后将该簇一分为二。之后选择能最大程度降低聚类代价函数(也就是误差平方和)的簇划分为两个簇
 * 以此进行下去，直到簇的数目等于用户给定的数目K为止
 * 初始的聚类个数设置为4
 */
object entrance {

  def biKMeansClustering(rdd: RDD[String]): RDD[String] = {
    val data = rdd.map(s => Vectors.dense(s.trim.split(",").map(_.toDouble)))
    val bkm = new BisectingKMeans().setK(2)
    val model = bkm.run(data)

    println(s"Compute Cost: ${model.computeCost(data)}")  //计算聚类代价，该值越小表示聚类效果越好，可以根据该值确定聚类的个数

    model.clusterCenters.zipWithIndex.foreach{
      case (center,idx) => println(s"Cluster Center ${idx}: ${center}") //打印聚类中心
    }

    data.map(line => {
      val predictedClusterIndex:Int = model.predict(line)
      line.toString + "," + predictedClusterIndex
    })

  }
}
