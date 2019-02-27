package intPolynom

import kotlin.math.pow


class Polynom(vararg consts: Int) {
    val coefficients: IntArray = if (consts.isEmpty()) intArrayOf(0) else consts

    operator fun unaryMinus(): Polynom = Polynom(*coefficients.map { -it }.toIntArray())

    operator fun plus(other: Polynom): Polynom =
        if (coefficients.size >= other.coefficients.size)
            Polynom(*coefficients.mapIndexed { index, value -> value + other.coefficients[index] }.toIntArray())
        else
            Polynom(*other.coefficients.mapIndexed { index, value -> value + coefficients[index] }.toIntArray())

    operator fun minus(other: Polynom): Polynom {
        val coefficients = coefficients.toMutableList()
        while (coefficients.size < other.coefficients.size) coefficients.add(0, 0)
        return Polynom(*coefficients.mapIndexed { index, value -> value - other.coefficients[index] }.toIntArray())
    }

    fun powerToConst(): Map<Int, Int> =
        coefficients.mapIndexed { index, v -> coefficients.size - index - 1 to v }.toMap()

    operator fun times(other: Polynom): Polynom {
        val res = mutableMapOf<Int, Int>()

        for ((k, v) in this.powerToConst())
            for ((k2, v2) in other.powerToConst())
                res[k + k2] = res.getOrDefault((k + k2), 0) + v * v2

        return Polynom(*res.values.toIntArray())
    }

    operator fun div(other: Polynom): Polynom {
        val map = this.powerToConst().toMap()
        val otherMap = other.powerToConst().toMap()
        val divResult = mutableMapOf<Int, Int>()
        var leadPower = map.keys.first()
        var otherLeadPower = otherMap.keys.first()
        println(map)

        if (leadPower < otherLeadPower) return Polynom()

        while (leadPower >= otherLeadPower) {

        }

        print(divResult)
        return Polynom(*divResult.values.toIntArray())

    }

    infix fun count(x: Int) =
        coefficients
            .mapIndexed { index, acc -> acc * x.toDouble().pow(coefficients.size - index - 1).toInt() }.sum()


    override fun equals(other: Any?): Boolean =
        other is Polynom &&
                coefficients.size == other.coefficients.size &&
                coefficients.contentEquals(other.coefficients)

    override fun hashCode(): Int {
        var result = 0
        for (i in coefficients)
            result = result * 31 + coefficients[i]
        return result
    }

    override fun toString(): String {

        fun Int.isPrintedConst(): String? = when (this) {
            0 -> null
            1 -> " + "
            -1 -> " - "
            else -> if (this > 0) " + $this" else " - ${-this}"
        }

        fun Int.isPrintedX(): String = if (this > 1) "x^$this" else if (this == 1) "x" else ""

        // отбрасываем первые незначащие нули
        val powersToConsts = this.powerToConst().toList().dropWhile { it.second == 0 }.toMap()
        if (powersToConsts.isEmpty()) return "0"

        val sb = StringBuilder()

        // выводим первый коэффициент и первый х
        // делаем это не в цикле потому что перед коэф. не должно быть знака
        // если это не отрицатеоьное число,  конечно
        val firstConst: Int = powersToConsts.values.first()
        val firstConstStr: String = if (firstConst in 0..1) "" else "$firstConst"
        val firstX: String = powersToConsts.keys.first().isPrintedX()
        sb.append(firstConstStr + firstX)

        var genericPowersToConsts = powersToConsts.toList().drop(1).toMap()

        // также не в цикле считаем последнее число
        // потому что оно пишется без х
        // и его нужно писать даже если оно равно единице
        val lastConst: Int =
            if (genericPowersToConsts.isNotEmpty()) genericPowersToConsts.values.last() else return sb.toString()
        val lastConStr: String = when (lastConst) {
            0 -> ""
            1 -> " + 1"
            -1 -> " - 1"
            else -> if (lastConst > 0) " + $lastConst" else " - ${-lastConst}"
        }

        genericPowersToConsts = genericPowersToConsts.toList().dropLast(1).toMap()

        // если коэф. равен нулю, то цикл переходит к следуюощей степени
        for ((power, const) in genericPowersToConsts)
            sb.append("${const.isPrintedConst() ?: continue}${power.isPrintedX()}")

        // проверка, не ялвяется ли последнее число единственным коэффициентом
        sb.append(if (sb.isEmpty()) lastConst else lastConStr)

        return sb.toString()
    }

}
