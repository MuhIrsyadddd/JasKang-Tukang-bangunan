package com.example.bottomnavyt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class konfirmasidankonsultasilayanan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasidankonsultasilayanan)

        // Inisialisasi layout (LinearLayout) sebagai tombol
        val btnKonfirmasiPesanan: LinearLayout = findViewById(R.id.boxKonfirmasiPesanan)
        val btnKonsultasiPesanan: LinearLayout = findViewById(R.id.boxKonsultasiPesanan)

        // Aksi ketika "Konfirmasi Pesanan" diklik
        btnKonfirmasiPesanan.setOnClickListener {
            // Intent untuk membuka WhatsApp dengan link wa.link
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.link/o43qdw")
            startActivity(intent)
        }

        // Aksi ketika "Konsultasi Pesanan" diklik
        btnKonsultasiPesanan.setOnClickListener {
            // Intent untuk membuka WhatsApp dengan nomor yang diberikan
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/6289518097920") // Nomor WA dalam format internasional
            startActivity(intent)
        }
    }
}
