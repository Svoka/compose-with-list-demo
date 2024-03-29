package com.svoka.composervtest.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.svoka.composervtest.Item
import com.svoka.composervtest.ItemsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ItemsViewModel : ViewModel() {

    private val list = mutableListOf<Item>().apply {
        for (i in 1..1000) {
            add(Item("$i", "Test$i"))
        }
    }

    private val _uiState = MutableStateFlow(ItemsState(list))

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val uiState: StateFlow<ItemsState> = _uiState.asStateFlow()

    init {
        scope.launch {
            tickerFlow(5.toDuration(DurationUnit.SECONDS)).collect{
                val l = _uiState.value.items.toMutableList()
                if (l.isNotEmpty()) {
                    l.removeLast()
                    _uiState.emit(ItemsState(l))
                }
            }
        }
    }

    private fun tickerFlow(period: Duration, initialDelay: Duration = Duration.ZERO) = flow {
        delay(initialDelay)
        while (true) {
            emit(Unit)
            delay(period)
        }
    }
}


