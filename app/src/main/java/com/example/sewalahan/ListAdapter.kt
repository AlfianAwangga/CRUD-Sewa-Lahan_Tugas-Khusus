package com.example.sewalahan

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ListAdapter(private val context: Context, private val lahanList: ArrayList<Lahan>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_home_list,
        parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = lahanList[position]

        holder.nama.text = current.nama
        holder.lokasi.text = current.lokasi
        holder.ukuran.text = current.ukuran
        Picasso.get().load(current.url).into(holder.gambar)

        val nama = holder.nama.text.toString()

        holder.btnDetail.setOnClickListener {
            val intent = Intent(context, detail::class.java)
            intent.putExtra("nama",current.nama)
            intent.putExtra("lokasi",current.lokasi)
            intent.putExtra("ukuran",current.ukuran)
            intent.putExtra("deskripsi",current.deskripsi)
            intent.putExtra("harga",current.harga)
            intent.putExtra("pemilik",current.pemilik)
            intent.putExtra("telp",current.telp)
            intent.putExtra("gambar",current.url)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return lahanList.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val nama : TextView = itemView.findViewById(R.id.tv_nama)
        val lokasi : TextView = itemView.findViewById(R.id.tv_lokasi)
        val ukuran : TextView = itemView.findViewById(R.id.tv_ukuran)
        val gambar : ImageView = itemView.findViewById(R.id.iv_image)
        val btnDetail : Button = itemView.findViewById(R.id.btn_detail)
    }
}