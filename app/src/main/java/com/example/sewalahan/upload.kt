package com.example.sewalahan

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.sewalahan.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class upload : AppCompatActivity() {
    lateinit var binding: ActivityUploadBinding
    val database: FirebaseDatabase= FirebaseDatabase.getInstance()
    val db: DatabaseReference=database.getReference("lahan")
    var imageUri: Uri?= null
    val firebaseStorage:FirebaseStorage=FirebaseStorage.getInstance()
    val storageReference:StorageReference=firebaseStorage.getReference("images")

    companion object{
        val IMAGE_REQUEST_CODE=100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGambar.setOnClickListener {
            select_image()
        }
        binding.btnUpload.setOnClickListener{
            upload_gambar()
        }
    }

    private fun select_image() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type="image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    private fun insert_data(url :String, imageName: String) {
        val nama = binding.etNama.text.toString()
        val lokasi = binding.etLokasi.text.toString()
        val ukuran = binding.etUkuran.text.toString()
        val deskripsi = binding.etDeskripsi.text.toString()
        val harga = binding.etHarga.text.toString()
        val pemilik = binding.etPemilik.text.toString()
        val telp = binding.etTelp.text.toString()
        val id:String=db.push().key.toString()
        val isActive = 1

        if (nama.isEmpty()){
            binding.etNama.error = "Silakan masukkan nama lahan"
            return
        }
        if (lokasi.isEmpty()){
            binding.etLokasi.error = "Silakan masukkan lokasi lahan"
            return
        }
        if (ukuran.isEmpty()){
            binding.etUkuran.error = "Silakan masukkan ukuran lahan"
            return
        }
        if (deskripsi.isEmpty()){
            binding.etDeskripsi.error = "Silakan masukkan deskripsi lahan"
            return
        }

        val lahan = Lahan(id, nama, lokasi, ukuran, deskripsi, url, imageName, harga, pemilik, telp, isActive)

            db.child(id).setValue(lahan).addOnCompleteListener {
                binding.etNama.text.clear()
                binding.etLokasi.text.clear()
                binding.etUkuran.text.clear()
                binding.etDeskripsi.text.clear()
                binding.etHarga.text.clear()
                binding.etPemilik.text.clear()
                binding.etTelp.text.clear()
                binding.ivGambar.setImageResource(0)
                Toast.makeText(this, "data telah diupload", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener(){
                Toast.makeText(this, "data gagal diupload", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data!=null) {
            imageUri =data?.data
            binding.ivGambar.setImageURI(imageUri)
        }
    }
    private fun upload_gambar() {
        val imageName= UUID.randomUUID().toString()
        val imageReference = storageReference.child(imageName)
        imageUri?.let { uri ->
            imageReference.putFile(uri).addOnSuccessListener {
                Toast.makeText(applicationContext,"gambar berhasil terupload",Toast.LENGTH_SHORT).show()
                val myUploadImagerReference=storageReference.child(imageName)
                myUploadImagerReference.downloadUrl.addOnSuccessListener {url ->
                    val imageURl = url.toString()
                    insert_data(imageURl,imageName)
                }
            }.addOnFailureListener{
                Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_admin, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add -> Intent(this,upload::class.java).also {
                startActivity(it)
            }
            R.id.update -> Intent(this,edit::class.java).also {
                startActivity(it)
            }
            R.id.home -> Intent(this,home::class.java).also {
                startActivity(it)
            }
        }
        return true
    }

}