package com.example.bottomnavyt

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.*

class metodebni : AppCompatActivity() {

    private lateinit var textViewCountdown: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_metodebni)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve data from intent
        val layananTerpilih = intent.getStringExtra("layananTerpilih")
        val totalHargaLayanan = intent.getIntExtra("totalHargaLayanan", 0)
        val biayaLayanan = 2500
        val totalBiaya = totalHargaLayanan + biayaLayanan

        // Find views
        val textViewLayanan: TextView = findViewById(R.id.textViewLayanan)
        val textViewTotalHarga: TextView = findViewById(R.id.textViewTotalHarga)
        val textViewBiayaLayanan: TextView = findViewById(R.id.textViewBiayaLayanan)
        val textViewTotalBiaya: TextView = findViewById(R.id.textViewTotalBiaya)
        val textViewTanggalSekarang: TextView = findViewById(R.id.textViewTanggalSekarang)
        val textViewJamSekarang: TextView = findViewById(R.id.textViewJamSekarang)
        val buttonPembayaran: Button = findViewById(R.id.buttonPembayaran)
        textViewCountdown = findViewById(R.id.textViewCountdown)

        // Set text for views
        textViewLayanan.text = "Layanan:\n$layananTerpilih"
        textViewTotalHarga.text = "Total Harga: Rp $totalHargaLayanan"
        textViewBiayaLayanan.text = "Biaya Layanan: Rp $biayaLayanan"
        textViewTotalBiaya.text = "Total: Rp $totalBiaya"

        // Set current date and time
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val tanggalSekarang = simpleDateFormat.format(calendar.time)
        val jamSekarang = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)

        textViewTanggalSekarang.text = "Tanggal Sekarang: $tanggalSekarang"
        textViewJamSekarang.text = "Jam Sekarang: $jamSekarang"

        // Start countdown timer for 24 hours
        startCountdown()

        // Handle button click
        buttonPembayaran.setOnClickListener {
            val intent = Intent(this, konfirmasipembayaran::class.java).apply {
                putExtra("layananTerpilih", layananTerpilih)
                putExtra("tanggalSekarang", tanggalSekarang)
                putExtra("jamSekarang", jamSekarang)
                putExtra("totalBiaya", totalBiaya)
            }
            startActivity(intent)
        }
    }

    private fun startCountdown() {
        val millisInFuture = 24 * 60 * 60 * 1000 // 24 hours in milliseconds
        val countDownInterval = 1000 // 1 second in milliseconds

        object : CountDownTimer(millisInFuture.toLong(), countDownInterval.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = millisUntilFinished / (60 * 60 * 1000)
                val minutes = (millisUntilFinished % (60 * 60 * 1000)) / (60 * 1000)
                val seconds = (millisUntilFinished % (60 * 1000)) / 1000

                val timeRemaining = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
                textViewCountdown.text = "Waktu Tersisa: $timeRemaining"
            }

            override fun onFinish() {
                textViewCountdown.text = "Waktu Habis!"
            }
        }.start()
    }
}
