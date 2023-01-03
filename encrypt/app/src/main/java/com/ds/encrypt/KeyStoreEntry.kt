package com.ds.encrypt

import java.security.PrivateKey
import java.security.PublicKey

interface KeyStoreEntry {
    val publicKey: PublicKey
    val privateKey: PrivateKey
}