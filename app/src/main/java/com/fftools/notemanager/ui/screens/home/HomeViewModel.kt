package com.fftools.notemanager.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fftools.notemanager.common.enum.LoadStatus
import com.fftools.notemanager.model.NoteItem
import com.fftools.notemanager.repositories.Api
import com.fftools.notemanager.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val notes : List<NoteItem> = emptyList(),
    val status : LoadStatus = LoadStatus.Init(),
    val selectedIndex : Int = -1
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val log: MainLog?,
    private val api: Api?
) : ViewModel() {
    val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow() // State only read

    fun logMessage(message: String) {
        log?.i("HomeViewModel", message)
    }

    private fun updateStatus(status: LoadStatus) {
        _uiState.value = _uiState.value.copy(status = status)
    }
    fun reset() {
        updateStatus(LoadStatus.Init())
        logMessage("HomeViewModel reset")
    }

    fun loadNote() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                if(api != null) {
                    val notes = api.loadNotes()
                    _uiState.value = _uiState.value.copy(notes = notes, status = LoadStatus.Success())
                    logMessage("Loaded ${notes.size} notes")
                } else {
                    _uiState.value = _uiState.value.copy(status = LoadStatus.Error("Api is null"))
                    logMessage("Api is null")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
                logMessage("Error loading notes: ${e.message}")
            }
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                if(api != null) {
                    api.deleteNote(noteId)
                    val loadNotes = api.loadNotes()
                    _uiState.value = _uiState.value.copy(notes = loadNotes, status = LoadStatus.Success())
                    logMessage("Deleted note with id: $noteId")
                } else {
                    _uiState.value = _uiState.value.copy(status = LoadStatus.Error("Api is null"))
                    logMessage("Api is null")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
                logMessage("Error deleting note: ${e.message}")
            }
        }
    }

    fun selectNote(index: Int) {
        _uiState.value = _uiState.value.copy(selectedIndex = index)
        logMessage("Selected note at index: $index")
    }
}