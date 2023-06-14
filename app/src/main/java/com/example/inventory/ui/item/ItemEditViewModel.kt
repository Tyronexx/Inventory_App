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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve and update an item from the [ItemsRepository]'s data source.
 */
class ItemEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])


//    implementation (this will retrieve the item entity details and populate the edit page)
    init {
        viewModelScope.launch {
//            retrieve the entity details with itemsRepository.getItemStream(itemId).
            itemUiState = itemsRepository.getItemStream(itemId)
                .filterNotNull()          // filter to return a flow that only contains values that are not null
                .first()
                .toItemUiState(actionEnabled = true)  // convert the item entity to ItemUiState   //pass the actionEnabled value as true to enable the Save button.
        }
    }


    /** This function updates the itemUiState with new values that the user enters.*/
    fun updateUiState(newItemUiState: ItemUiState){
//      The app enables the Save button if actionEnabled is true. You set this value to true only if the input that the user enters is valid.
        itemUiState = newItemUiState.copy(
            actionEnabled = newItemUiState.isValid()
        )
    }

    /**This function saves the updated entity to the Room database*/
    suspend fun updateItem(){
        if (itemUiState.isValid()){
            itemsRepository.updateItem(itemUiState.toItem())
        }
    }

}
