package com.example.sewalahan

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class AdapterEdit(private val context:Context ,private val editList: ArrayList<Lahan>) : RecyclerView.Adapter<AdapterEdit.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.edit_list,
            parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = editList[position]

        holder.nama.text = current.nama
        holder.lokasi.text = current.lokasi

        holder.btnEdit.setOnClickListener {
            updateDialog(current)
        }
        //show or hide data
        holder.btnShow.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val db =database.getReference("lahan")
            if (current.isActive == 1) {
                holder.btnShow.text = "Show"
                current.isActive = 0
            }
            else if (current.isActive == 0){
                holder.btnShow.text = "Hide"
                current.isActive = 1
            }
        }
    }

    override fun getItemCount(): Int {
        return editList.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val nama : TextView = itemView.findViewById(R.id.tv_nama_edit)
        val lokasi : TextView = itemView.findViewById(R.id.tv_lokasi_edit)
        val btnEdit : Button = itemView.findViewById(R.id.btn_edit)
        val btnShow : Button = itemView.findViewById(R.id.btn_show)
    }

    //fungsi dialog update
    fun updateDialog(current: Lahan) {
        val builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.edit_dialog, null)

        val etNama : EditText= view.findViewById(R.id.et_nama)
        val etLokasi : EditText= view.findViewById(R.id.et_lokasi)
        val etUkuran : EditText= view.findViewById(R.id.et_ukuran)
        val etDeskripsi : EditText= view.findViewById(R.id.et_deskripsi)
        val etHarga : EditText= view.findViewById(R.id.et_harga)
        val etPemilik : EditText= view.findViewById(R.id.et_pemilik)
        val etTelp : EditText= view.findViewById(R.id.et_telp)
        val ivGambar : ImageView= view.findViewById(R.id.iv_gambar)

        //tampilkan data pada dialog
        etNama.setText(current.nama)
        etLokasi.setText(current.lokasi)
        etUkuran.setText(current.ukuran)
        etDeskripsi.setText(current.deskripsi)
        etHarga.setText(current.harga)
        etPemilik.setText(current.pemilik)
        etTelp.setText(current.telp)
        Picasso.get().load(current.url).into(ivGambar)

        //dialog
        builder.setTitle("Edit Data")
        builder.setView(view)

        //option Update
        builder.setPositiveButton("Update"){ p0,p1 ->
            val database = FirebaseDatabase.getInstance()
            val db =database.getReference("lahan")

            val nama = etNama.text.toString()
            val lokasi = etLokasi.text.toString()
            val ukuran = etUkuran.text.toString()
            val deskripsi = etDeskripsi.text.toString()
            val harga = etHarga.text.toString()
            val pemilik = etPemilik.text.toString()
            val telp = etTelp.text.toString()
            val isActive = current.isActive

            var lahan = Lahan(current.id, nama, lokasi, ukuran, deskripsi, current.url, current.gambar,
            harga, pemilik, telp, isActive)

            db.child(current.id.orEmpty()).setValue(lahan).addOnSuccessListener {
                Toast.makeText(context, "data telah diperbarui", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, edit::class.java)
                context.startActivity(intent)
            }
        }

        //option Hapus
        builder.setNegativeButton("Hapus"){ p0,p1 ->
            val database = FirebaseDatabase.getInstance()
            val db =database.getReference("lahan").child(current.id.orEmpty())

            db.removeValue().addOnSuccessListener {
                Toast.makeText(context, "data telah dihapus", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, edit::class.java)
                context.startActivity(intent)
            }
        }

        //Option Batal
        builder.setNeutralButton("Batal"){ p0,p1 ->

        }
        builder.create().show()
    }
}