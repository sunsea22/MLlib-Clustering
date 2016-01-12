import KMeansClustering.delegateKMeans
import org.apache.spark.{SparkContext, SparkConf}

/**此项目专注Spark下MLlib的聚类算法，会将目前库中的4个聚类算法汇总并进行详细的说明
 * Created by Flyln on 16/1/6.
  * spark1.6对内存做了优化，刚开始运行的时候可能会出现Please use a larger heap size这个问题，把JVM的初始堆大小设置大一点问题就可以解决了
 */
object Entrance {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("Clustering")
    val sc = new SparkContext(conf)

    val data = sc.textFile("")

    //val clusterResult = GMM.myGMMModel.gmmModel(data)

    //new delegateKMeans().kValueCost(data)  //GMM模型
    //val clusterResult = new delegateKMeans().kmeansDemo(data)  //KMeans
    //BiseKMeans.entrance.biKMeansClustering(data).repartition(1).saveAsTextFile("")//二分K均值
    //clusterResult.repartition(1).saveAsTextFile("")


  }

}
