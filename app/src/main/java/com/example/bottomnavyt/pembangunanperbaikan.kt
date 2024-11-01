package com.example.bottomnavyt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class pembangunanperbaikan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembangunanperbaikan)

        val btnSubmit: Button = findViewById(R.id.btnSubmit)
        val editNama: EditText = findViewById(R.id.editNama)
        val editAlamat: EditText = findViewById(R.id.editAlamatPengerjaan)
        val editCatatan: EditText = findViewById(R.id.editCatatan)

        btnSubmit.setOnClickListener {
            val nama = editNama.text.toString().trim()
            val alamat = editAlamat.text.toString().trim()
            val catatan = editCatatan.text.toString().trim()

            // Validasi input
            if (nama.isNotEmpty() && alamat.isNotEmpty()) {
                // Kirim data ke server
                val url = Db_Contract.urlPembangunanPerbaikan

                val request = object : StringRequest(Request.Method.POST, url,
                    Response.Listener { response ->
                        try {
                            val jsonObject = JSONObject(response)
                            val success = jsonObject.getInt("success")
                            val message = jsonObject.getString("message")

                            if (success == 1) {
                                // Jika sukses, pindah ke halaman layananjasa
                                val intent = Intent(this, layananjasa::class.java)
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
                        params["nama"] = nama
                        params["alamatPengerjaan"] = alamat
                        params["catatanTambahan"] = catatan
                        return params
                    }
                }

                // Adding request to request queue
                Volley.newRequestQueue(this).add(request)

            } else {
                // Jika ada kolom yang kosong, tampilkan pesan kesalahan
                Toast.makeText(this, "Mohon lengkapi semua kolom!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
