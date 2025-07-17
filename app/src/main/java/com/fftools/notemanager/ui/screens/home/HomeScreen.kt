@file:OptIn(ExperimentalMaterial3Api::class)

package com.fftools.notemanager.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.fftools.notemanager.MainViewModel
import com.fftools.notemanager.Screen
import com.fftools.notemanager.common.enum.LoadStatus
import com.fftools.notemanager.ui.screens.detail.DetailNote

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    mainViewModel: MainViewModel
) {
    val state by viewModel.uiState.collectAsState()

    val screenWith = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(Unit) {
        viewModel.loadNote() // Load notes when the screen is first displayed
    }

    Scaffold(
        topBar = {
             TopAppBar(title = { Text("Note Manager") }) // Uncomment if you want a top bar
        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddOrEdit.route) // Navigate to Add or Edit screen
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) {
        Column (modifier =  Modifier.padding(it)) { // Them padding cho statusbar
            if(state.status is LoadStatus.Loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                if (state.status is LoadStatus.Error) {
                    mainViewModel.setError(state.status.description)
                    viewModel.reset() // Reset state after showing error
                }
                if (screenWith < 600.dp) {
                    ListNote(state, navController, viewModel, false)
                } else {
                    Row {
                        Box(modifier = Modifier.weight(1f)) {
                            ListNote(state, navController, viewModel, true)
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            DetailNote(viewModel, state.selectedIndex, navController)
                        }
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(
//        navController = NavHostController(context = LocalContext.current),
//        viewModel = HomeViewModel(null, null),
//        mainViewModel = MainViewModel()
//    )
//}

@Composable
fun ListNote(
    state: HomeUiState,
    navController: NavController,
    viewModel: HomeViewModel,
    isSplitMode: Boolean
) {
    LazyColumn(Modifier.padding(16.dp)) {
        items(state.notes.size) { index ->
            ListItem(
                modifier = Modifier.clickable {
                    if (!isSplitMode) {
                        navController.navigate("${Screen.Detail.route}?noteIndex=${index}")
                    } else {
                        viewModel.selectNote(index) // Select note for detail view in split mode
                    }
                },
                overlineContent = { Text(text = state.notes[index].id.toString()) },
                headlineContent = { Text(text = state.notes[index].title) },
                supportingContent = { Text(text = state.notes[index].content) },
                trailingContent = {
                    IconButton(onClick = { viewModel.deleteNote(state.notes[index].id) }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Note")
                    }
                }
            )
        }
    }
}