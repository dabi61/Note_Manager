package com.fftools.notemanager.ui.screens.addoredit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fftools.notemanager.common.enum.LoadStatus
import com.fftools.notemanager.repositories.Api
import com.fftools.notemanager.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class AddOrEditUiState(
    val noteId: Long = -1, // null for new note
    val title: String = "",
    val content: String = "",
    val status: LoadStatus = LoadStatus.Init()
)

@HiltViewModel
class AddOrEditViewModel @Inject constructor(
    private val log: MainLog?,
    private val api: Api?
) : ViewModel() {
    val _uiState = MutableStateFlow(AddOrEditUiState())
    val uiState = _uiState.asStateFlow() // State only read

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                api?.addNote(title, content)
                _uiState.value = _uiState.value.copy(status = LoadStatus.Success())
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
                log?.e("AddOrEditViewModel", "Error adding note: ${e.message}")
            }
        }
    }
    fun editNote(id: Long, title: String, content: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                api?.updateNote(id, title, content)
                _uiState.value = _uiState.value.copy(status = LoadStatus.Success())
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
                log?.e("AddOrEditViewModel", "Error adding note: ${e.message}")
            }
        }
    }

}