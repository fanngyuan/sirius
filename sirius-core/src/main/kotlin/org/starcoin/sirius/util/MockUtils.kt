package org.starcoin.sirius.util

import org.apache.commons.lang3.RandomUtils
import java.math.BigInteger


object MockUtils {

    const val MAX_LONG = Int.MAX_VALUE / 100.toLong()
    const val MAX_INT = Int.MAX_VALUE / 1000

    fun nextLong(): Long {
        return RandomUtils.nextLong(0, MAX_LONG)
    }

    fun nextLong(startInclusive: Long, endExclusive: Long): Long {
        return RandomUtils.nextLong(startInclusive, endExclusive)
    }

    fun nextBigInteger(): BigInteger {
        return RandomUtils.nextLong().toBigInteger()
    }

    fun nextBigInteger(startInclusive: BigInteger, endExclusive: BigInteger): BigInteger {
        return RandomUtils.nextLong(startInclusive.longValueExact(), endExclusive.longValueExact()).toBigInteger()
    }

    fun nextInt(): Int {
        return RandomUtils.nextInt(0, MAX_INT)
    }

    fun nextInt(startInclusive: Int, endExclusive: Int): Int {
        return RandomUtils.nextInt(startInclusive, endExclusive)
    }

    fun nextBoolean() = RandomUtils.nextBoolean()

    fun nextBytes(count: Int) = RandomUtils.nextBytes(count)

}
