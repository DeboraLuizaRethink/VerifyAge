package com.example.verifyingvalue

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextYear: EditText = findViewById(R.id.editTextBirth)
        val btnVerify: Button = findViewById(R.id.btn_verify)
        val txtAge: TextView = findViewById(R.id.txt_age)


        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 10
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(ContentValues.TAG, "Config params updated: $updated")
                    Toast.makeText(this, "Está acessando o FireBase",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Não está acessando o FireBase",
                        Toast.LENGTH_SHORT).show()
                }
            }

        val getActualYear: String = Firebase.remoteConfig.getString("actual_year");

        if(getActualYear == ""){
            Toast.makeText(this, "Valores vazios",
                Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Valores do firebase não estão vazios",
                Toast.LENGTH_SHORT).show()
        }

        btnVerify.setOnClickListener {

           val actualAge : Int = getActualYear.toInt() - editTextYear.text.toString().toInt()

            txtAge.text = actualAge.toString()

            if (actualAge >= 18){
                Toast.makeText(this, "Você é maior de Idade",
                    Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Você não é maior de Idade",
                    Toast.LENGTH_SHORT).show()
            }

        }

    }
}