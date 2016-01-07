import KMeansClustering.delegateKMeans
import org.apache.spark.{SparkContext, SparkConf}

/**此项目专注Spark下MLlib的聚类算法，会将目前库中的4个聚类算法汇总并进行详细的说明
 * Created by Flyln on 16/1/6.
 */
object Entrance {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("Clustering")
    val sc = new SparkContext(conf)

    val data = sc.textFile("")

    GMM.myGMMModel.gmmModel(data)

    new delegateKMeans().kValueCost(data)


  }

}
