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

import android.content.Context

/**
 * Kontainer aplikasi untuk Dependency Injection.
 * Interface ini mendefinisikan `itemsRepository` yang akan diimplementasikan oleh kelas lain.
 * Tujuan dari `AppContainer` adalah untuk menyediakan akses terpusat ke dependency utama aplikasi, dalam hal ini `ItemsRepository`, yang memungkinkan pengelolaan instance dependency secara modular.
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/**
 * Implementasi dari [AppContainer] yang menyediakan instance dari [OfflineItemsRepository].
 * Kelas ini menggunakan `context` untuk mengakses database Room dan menyediakan dependency untuk `ItemsRepository` melalui instance `OfflineItemsRepository`.
 * Dengan menggunakan AppDataContainer, kita dapat memastikan bahwa hanya ada satu instance `itemsRepository` yang digunakan di seluruh aplikasi, sehingga memudahkan pengelolaan dependency.
 */
class AppDataContainer(private val context: Context) : AppContainer {

    /**
     * Implementasi untuk [ItemsRepository].
     * Property `itemsRepository` diinisialisasi secara lazy menggunakan instance `OfflineItemsRepository` yang dibuat dengan mengambil `ItemDao` dari database Room.
     * Dengan lazy initialization, `itemsRepository` hanya akan dibuat saat pertama kali diakses, sehingga dapat meningkatkan efisiensi penggunaan memori.
     */
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}

