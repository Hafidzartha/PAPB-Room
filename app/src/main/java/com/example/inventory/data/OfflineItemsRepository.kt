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
 * Implementasi OfflineItemsRepository yang menggunakan DAO untuk melakukan operasi database.
 * Kelas ini mengimplementasikan antarmuka ItemsRepository, dan menyediakan fungsi-fungsi CRUD (Create, Read, Update, Delete)
 * yang diakses melalui ItemDao. Kelas ini bertindak sebagai lapisan perantara antara sumber data (Room database) dan komponen lain dalam aplikasi.
 * Dengan menggunakan repository ini, komponen lain dapat berinteraksi dengan database secara offline tanpa perlu mengetahui implementasi detail dari akses data.
 */
class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {

    /**
     * Mengambil semua item dari tabel "items" dalam bentuk aliran data (Flow).
     * Fungsi ini mengembalikan Flow<List<Item>>, yang memungkinkan data diperbarui secara real-time pada saat ada perubahan di tabel "items". Fungsi ini memanggil `getAllItems()` dari ItemDao.
     */
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    /**
     * Mengambil satu item berdasarkan ID dalam bentuk aliran data (Flow).
     * Fungsi ini mengembalikan Flow<Item?>, yang memungkinkan data item diperbarui secara real-time.
     * Fungsi ini memanggil `getItem(id)` dari ItemDao, di mana `id` adalah ID item yang dicari.
     */
    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)

    /**
     * Menyisipkan item baru ke dalam tabel "items".
     * Fungsi ini bersifat suspend untuk memastikan operasi penyisipan dilakukan di luar thread utama.
     * Fungsi ini memanggil `insert(item)` dari ItemDao untuk menambahkan item ke database.
     */
    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    /**
     * Menghapus item dari tabel "items".
     * Fungsi ini juga bersifat suspend dan memanggil `delete(item)` dari ItemDao untuk menghapus item dari database.
     */
    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    /**
     * Memperbarui data item yang ada di tabel "items".
     * Fungsi ini juga bersifat suspend untuk memastikan operasi update dilakukan di luar thread utama.
     * Fungsi ini memanggil `update(item)` dari ItemDao untuk memperbarui item di database.
     */
    override suspend fun updateItem(item: Item) = itemDao.update(item)
}

