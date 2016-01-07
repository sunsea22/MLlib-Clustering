package GMM

import org.apache.spark.mllib.clustering.GaussianMixture
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD

/**
 * Created by Flyln on 16/1/6.
 */
object myGMMModel {

  def gmmModel(rdd: RDD[String]):RDD[String] = {
    val parsedData = rdd.map(s => Vectors.dense(s.trim.split(",").map(_.toDouble))).cache()

    val gmm = new GaussianMixture().setK(2).run(parsedData)

    for (i <- 0 until gmm.k) {
      println("weight=%f\nmu=%s\nsigma=\n%s\n" format(gmm.weights(i),gmm.gaussians(i).mu,gmm.gaussians(i).sigma))
    }

    parsedData.map(testDataLine => {
      val predictedClusterIndex:Int = gmm.predict(testDataLine)
      testDataLine.toString + "," + predictedClusterIndex
    })
  }
}
