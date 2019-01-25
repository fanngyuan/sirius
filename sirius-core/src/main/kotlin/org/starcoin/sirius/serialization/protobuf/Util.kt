package org.starcoin.sirius.serialization.protobuf

import kotlinx.io.ByteBuffer
import kotlinx.io.IOException
import kotlinx.io.InputStream


internal fun <T> List<T>.onlySingleOrNull() = when (this.size) {
    0 -> null
    1 -> this[0]
    else -> throw IllegalStateException("Too much arguments in list")
}

internal fun InputStream.readExactNBytes(bytes: Int): ByteArray {
    val array = kotlin.ByteArray(bytes)
    var read = 0
    while (read < bytes) {
        val i = this.read(array, read, bytes - read)
        if (i == -1) throw IOException("Unexpected EOF")
        read += i
    }
    return array
}

internal fun InputStream.readToByteBuffer(bytes: Int): ByteBuffer {
    val arr = readExactNBytes(bytes)
    val buf = kotlinx.io.ByteBuffer.allocate(bytes)
    buf.put(arr).flip()
    return buf
}

object HexConverter {
    fun parseHexBinary(s: String): ByteArray {
        val len = s.length

        if (len % 2 != 0) {
            throw IllegalArgumentException("HexBinary string must be even length")
        }

        val bytes = ByteArray(len / 2)
        var i = 0

        while (i < len) {
            val h = hexToInt(s[i])
            val l = hexToInt(s[i + 1])
            if (h == -1 || l == -1) {
                throw IllegalArgumentException("Invalid hex chars: ${s[i]}${s[i + 1]}")
            }

            bytes[i / 2] = ((h shl 4) + l).toByte()
            i += 2
        }

        return bytes
    }

    private fun hexToInt(ch: Char): Int = when (ch) {
        in '0'..'9' -> ch - '0'
        in 'A'..'F' -> ch - 'A' + 10
        in 'a'..'f' -> ch - 'a' + 10
        else -> -1
    }

    private val hexCode = "0123456789ABCDEF"

    fun printHexBinary(data: ByteArray, lowerCase: Boolean = false): String {
        val r = StringBuilder(data.size * 2)
        for (b in data) {
            r.append(hexCode[b.toInt() shr 4 and 0xF])
            r.append(hexCode[b.toInt() and 0xF])
        }
        return if (lowerCase) r.toString().toLowerCase() else r.toString()
    }

    fun toHexString(n: Int) = printHexBinary(ByteBuffer.allocate(4).putInt(n).flip().array(), true)
        .trimStart('0').takeIf { it.isNotEmpty() } ?: "0"
}

fun ByteBuffer.getUnsignedByte(): Int = this.get().toInt() and 0xff
fun ByteBuffer.getUnsignedShort(): Int = this.getShort().toInt() and 0xffff
fun ByteBuffer.getUnsignedInt(): Long = this.getInt().toLong() and 0xffffffffL
