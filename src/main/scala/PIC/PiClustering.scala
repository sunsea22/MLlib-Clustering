package PIC

import org.apache.spark.mllib.clustering.PowerIterationClustering
import org.apache.spark.rdd.RDD

/**
 * 快速迭代聚类算法适用在对图的节点的聚类上
 * 通过两个节点的相似度进行聚类，这个相似度也就是两个节点的连边的属性
 * 相似度非负，如果在输入数据中没有出现一些节点，则视这些节点的相似度为0
 * 且一对节点在输入数据中至多出现一次
 *
 */
object PiClustering {

  def powerCluster(rdd: RDD[String]):RDD[String] = {
    val similarities = rdd.map { line =>
      val parts = line.split(" ")
      (parts(0).toLong, parts(1).toLong, parts(2).toDouble)
    }

    /**
     * 参数设置
     * k: 聚类的个数
     * maxIteration：最大的迭代次数
     * initializationModel：初始化模型，默认设置为随机random，使用节点属性的随即向量；或者degree，使用标准化的相似度总和
     */
    val pic = new PowerIterationClustering().setK(2).setMaxIterations(10)
    val model = pic.run(similarities)

    model.assignments.map(a => s"${a.id} -> ${a.cluster}")
  }
}
