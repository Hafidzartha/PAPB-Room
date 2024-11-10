package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Kelas database dengan objek Instance tunggal (singleton).
 * Anotasi @Database digunakan untuk menandakan bahwa kelas ini adalah database Room.
 * - entities: Menentukan entitas (tabel) dalam database, dalam hal ini hanya "Item".
 * - version: Menentukan versi database, yang penting untuk migrasi database jika ada perubahan struktur di masa depan.
 * - exportSchema: Jika diatur ke false, Room tidak akan mengekspor skema database untuk digunakan dalam migrasi.
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    /**
     * Mengembalikan instance dari DAO (Data Access Object) untuk entitas Item.
     * Abstrak ini akan diimplementasikan oleh Room secara otomatis, menyediakan cara untuk mengakses operasi CRUD (Create, Read, Update, Delete) untuk tabel "items".
     */
    abstract fun itemDao(): ItemDao

    companion object {
        /**
         * Variabel Instance disimpan secara volatile, sehingga perubahan yang dibuat pada Instance segera terlihat oleh thread lain.
         */
        @Volatile
        private var Instance: InventoryDatabase? = null

        /**
         * Mengembalikan instance database.
         * Memastikan bahwa hanya ada satu instance database yang dibuat untuk seluruh aplikasi (menggunakan pola singleton).
         * - Jika Instance sudah ada, langsung dikembalikan.
         * - Jika belum ada, synchronized block digunakan untuk memastikan instance hanya dibuat satu kali, bahkan jika ada beberapa thread yang mengakses metode ini bersamaan.
         * - Room.databaseBuilder digunakan untuk membuat instance database Room dengan nama "item_database".
         */
        fun getDatabase(context: Context): InventoryDatabase {
            // jika Instance tidak null, kembalikan; jika null, buat instance database baru.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
