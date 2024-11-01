package com.example.bottomnavyt

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class daftarpesanan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daftarpesanan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil data dari intent
        val alamatPengerjaan = intent.getStringExtra("alamatPengerjaan")
        val catatanTambahan = intent.getStringExtra("catatanTambahan")
        val layananTerpilih = intent.getStringExtra("layananTerpilih")

        // Tampilkan data pada TextView yang sesuai
        findViewById<TextView>(R.id.textAlamatPengerjaan).text = "Alamat Pengerjaan: $alamatPengerjaan"
        findViewById<TextView>(R.id.textCatatanTambahan).text = "Catatan Tambahan: $catatanTambahan"
        findViewById<TextView>(R.id.textLayanan).text = "Layanan: $layananTerpilih"
    }
}
