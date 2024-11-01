package com.example.bottomnavyt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

private const val ARG_LAYANAN_ID = "layananId"
private const val ARG_TANGGAL = "tanggal"
private const val ARG_JAM = "jam"

class Pesanan : Fragment() {
    private var layananId: Int? = null
    private var tanggal: String? = null
    private var jam: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layananId = it.getInt(ARG_LAYANAN_ID)
            tanggal = it.getString(ARG_TANGGAL)
            jam = it.getString(ARG_JAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pesanan, container, false)

        val textViewLayanan: TextView = view.findViewById(R.id.textViewLayanan)
        val textViewJumlahHari: TextView = view.findViewById(R.id.textViewJumlahHari)
        val textViewTanggal: TextView = view.findViewById(R.id.textViewTanggal)
        val textViewJam: TextView = view.findViewById(R.id.textViewJam)
        val textViewTotalHarga: TextView = view.findViewById(R.id.textViewTotalHarga)

        textViewTanggal.text = "Tanggal: $tanggal"
        textViewJam.text = "Jam: $jam"

        // Panggil fungsi fetchDataLayananJasa dengan parameter ID layanan yang sesuai
        val idLayananJasa = 1 // Ganti dengan ID yang sesuai
        fetchDataLayananJasa(idLayananJasa, textViewLayanan, textViewJumlahHari, textViewTotalHarga)

        return view
    }

    // Fungsi untuk mengambil data layanan dari API
    private fun fetchDataLayananJasa(layananId: Int, textViewLayanan: TextView, textViewJumlahHari: TextView, textViewTotalHarga: TextView) {
        val url = "${Db_Contract.urlLayananJasa}?id=$layananId"
        Log.d("PesananFragment", "Fetching data from URL: $url")

        val request = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    Log.d("PesananFragment", "Response: $response")
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getInt("success")
                    if (success == 1) {
                        val layananTerpilih = jsonObject.getString("layananTerpilih")
                        val totalHarga = jsonObject.getInt("totalHarga")
                        val jumlahHari = jsonObject.getInt("jumlahHari")

                        textViewLayanan.text = "Layanan: \n$layananTerpilih"
                        textViewTotalHarga.text = "Total Harga: Rp $totalHarga"
                        textViewJumlahHari.text = "Jumlah Hari: $jumlahHari"
                    } else {
                        val message = jsonObject.getString("message")
                        Log.e("PesananFragment", "Error message: $message")
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    Log.e("PesananFragment", "JSON Exception: ${e.message}")
                    e.printStackTrace()
                }
            },
            { error ->
                Log.e("PesananFragment", "Volley Error: ${error.message}")
                Toast.makeText(activity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        Volley.newRequestQueue(activity).add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(layananId: Int, tanggal: String?, jam: String?) =
            Pesanan().apply {
                arguments = Bundle().apply {
                    putInt(ARG_LAYANAN_ID, layananId)
                    putString(ARG_TANGGAL, tanggal)
                    putString(ARG_JAM, jam)
                }
            }
    }
}
