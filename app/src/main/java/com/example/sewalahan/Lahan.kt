package com.example.sewalahan

data class Lahan(
    val id:String?="",
    val nama:String?="",
    val lokasi:String?="",
    val ukuran:String?="",
    val deskripsi:String?="",
    val url:String?="",
    val gambar:String?="",
    val harga:String?="",
    val pemilik:String?="",
    val telp:String?="",
    var isActive:Int?=1,
    ) {
//    constructor(): this("","","","","","","","","","",null)
}
