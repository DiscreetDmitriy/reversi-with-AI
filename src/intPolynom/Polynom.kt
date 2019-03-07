package intPolynom

import kotlin.math.pow


class Polynom(vararg consts: Int) {
    val coefficientsArray = if (consts.isEmpty()) intArrayOf(0)
    else consts.dropWhile { it == 0 && it != consts.last() }.toIntArray()

    operator fun plus(other: Polynom): Polynom {
        val list = coefficientsArray.toMutableList()
        val otherList = other.coefficientsArray.toMutableList()

        while (list.size < otherList.size) list.add(0, 0)
        while (list.size > otherList.size) otherList.add(0, 0)

        return Polynom(*list.mapIndexed { index, value -> value + otherList[index] }.toIntArray())
    }


    operator fun minus(other: Polynom): Polynom {
        val list = coefficientsArray.toMutableList()
        val otherList = other.coefficientsArray.toMutableList()

        while (list.size < otherList.size) list.add(0, 0)
        while (list.size > otherList.size) otherList.add(0, 0)

        return Polynom(*list.mapIndexed { index, value -> value - otherList[index] }.toIntArray())
    }


    fun powerToConst(): Map<Int, Int> =
        coefficientsArray.mapIndexed { index, v -> coefficientsArray.size - index - 1 to v }.toMap()


    operator fun times(other: Polynom): Polynom {
        val res = mutableMapOf<Int, Int>()

        for ((k, v) in this.powerToConst())
            for ((k2, v2) in other.powerToConst())
                res[k + k2] = res.getOrDefault((k + k2), 0) + v * v2

        return Polynom(*res.values.toIntArray())
    }


    infix fun divToMod(other: Polynom): Pair<Polynom, Polynom> {
        var map = this.powerToConst()
        val otherMap = other.powerToConst()

        var newPolynom = this
        var leadPower = map.keys.first()
        val otherLeadPower = otherMap.keys.first()

        val divResult = mutableMapOf<Int, Int>()
        val modResult = mutableMapOf<Int, Int>()

        if (leadPower < otherLeadPower) return Polynom() to Polynom()

        while (leadPower > otherLeadPower) {
            var powerDifference = leadPower - otherLeadPower

            divResult[powerDifference] = map.values.first() / otherMap.values.first()

            val array = otherMap.values.toMutableList()
            while (powerDifference > 0) {
                array.add(0)
                powerDifference--
            }

            newPolynom -= Polynom(map.values.first() / otherMap.values.first()) * Polynom(*array.toIntArray())
            modResult[leadPower] = newPolynom.powerToConst()[leadPower] ?: 0
            if (newPolynom.coefficientsArray.size - 1 == leadPower)
                newPolynom = Polynom(*newPolynom.coefficientsArray.drop(1).toIntArray())

            map = newPolynom.powerToConst()
            leadPower--
        }

        try {
            divResult[0] = map.values.first() / otherMap.values.first()
        } catch (e: Exception) {
            return Polynom() to Polynom()
        }

        newPolynom -= Polynom(map.values.first() / otherMap.values.first()) * other
        if (map.size - newPolynom.coefficientsArray.size == 1) modResult[map.size - 1] = 0
        map = newPolynom.powerToConst()
        map.forEach { modResult[it.key] = modResult.getOrDefault(it.key, 0) + it.value }

        return Polynom(*divResult.values.toIntArray()) to Polynom(*modResult.values.dropWhile { it == 0 }.toIntArray())
    }


    operator fun div(other: Polynom) = (this divToMod other).first

    operator fun rem(other: Polynom) = (this divToMod other).second


    infix fun count(x: Int) = coefficientsArray
        .reversed()
        .mapIndexed { index, acc -> acc * x.toDouble().pow(index).toInt() }
        .sum()


    override fun equals(other: Any?): Boolean =
        other is Polynom && coefficientsArray.contentEquals(other.coefficientsArray)

    override fun hashCode(): Int = this.coefficientsArray.contentHashCode()


    override fun toString(): String {

        fun Int.isPrintedConst(): String? = when (this) {
            0 -> null
            1 -> " + "
            -1 -> " - "
            else -> if (this > 0) " + $this" else " - ${-this}"
        }

        fun Int.isPrintedX(): String = if (this > 1) "x^$this" else if (this == 1) "x" else ""

        val powersToConsts = this.powerToConst()
        if (powersToConsts == mapOf(0 to 0)) return "0"
        if (powersToConsts == mapOf(0 to 1)) return "1"

        val sb = StringBuilder()

        // выводим первый коэффициент и первый х
        // делаем это не в цикле потому что перед коэф. не должно быть знака
        // если это не отрицательное число,  конечно
        val firstConst = powersToConsts.values.first()
        val firstConstStr = if (firstConst == 1) "" else "$firstConst"
        val firstX = powersToConsts.keys.first().isPrintedX()
        sb.append(firstConstStr + firstX)

        var middlePowersToConsts = powersToConsts.toList().drop(1).toMap()

        // также не в цикле считаем последнее число
        // потому что оно пишется без х
        // и его нужно писать, даже если оно равно единице
        val lastConst =
            if (middlePowersToConsts.isNotEmpty()) middlePowersToConsts.values.last() else return sb.toString()
        val lastConStr = when {
            lastConst == 0 -> ""
            lastConst > 0 -> " + $lastConst"
            else -> " - ${-lastConst}"
        }

        middlePowersToConsts = middlePowersToConsts.toList().dropLast(1).toMap()

        // если коэф. равен нулю, то цикл переходит к следующей степени
        for ((power, const) in middlePowersToConsts)
            sb.append("${const.isPrintedConst() ?: continue}${power.isPrintedX()}")

        sb.append(lastConStr)

        return sb.toString()
    }

}
