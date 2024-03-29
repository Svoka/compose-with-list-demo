package com.svoka.composervtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.svoka.composervtest.ui.ItemsViewModel
import com.svoka.composervtest.ui.theme.ComposeRvTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRvTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   ItemsHolder()
                }
            }
        }
    }
}

@Composable
fun ItemsHolder(vm: ItemsViewModel = viewModel()) {
    val state by vm.uiState.collectAsState()
    Items(state)
}

@Composable
fun Items(state: ItemsState) {
    LazyColumn {
        itemsIndexed(state.items, key = { _, item -> item.id }) {_, item ->
            Box {
                Text(text = item.text)
            }
        }
    }
}

data class ItemsState(
    val items: List<Item>
)

data class Item(
    val id: String,
    val text: String
)