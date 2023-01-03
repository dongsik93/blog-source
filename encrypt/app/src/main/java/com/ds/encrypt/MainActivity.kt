package com.ds.encrypt

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.ds.encrypt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            "encrypt_shared_preference",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        with(binding) {
            btnSave.setOnClickListener {
                val key = etKey.text.toString()
                val value = etValue.text.toString()
                sharedPreferences.edit().putString(key, value).apply()
            }

            btnLoad.setOnClickListener {
                tvLoadValue.text = sharedPreferences.getString(etKey.text.toString(), "")
            }
        }
    }
}