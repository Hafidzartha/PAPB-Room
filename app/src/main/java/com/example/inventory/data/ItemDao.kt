package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO (Data Access Object) menyediakan metode untuk mengakses database.
 * Setiap metode di dalam interface ini digunakan untuk berinteraksi dengan tabel "items" di database Room.
 * Anotasi @Dao menandakan bahwa interface ini adalah DAO untuk Room.
 */
@Dao
interface ItemDao {

    /**
     * Menyisipkan item baru ke dalam tabel "items".
     * Anotasi @Insert digunakan untuk menandakan bahwa metode ini bertugas menyisipkan data.
     * Parameter onConflict = OnConflictStrategy.IGNORE akan mengabaikan penyisipan jika data dengan primary key yang sama sudah ada di tabel.
     * suspend berarti dapat dijalankan di dalam coroutine.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    /**
     * Memperbarui data item yang ada di tabel "items".
     * Anotasi @Update digunakan untuk menandakan bahwa metode ini bertugas memperbarui data.
     * Fungsi ini akan memperbarui item sesuai dengan nilai yang diberikan, menggunakan primary key untuk identifikasi.
     */
    @Update
    suspend fun update(item: Item)

    /**
     * Menghapus item dari tabel "items".
     * Anotasi @Delete digunakan untuk menandakan bahwa metode ini bertugas menghapus data.
     * Fungsi ini akan menghapus item yang diberikan dari tabel "items".
     */
    @Delete
    suspend fun delete(item: Item)

    /**
     * Mendapatkan item berdasarkan ID dari tabel "items".
     * Anotasi @Query digunakan untuk menjalankan perintah SQL yang disediakan.
     * Fungsi ini mengambil item yang memiliki ID tertentu dan mengembalikan hasilnya dalam bentuk Flow<Item>.
     * Penggunaan Flow memungkinkan data yang diperoleh secara real-time saat ada perubahan pada item terkait.
     */
    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    /**
     * Mengambil semua item yang ada di tabel "items" dan mengurutkannya berdasarkan nama secara ascending.
     * Fungsi ini mengembalikan daftar item dalam bentuk Flow<List<Item>>, memungkinkan update data secara real-time.
     * Dengan menggunakan @Query, kita bisa mendapatkan seluruh item yang diurutkan berdasarkan kolom `name` secara ascending.
     */
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}
