package com.example.marvel.utils

import java.math.BigInteger
import java.security.MessageDigest

object Constants {
        const val BASE_URL = "https://gateway.marvel.com/"
        const val TIME = "1"
        const val PUBLIC_KEY = "0cff46d6073f696863bdbcf7ea1ee61c"
        const val PRIVATE_KEY = "a580ad2e8c72ab6d5168ac72838b898b60d03a44"
        fun hash(): String {
            val input = "$TIME$PRIVATE_KEY$PUBLIC_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16)
        }
}
