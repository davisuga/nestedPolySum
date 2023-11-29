//> using scala 3
import scala.language.implicitConversions
import scala.reflect.ClassTag

implicit def longToInt(s: BigDecimal): Int = s.toInt

def func(x: BigDecimal) =
  6e-40
    + 2e-40 * x
    - 3e-40 * x * x
    - 3e-40 * x * x * x
    - 3e-40 * x * x * x * x
    + 1e-40 * x * x * x * x * x

def updateMatrix[T: ClassTag](
    matrix: Array[Array[T]],
    i: BigDecimal,
    j: BigDecimal,
    v: T
) =
  matrix.updated(i.toInt, matrix(i.toInt).updated(j.toInt, v))

def memo(
    a: BigDecimal,
    x: BigDecimal,
    k: BigDecimal,
    cache: Array[Array[BigDecimal]]
) =
  if (k == 0) updateMatrix(cache, x, k, func(x))
  else if (x == a) updateMatrix(cache, x, k, cache(x)(k - 1))
  else updateMatrix(cache, x, k, cache(x - 1)(k) + cache(x)(k - 1))

def loopOuter(
    j: BigDecimal,
    k: BigDecimal,
    a: BigDecimal,
    b: BigDecimal,
    cache: Array[Array[BigDecimal]]
): Array[Array[BigDecimal]] =
  if (j > k) cache
  else
    val updatedCache = (a to b)
      .by(1)
      .foldLeft(cache): (acc, i) =>
        memo(a, i, j, acc)
    loopOuter(j + 1, k, a, b, updatedCache)

@main def main() =
// @main def main(a: Long = 1, b: Long = 100, k: Long = 300) =
  val a: BigDecimal = 1
  val b: BigDecimal = 100
  val k: BigDecimal = 300
  val cache = Array.ofDim[BigDecimal]((b + 1).toInt, (k + 1).toInt)
  //   warm up
  val now1 = System.currentTimeMillis()

  loopOuter(0, k, a, b, cache)
  val end1 = System.currentTimeMillis()

  println(s"Time: ${end1 - now1}ms")

  // val now = System.currentTimeMillis()
  NestedPolySumSc.run(Array("1", "100", "300"))

  // val result = loopOuter(0, k, a, b, cache)
  // val end = System.currentTimeMillis()

  // println(s"Time: ${end - now}ms")
  // println(result(b.toInt)(k.toInt))

object NestedPolySumSc {
  val iterLim = 10000
  val kLim = 10000

  var MEM: Array[Array[Double]] = Array.ofDim[Double](iterLim, kLim)

  def func(x: Long): Double = {
    6e-40 +
      2e-40 * x -
      3e-40 * x * x -
      3e-40 * x * x * x -
      3e-40 * x * x * x * x +
      1e-40 * x * x * x * x * x
  }

  def memoization(a: Long, x: Long, k: Long): Unit = {
    if (k == 0) {
      MEM(x.toInt)(0) = func(x)
    } else if (x == a) {
      MEM(a.toInt)(k.toInt) = MEM(a.toInt)(k.toInt - 1)
    } else {
      MEM(x.toInt)(k.toInt) =
        MEM(x.toInt - 1)(k.toInt) + MEM(x.toInt)(k.toInt - 1)
    }
  }

  def run(args: Array[String]): Unit = {
    val a = args(0).toLong
    val b = args(1).toLong
    val k = args(2).toLong
    val now = System.currentTimeMillis()

    // val result = loopOuter(0, k, a, b, cache)
    for (j <- 0 to k.toInt) {
      for (i <- a.toInt to b.toInt) {
        memoization(a, i, j)
      }
    }
    val end = System.currentTimeMillis()

    println(s"Time: ${end - now}ms")

    println(MEM(b.toInt)(k.toInt))
  }
}
