package org.starcoin.sirius.core

import com.google.protobuf.ByteString
import kotlinx.serialization.*
import org.starcoin.sirius.crypto.CryptoService
import org.starcoin.sirius.serialization.BinaryDecoder
import org.starcoin.sirius.serialization.BinaryEncoder
import org.starcoin.sirius.util.KeyPairUtil
import org.starcoin.sirius.util.Utils
import java.security.PrivateKey
import java.security.PublicKey

@Serializable
class Signature private constructor(private val bytes: ByteArray) {

    fun verify(data: ByteArray, publicKey: PublicKey): Boolean {
        return CryptoService.verify(data, this, publicKey)
    }

    override fun toString(): String {
        return Utils.HEX.encode(this.bytes)
    }

    fun toBytes() = this.bytes.copyOf()

    fun toByteString(): ByteString = ByteString.copyFrom(this.bytes)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Signature) return false

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }

    @Serializer(forClass = Signature::class)
    companion object : KSerializer<Signature> {

        override fun deserialize(input: Decoder): Signature {
            return when (input) {
                is BinaryDecoder -> wrap(input.decodeByteArray())
                else -> this.wrap(input.decodeString())
            }
        }

        override fun serialize(output: Encoder, obj: Signature) {
            when (output) {
                is BinaryEncoder -> output.encodeByteArray(obj.bytes)
                else -> output.encodeString(obj.toString())
            }
        }

        fun wrap(hexString: String): Signature {
            return wrap(Utils.HEX.decode(hexString))
        }

        fun wrap(sign: ByteArray): Signature {
            return Signature(sign)
        }

        fun wrap(byteString: ByteString): Signature {
            return Signature(byteString.toByteArray())
        }

        fun of(privateKey: PrivateKey, data: ByteArray): Signature {
            return Signature(KeyPairUtil.signData(data, privateKey))
        }

    }

}
