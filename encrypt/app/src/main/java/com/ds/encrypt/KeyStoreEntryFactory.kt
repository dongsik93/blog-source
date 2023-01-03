package com.ds.encrypt

import java.security.*

object KeyStoreEntryFactory {

    fun getEntry(keyStore: KeyStore, alias: String): KeyStoreEntry {
        return RsaKeyStoreEntry(keyStore, alias)
    }

    abstract class KeyStoreBase protected constructor(val keyStore: KeyStore, val alias: String)
}

class RsaKeyStoreEntry(keyStore: KeyStore, alias: String) : KeyStoreEntryFactory.KeyStoreBase(keyStore, alias), KeyStoreEntry {
    private var entry: KeyStore.Entry? = null

    override val publicKey: PublicKey
        @Throws(Exception::class)
        get() {
            val key: PublicKey

            if (entry == null) {
                entry = _entry
            }
            val certificate = (entry as KeyStore.PrivateKeyEntry).certificate
            key = certificate.publicKey

            return key
        }

    override val privateKey: PrivateKey
        @Throws(Exception::class)
        get() {
            if (entry == null) {
                entry = _entry
            }
            return (entry as KeyStore.PrivateKeyEntry).privateKey
        }

    private val _entry: KeyStore.Entry
        @Throws(UnrecoverableEntryException::class, NoSuchAlgorithmException::class, KeyStoreException::class)
        get() = keyStore.getEntry(alias, null)
}
