package com.example.sewalahan

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.sewalahan.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class detail : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nama = binding.tvNamaDetail
        val lokasi = binding.tvLokasiDetail
        val ukuran = binding.tvUkuranDetail
        val deskripsi = binding.tvDeskripsiDetail
        val harga = binding.tvHargaDetail
        val pemilik = binding.tvPemilikDetail
        val telp = binding.btnTelpDetail
        val gambar = binding.ivGambarDetail

        val bundle : Bundle?= intent.extras
        val bundleNama = bundle!!.getString("nama")
        val bundleLokasi = bundle.getString("lokasi")
        val bundleUkuran = bundle.getString("ukuran")
        val bundleDeskripsi = bundle.getString("deskripsi")
        val bundleHarga = bundle.getString("harga")
        val bundlePemilik = bundle.getString("pemilik")
        val bundleTelp = bundle.getString("telp")
        val bundleGambar = bundle.getString("gambar")

        nama.text = bundleNama
        lokasi.text = bundleLokasi
        ukuran.text = bundleUkuran
        deskripsi.text = bundleDeskripsi
        harga.text = bundleHarga
        pemilik.text = bundlePemilik
        telp.text = "WhatsApp : "+bundleTelp
        Picasso.get().load(bundleGambar).into(gambar)

        telp.setOnClickListener {
            val uri = Uri.parse("https://api.whatsapp.com/send?phone="+bundleTelp)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
//            val uri = Uri.parse("number"+"+6281340391017")
//            val intent = Intent(Intent.ACTION_SENDTO, uri)
//            intent.setPackage("com.whatsapp")
//            if (intent.resolveActivity(this.packageManager)!=null) {
//                startActivity(intent)
//            }
//            else {
//                Toast.makeText(this, "WhatsApp tidak terinstal di perangkat Anda",
//                    Toast.LENGTH_SHORT).show()
//            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.admin -> Intent(this,login::class.java).also{
                startActivity(it)
            }
            R.id.home -> Intent(this,home::class.java).also{
                startActivity(it)
            }
            R.id.close -> finish()
        }
        return true
    }
}