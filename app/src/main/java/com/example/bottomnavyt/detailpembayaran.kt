package com.example.bottomnavyt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class detailpembayaran : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detailpembayaran)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Volley request queue
        requestQueue = Volley.newRequestQueue(this)

        // Find views
        val textViewLayananDipesan: TextView = findViewById(R.id.textViewLayananDipesan)
        val textViewTanggalJam: TextView = findViewById(R.id.textViewTanggalJam)
        val textViewTotalBiaya: TextView = findViewById(R.id.textViewTotalBiaya)
        val buttonKembaliKeHome: Button = findViewById(R.id.buttonKembaliKeHome)

        // Retrieve data from intent
        val layananTerpilih = intent.getStringExtra("layananTerpilih")
        val tanggalSekarang = intent.getStringExtra("tanggalSekarang")
        val jamSekarang = intent.getStringExtra("jamSekarang")
        val totalBiaya = intent.getIntExtra("totalBiaya", 0)

        // Set text for views
        textViewLayananDipesan.text = "Layanan yang dipesan: \n$layananTerpilih"
        textViewTanggalJam.text = "Tanggal: $tanggalSekarang\nJam: $jamSekarang"
        textViewTotalBiaya.text = "Total Biaya: Rp $totalBiaya"

        // Handle button click
        buttonKembaliKeHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("layananTerpilih", layananTerpilih)
                putExtra("tanggalSekarang", tanggalSekarang)
                putExtra("jamSekarang", jamSekarang)
                putExtra("totalBiaya", totalBiaya)
            }
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
