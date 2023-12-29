package com.example.sewalahan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sewalahan.databinding.ActivityHomeBinding
import com.google.firebase.database.*

class home : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<Lahan>
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val db: DatabaseReference =database.getReference("lahan")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.listItem
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        arrayList = arrayListOf<Lahan>()
        getData()
    }

    private fun getData() {
        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (listSnapshot in snapshot.children){
                        val item = listSnapshot.getValue(Lahan::class.java)
                        if (item?.isActive == 1) {
                            arrayList.add(item)
                        }
                    }
                    recyclerView.adapter = ListAdapter(this@home, arrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
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