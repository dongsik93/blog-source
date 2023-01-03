package com.ds.encrypt

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.spec.RSAKeyGenParameterSpec
import java.util.*
import javax.crypto.Cipher

/**
 * Android KeyStore 를 이용한, encrypt/decrypt 기능을 제공
 */
class RsaKeyStore private constructor() {

    private var entry: KeyStoreEntry? = null

    init {
        entry = initialize()
    }

    private fun initialize(): KeyStoreEntry? {
        Log.d(TAG, "initialize")

        val alias = BuildConfig.APPLICATION_ID + ".rsakeypairs"
        try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)

            if (!keyStore.containsAlias(alias)) {
                initAndroidKeyStore(alias)

                Log.d(TAG, "keyStore initialize succeed.")
            } else {
                Log.d(TAG, "initialize. alias already exist.")
            }

            return KeyStoreEntryFactory.getEntry(keyStore, alias)

        } catch (e: Exception) {
            Log.e(TAG, "unexpected", e)
        }

        return null
    }

    private fun initAndroidKeyStore(alias: String) {
        Log.d(TAG, "initialize")

        val generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore")
        val spec = KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setAlgorithmParameterSpec(RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4))
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            .setDigests(KeyProperties.DIGEST_SHA512, KeyProperties.DIGEST_SHA384, KeyProperties.DIGEST_SHA256)
            .setUserAuthenticationRequired(false) // MUST BE false
            .build()

        generator.apply {
            initialize(spec)
            generateKeyPair()
        }
    }

    /**
     * Encrypt the bytes
     * @param bytes
     */
    fun encrypt(bytes: ByteArray?): String? {

        var encryptedBytes: ByteArray? = null
        var base64EncryptedBytes: ByteArray? = null

        try {
            if (entry == null) {
                Log.e(TAG, "encrypt. Entry is null")
                return null
            }

            if (bytes == null) {
                Log.e(TAG, "encrypt. input param is null")
                return null
            }

            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, entry!!.publicKey)

            encryptedBytes = cipher.doFinal(bytes)
            base64EncryptedBytes = Base64.encode(encryptedBytes, Base64.DEFAULT)

            return String(base64EncryptedBytes, StandardCharsets.UTF_8)

        } catch (e: Exception) {
            Log.e(TAG, "encrypt. unexpected", e)

        } finally {
            if (bytes != null) {
                Arrays.fill(bytes, 0.toByte())
            }

            if (encryptedBytes != null) {
                Arrays.fill(encryptedBytes, 0.toByte())
            }

            if (base64EncryptedBytes != null) {
                Arrays.fill(base64EncryptedBytes, 0.toByte())
            }
        }

        return null
    }

    /**
     * Decrypt the base64 encrypted cipher text
     * @param base64EncryptedCipherText
     */
    fun decrypt(base64EncryptedCipherText: String): ByteArray? {
        if (entry == null) {
            Log.e(TAG, "decrypt. Entry is null")
            return null
        }

        var base64EncryptedBytes: ByteArray? = null
        var encryptedBytes: ByteArray? = null

        try {
            val cipher = Cipher.getInstance(TRANSFORMATION)

            cipher.init(Cipher.DECRYPT_MODE, entry!!.privateKey)

            base64EncryptedBytes = base64EncryptedCipherText.toByteArray(StandardCharsets.UTF_8)
            encryptedBytes = Base64.decode(base64EncryptedBytes, Base64.DEFAULT)

            return cipher.doFinal(encryptedBytes)

        } catch (e: Exception) {
            Log.e(TAG, "decrypt. unexpected", e)
        } finally {
            if (base64EncryptedBytes != null) {
                Arrays.fill(base64EncryptedBytes, 0.toByte())
            }

            if (encryptedBytes != null) {
                Arrays.fill(encryptedBytes, 0.toByte())
            }
        }

        return null
    }

    companion object {
        private const val TAG = "RsaKeyStore"
        private const val TRANSFORMATION = "RSA/ECB/PKCS1Padding"

        @Volatile
        private var INSTANCE: RsaKeyStore? = null

        fun getInstance(): RsaKeyStore =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RsaKeyStore().also { INSTANCE = it }
            }
    }
}