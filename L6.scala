package pdgm21
import scala.collection.mutable.ListBuffer
trait Debug{
    def debugName(): Unit ={
        println(getClass.getSimpleName)
    }

    def debugVars(): List[Any] = {
        def helper(list: List[java.lang.reflect.Field], res: List[Any]): List[Any] =
        {
            list match {
                case head :: tail =>  head.setAccessible(true)
                    val temp = List(head.getName(), head.getType().getName(), head.get(this))
                    head.setAccessible(false)
                    helper(tail, temp :: res)
                case Nil => res.reverse
            }
        }
        helper(getClass.getDeclaredFields.toList, Nil)
    }

}

class Point(xv: Int, yv: Int) extends Debug {
    var x: Int = xv
    var y: Int = yv
    var a: String = "test"
}


object L6 {

    def eachNElement[A](lazyList: LazyList[A],n:Int,end:Int):LazyList[A] = {
        def helper[A] (lazyList: LazyList[A],n:Int,end:Int,iter:Int,res:LazyList[A]):LazyList[A] = {
           lazyList match {
               case head #::tail => if iter >end then res.reverse
               else if iter%n==0 then helper(tail,n,end,iter+1,head#::res)
               else helper(tail,n, end, iter+1, res)
           }
        }
        helper(lazyList,n,end-1,0,LazyList())
    }

    def lazyExecute(list1: LazyList[Int],list2: LazyList[Int],char: Char):LazyList[Int] = {
        (list1,list2) match {
            case (h#::t,h1#::t1)=> char match {
                case '+' => (h+h1)#::lazyExecute(t,t1,char)
                case '-'=>(h-h1)#::lazyExecute(t,t1,char)
                case '*'=> (h*h1)#::lazyExecute(t,t1,char)
                case '/'=> (h/h1)#::lazyExecute(t,t1,char)
            }
            case(LazyList(),h#::t)=>h#::lazyExecute(LazyList(),t,char)
            case(h#::t,LazyList())=> h#::lazyExecute(t,LazyList(),char)
            case(LazyList(),LazyList())=>LazyList()
        }
    }


    def duplicate(list: List[Int],list2: List[Int]):List[Int] = {
        def helper[A](list: List[A], list2: List[Int], res: List[A]): List[A] = {
            (list, list2) match {
                case (h1 :: t1, h2 :: t2) => if h2 > 0 then helper(list, (h2-1) :: t2, h1 :: res) else helper(t1, t2, res)
                case (Nil, _) => res.reverse
                case (_, Nil) => res.reverse
            }
        }
        helper(list, list2, Nil)
    }


    def main(args: Array[String]): Unit = {
       // val l =  LazyList(5,6,3,2,1)
        //println(eachNElement(l,2,4).toList)
       // val l1 = LazyList(1,2,3)
       // val l2 = LazyList(2,3,4,5)
       // println(lazyExecute(l1,l2,'/').toList)
       var p: Point = new Point(3,4)
       println(p.debugVars())
       //println(duplicate(List(1,2,3,4,5,6),List(0,3,1,4)))
    }
}
