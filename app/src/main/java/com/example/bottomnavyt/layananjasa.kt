package com.example.bottomnavyt

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class layananjasa : AppCompatActivity() {

    private val hargaLayananPerHari = mapOf(
        R.id.checkboxPembangunanRumah to 1800000,
        R.id.checkboxPerbaikanAtap to 750000,
        R.id.checkboxPerbaikanPipaAir to 450000,
        R.id.checkboxPerbaikanListrik to 300000,
        R.id.checkboxPengecatan to 450000,
        R.id.checkboxInstalasiListrik to 600000,
        R.id.checkboxPemasanganPompa to 450000,
        R.id.checkboxPembangunanPondasi to 1050000
    )

    private var jumlahHari = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layananjasa)

        val btnLanjutPemesanan: Button = findViewById(R.id.btnLanjutPemesanan)
        val textTotalHarga: TextView = findViewById(R.id.textTotalHarga)
        val textJumlahHari: TextView = findViewById(R.id.textJumlahHari)
        val buttonKurangHari: Button = findViewById(R.id.buttonKurangHari)
        val buttonTambahHari: Button = findViewById(R.id.buttonTambahHari)
        val editTextHargaManual: EditText = findViewById(R.id.editTextHargaManual)

        val checkboxListeners = mutableListOf<CheckBox>()

        for (checkboxId in hargaLayananPerHari.keys) {
            val checkbox = findViewById<CheckBox>(checkboxId)
            checkbox.setOnCheckedChangeListener { _, _ ->
                updateTotalHarga()
            }
            checkboxListeners.add(checkbox)
        }

        textTotalHarga.text = "Total Harga: Rp 0"

        buttonKurangHari.setOnClickListener {
            if (jumlahHari > 1) {
                jumlahHari--
                updateJumlahHariText()
                updateTotalHarga()
            }
        }

        buttonTambahHari.setOnClickListener {
            jumlahHari++
            updateJumlahHariText()
            updateTotalHarga()
        }

        // Update total harga saat harga manual berubah
        editTextHargaManual.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateTotalHarga()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btnLanjutPemesanan.setOnClickListener {
            val layananTerpilih = StringBuilder()
            var adaLayananDipilih = false

            for (checkboxId in hargaLayananPerHari.keys) {
                val checkbox = findViewById<CheckBox>(checkboxId)
                if (checkbox.isChecked) {
                    layananTerpilih.append("${checkbox.text}\n")
                    adaLayananDipilih = true
                }
            }

            // Ambil harga manual dari EditText
            val hargaManualText = editTextHargaManual.text.toString()
            val hargaManual = if (hargaManualText.isNotEmpty()) hargaManualText.toInt() else 0

            // Jika tidak ada layanan dipilih dan harga manual kosong, tampilkan notifikasi
            if (!adaLayananDipilih && hargaManual == 0) {
                Toast.makeText(this, "Silakan pilih setidaknya satu layanan atau masukkan harga manual", Toast.LENGTH_LONG).show()
            } else {
                val totalHarga = hitungTotalHarga()

                // Kirim data ke server
                val url = Db_Contract.urlLayananJasa

                val request = object : StringRequest(Request.Method.POST, url,
                    Response.Listener { response ->
                        try {
                            val jsonObject = JSONObject(response)
                            val success = jsonObject.getInt("success")
                            val message = jsonObject.getString("message")

                            if (success == 1) {
                                // Jika sukses, pindah ke halaman pembayaran
                                val intent = Intent(this, pembayaran::class.java).apply {
                                    putExtra("layananTerpilih", layananTerpilih.toString())
                                    putExtra("totalHargaLayanan", totalHarga)
                                    putExtra("jumlahHari", jumlahHari)
                                }
                                startActivity(intent)
                                finish()
                            } else {
                                // Jika gagal, tampilkan pesan kesalahan
                                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error ->
                        // Handle error dari server atau koneksi
                        Toast.makeText(this, "Error: " + error.message, Toast.LENGTH_SHORT).show()
                    }) {

                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["layananTerpilih"] = layananTerpilih.toString()
                        params["totalHargaLayanan"] = totalHarga.toString()
                        params["jumlahHari"] = jumlahHari.toString()
                        return params
                    }
                }

                // Menambahkan request ke request queue
                Volley.newRequestQueue(this).add(request)
            }
        }

        updateJumlahHariText()
        updateTotalHarga()
    }

    private fun updateJumlahHariText() {
        val textJumlahHari: TextView = findViewById(R.id.textJumlahHari)
        textJumlahHari.text = "Jumlah hari: $jumlahHari"
    }

    private fun hitungTotalHarga(): Int {
        var total = 0

        // Perhitungan harga dari checkbox
        for (checkboxId in hargaLayananPerHari.keys) {
            val checkbox = findViewById<CheckBox>(checkboxId)
            if (checkbox.isChecked) {
                val hargaPerHari = hargaLayananPerHari[checkboxId] ?: 0
                total += hargaPerHari * jumlahHari
            }
        }

        // Ambil harga manual dari EditText
        val editTextHargaManual: EditText = findViewById(R.id.editTextHargaManual)
        val hargaManualText = editTextHargaManual.text.toString()
        val hargaManual = if (hargaManualText.isNotEmpty()) hargaManualText.toInt() else 0
        total += hargaManual * jumlahHari  // Tambahkan harga manual ke total

        return total
    }

    private fun updateTotalHarga() {
        val textTotalHarga: TextView = findViewById(R.id.textTotalHarga)
        textTotalHarga.text = "Total Harga: Rp ${hitungTotalHarga()}"
    }
}
