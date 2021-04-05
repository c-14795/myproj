package my.learn
import scala.collection.immutable.ListMap
import scala.collection.mutable
import scala.collection.parallel.immutable
import scala.util.control.Breaks._

object Obj1 {


  (a:Int,b:Int)=>if (a>b) a else b

  def main(args: Array[String]) = {
        println("gggg")

    val s="bcaacbc"
    val sf= s.distinct.sorted
    val distinctChars = s.distinct.toCharArray
    val len = distinctChars.length
    val map = scala.collection.mutable.Map[String,Int]()
    for (i <- 0 until s.length) {

      if(s.substring(i,s.length).distinct.sorted.equals(sf))
        {
          map.put(s.substring(i,s.length),s.substring(i,s.length).length)
        }

    }
  println(map)
    println(map.toSeq.sortWith(_._2 < _._2)(0)._2)
  }
}
