package com.fftools.notemanager.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fftools.notemanager.common.enum.LoadStatus
import com.fftools.notemanager.repositories.Api
import com.fftools.notemanager.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val status: LoadStatus = LoadStatus.Init(),
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val log: MainLog?,
    private val api: Api?
) : ViewModel() {
   private val _uiState = MutableStateFlow(LoginUiState())
   val uiState = _uiState.asStateFlow() // State only read

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }



    fun logMessage(message: String) {
        log?.i("LoginViewModel", message)
    }

    private fun updateStatus(status: LoadStatus) {
        _uiState.value = _uiState.value.copy(status = status)
    }
    fun reset() {
        updateStatus(LoadStatus.Init())
        logMessage("LoginViewModel reset")
    }

    fun login() {
        viewModelScope.launch {
            updateStatus(LoadStatus.Loading())
            try {
                // Simulate API call
                val result = api?.login(_uiState.value.username, _uiState.value.password)
                if (result == true) {
                    updateStatus(LoadStatus.Success())
                    logMessage("Login successful for user: ${_uiState.value.username}")
                } else {
                    updateStatus(LoadStatus.Error("Login failed"))
                    logMessage("Login failed for user: ${_uiState.value.username}")
                }
            } catch (e: Exception) {
                updateStatus(LoadStatus.Error(e.message ?: "Unknown error"))
            }
        }
    }
}