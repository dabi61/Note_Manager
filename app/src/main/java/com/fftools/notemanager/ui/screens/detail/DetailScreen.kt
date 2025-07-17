package com.fftools.notemanager.ui.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fftools.notemanager.MainViewModel
import com.fftools.notemanager.Screen
import com.fftools.notemanager.ui.screens.home.HomeViewModel

@ExperimentalMaterial3Api
@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    index: Int
) {
    Scaffold(
        topBar = {
             TopAppBar(title = { Text(text = "Detail Screen") }) }
    ) {
      Column(modifier = Modifier
          .fillMaxSize()
          .padding(it)
      ) {
          DetailNote(viewModel, index, navController)
      }
    }
}

@Composable
fun DetailNote(
    viewModel: HomeViewModel,
    index: Int,
    navController: NavController,
) {
    val state = viewModel.uiState.collectAsState()
    if (index >= 0) {
        Column {
            Text(text = "id: ${state.value.notes[index].id}")
            Box(Modifier.height(10.dp))
            Text(text = "title: ${state.value.notes[index].title}")
            Box(Modifier.height(10.dp))
            Text(text = "content: ${state.value.notes[index].content}")
            ElevatedButton(onClick = { navController.navigate("${Screen.AddOrEdit.route}?noteIndex=${index}") }) {
                Text(text = "Edit Note")
            }
        }
    }
}