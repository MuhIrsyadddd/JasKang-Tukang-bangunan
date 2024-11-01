package com.example.bottomnavyt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnavyt.databinding.KonfirmasipembayaranBinding; // Pastikan import sesuai dengan layout yang digunakan
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class konfirmasipembayaran extends AppCompatActivity {

    KonfirmasipembayaranBinding binding; // Gunakan binding yang sesuai dengan layout konfirmasipembayaran.xml
    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Gunakan binding yang sesuai dengan layout konfirmasipembayaran.xml
        binding = KonfirmasipembayaranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Pastikan layout yang digunakan adalah konfirmasipembayaran.xml

        // Retrieve data from intent
        Intent intent = getIntent();
        String layananTerpilih = intent.getStringExtra("layananTerpilih");
        String tanggalSekarang = intent.getStringExtra("tanggalSekarang");
        String jamSekarang = intent.getStringExtra("jamSekarang");
        int totalBiaya = intent.getIntExtra("totalBiaya", 0);

        binding.selectImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        binding.uploadimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(layananTerpilih, tanggalSekarang, jamSekarang, totalBiaya);
            }
        });
    }

    private void uploadImage(String layananTerpilih, String tanggalSekarang, String jamSekarang, int totalBiaya) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/" + fileName);

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        binding.firebaseimage.setImageURI(null);
                        Toast.makeText(konfirmasipembayaran.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                        // Pindah ke halaman detailpembayaran setelah berhasil upload
                        Intent intent = new Intent(konfirmasipembayaran.this, detailpembayaran.class);
                        intent.putExtra("layananTerpilih", layananTerpilih);
                        intent.putExtra("tanggalSekarang", tanggalSekarang);
                        intent.putExtra("jamSekarang", jamSekarang);
                        intent.putExtra("totalBiaya", totalBiaya);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(konfirmasipembayaran.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.firebaseimage.setImageURI(imageUri);
        }
    }
}
