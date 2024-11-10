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

package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item
import java.text.NumberFormat
import com.example.inventory.data.ItemsRepository

/**
 * ViewModel untuk memvalidasi dan memasukkan item ke dalam database Room.
 * Kelas ini bertindak sebagai lapisan antara UI dan `ItemsRepository`, mengelola status UI serta memproses input data pengguna.
 * Dengan `ItemEntryViewModel`, data item dapat divalidasi dan disimpan ke database menggunakan repository.
 */
class ItemEntryViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    /**
     * Menyimpan status UI item saat ini
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /**
     * Memperbarui [itemUiState] dengan nilai yang diberikan dalam argumen. Metode ini juga memicu validasi terhadap nilai input.
     * `updateUiState` memungkinkan pembaruan terhadap status UI item yang berasal dari perubahan input pengguna.
     */
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }
    /**
    *Memvalidasi input yang diberikan dalam [uiState] untuk memastikan semua data terisi.
    */
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }

    /**
     * Menyimpan item ke dalam database jika input valid.
     */
    suspend fun saveItem() {
        if (validateInput()) {
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }
}

/**
 * `ItemUiState` menyimpan status UI terkini, termasuk detail item dan validitas input.
 */
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

/**
 * `ItemDetails` menyimpan detail yang dimasukkan oleh pengguna, seperti ID, nama, harga, dan kuantitas item.
 */
data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)

/**
 * Extension function to convert [ItemDetails] to [Item]. If the value of [ItemDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ItemDetails.quantity] is not a valid [Int], then the quantity will be set to 0
 */
fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)

/**
 * Fungsi ekstensi untuk memformat harga [Item] sebagai string dalam format mata uang.
 */
fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

/**
 * Fungsi ekstensi untuk mengonversi [Item] menjadi [ItemUiState]. Yang memungkinkan item diubah menjadi status UI dengan status validasi yang ditentukan.
 */
fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Item] to [ItemDetails]
 * Fungsi ini mengonversi properti item seperti `id`, `name`, `price`, dan `quantity` menjadi `String` untuk memudahkan pengelolaan input dalam status UI.
 */
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)
