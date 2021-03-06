package top.myetl

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import top.myetl.lucenerdd.convert.{BeanToDoc, DocToBean}
import top.myetl.lucenerdd.rdd.{IndexReadRDD, LuceneRDD}

import scala.reflect.ClassTag

/**
  * Created by pengda on 17/5/12.
  */
package object lucenerdd {


  /** SparkContext function , load LuceneRDD from hdfs file */

  implicit def sparkContextFunctions(sc: SparkContext) = new SparkContextFunctions(sc)

  class SparkContextFunctions(sc: SparkContext) extends Serializable{
    def luceneRDD[T: ClassTag](tableName: String)(docConversion: DocToBean[T]): LuceneRDD[T] = {
      val indexReadRDD = new IndexReadRDD(sc, tableName).cache()
      new LuceneRDD[T](indexReadRDD)(docConversion)
    }

  }


  /** RDD function, save the RDD as Lucene format */
//  implicit def sparkRDDFunctions[T : ClassTag](rdd: RDD[T]) = new SparkRDDFunctions[T](rdd)
//
//  class SparkRDDFunctions[T : ClassTag](rdd: RDD[T]) extends Serializable {
//
//    def saveToLucene(tableName: String)(docConversion: BeanToDoc[T]): LuceneWriteRDD[T] = {
//      new LuceneWriteRDD[T](rdd, tableName)(docConversion)
//    }
//
//  }




}
