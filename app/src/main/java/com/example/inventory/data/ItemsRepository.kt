/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository menyediakan fungsi untuk insert, update, delete, dan mengambil data [Item] dari sumber data tertentu.
 * Repository ini bertujuan untuk menyediakan lapisan abstraksi antara sumber data dan komponen lain dalam aplikasi, sehingga kode lebih modular dan mudah diuji.
 */
interface ItemsRepository {

    /**
     * Mengambil semua item dari sumber data yang diberikan.
     * Mengembalikan aliran data (Flow) berupa daftar item. Dengan Flow, data akan diperbarui secara real-time pada saat ada perubahan pada data di sumber data.
     */
    fun getAllItemsStream(): Flow<List<Item>>

    /**
     * Mengambil item dari sumber data yang sesuai dengan [id] yang diberikan.
     * Mengembalikan data item dalam bentuk Flow, sehingga memungkinkan data dipantau secara real-time pada saat ada perubahan pada item yang sesuai dengan ID tersebut.
     * Tipe kembalian `Flow<Item?>` berarti bisa mengembalikan null jika item tidak ditemukan.
     */
    fun getItemStream(id: Int): Flow<Item?>

    /**
     * Menyisipkan item ke dalam sumber data.
     * Fungsi ini berfungsi untuk menambahkan item baru ke dalam sumber data.
     */
    suspend fun insertItem(item: Item)

    /**
     * Menghapus item dari sumber data.
     * Fungsi ini berfungsi untuk menghapus item yang diberikan dari sumber data.
     */
    suspend fun deleteItem(item: Item)

    /**
     * Memperbarui item di dalam sumber data.
     * Fungsi ini berfungsi untuk memperbarui data item yang sudah ada di dalam sumber data.
     */
    suspend fun updateItem(item: Item)
}
