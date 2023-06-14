/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.ui.item

import com.example.inventory.data.Item

/**
 * Represents Ui State for an Item.
 */
data class ItemUiState(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    /**The actionEnabled value is true if the text in all the text fields is valid (not empty).*/
    val actionEnabled: Boolean = false
)

/**
 * Extension function to convert [ItemUiState] to [Item]. If the value of [ItemUiState.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ItemUiState] is not a valid [Int], then the quantity will be set to 0
 */
fun ItemUiState.toItem(): Item = Item(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)

/**
 * Extension function to convert [Item] room entity object to [ItemUiState]
 */
fun Item.toItemUiState(actionEnabled: Boolean = false): ItemUiState = ItemUiState(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString(),
    actionEnabled = actionEnabled
)

/**The isValid() extension function checks if the name, price, and quantity are empty. This function verifies if the text in the TextFields is not empty. You use this function to verify user input before adding or updating the entity in the database.*/
fun ItemUiState.isValid() : Boolean {
    return name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
 }
