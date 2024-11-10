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

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Kelas data Entity merepresentasikan satu baris data di dalam database.
 * Anotasi @Entity digunakan untuk menandai kelas ini sebagai room entity, yang berarti kelas ini akan dipetakan ke sebuah tabel di database Room.
 * Setiap instance dari kelas data ini merepresentasikan satu item di tabel "items" di database.
 * Room akan menggunakan kelas data ini untuk membuat dan mengelola tabel "items".
 */
@Entity(tableName = "items")
data class Item(
    /**
     * Primary key untuk tabel items.
     * Anotasi @PrimaryKey menandakan bahwa field ini adalah primary key untuk tabel.
     * Parameter autoGenerate = true menunjukkan bahwa nilai ID akan dihasilkan secara otomatis oleh Room.
     * ID ini secara unik mengidentifikasi setiap baris dalam tabel "items".
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * Nama dari item.
     * Field ini akan dipetakan ke kolom dalam tabel "items" dan menyimpan nama item dalam bentuk String.
     */
    val name: String,

    /**
     * Harga atau price dari item yang bertype kan Double.
     * Field ini merepresentasikan harga item dalam bentuk Double yang akan disimpan sebagai kolom di tabel "items".
     */
    val price: Double,

    /**
     * Quantity atau jumlah item yang tersedia.
     * Field ini merepresentasikan jumlah item yang tersedia dalam bentuk integer yang akan disimpan sebagai kolom di tabel "items".
     */
    val quantity: Int
)

