package com.example.bottomnavyt

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class pembayaran : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pembayaran)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bca: TextView = findViewById(R.id.bca)
        val bri: TextView = findViewById(R.id.bri)
        val bni: TextView = findViewById(R.id.bni)
        val mandiri: TextView = findViewById(R.id.mandiri)

        val layananTerpilih = intent.getStringExtra("layananTerpilih")
        val totalHargaLayanan = intent.getIntExtra("totalHargaLayanan", 0)

        bca.setOnClickListener {
            Toast.makeText(this, "Anda memilih Bank BCA", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, metodebca::class.java).apply {
                putExtra("layananTerpilih", layananTerpilih)
                putExtra("totalHargaLayanan", totalHargaLayanan)
            }
            startActivity(intent)
        }

        bri.setOnClickListener {
            Toast.makeText(this, "Anda memilih Bank BRI", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, metodebri::class.java).apply {
                putExtra("layananTerpilih", layananTerpilih)
                putExtra("totalHargaLayanan", totalHargaLayanan)
            }
            startActivity(intent)
        }

        bni.setOnClickListener {
            Toast.makeText(this, "Anda memilih Bank BNI", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, metodebni::class.java).apply {
                putExtra("layananTerpilih", layananTerpilih)
                putExtra("totalHargaLayanan", totalHargaLayanan)
            }
            startActivity(intent)
        }

        mandiri.setOnClickListener {
            Toast.makeText(this, "Anda memilih Bank Mandiri", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, metodemandiri::class.java).apply {
                putExtra("layananTerpilih", layananTerpilih)
                putExtra("totalHargaLayanan", totalHargaLayanan)
            }
            startActivity(intent)
        }
    }
}
