package com.shevelev.foldable.ui.controlPanel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.foldable.repository.ColorRepository
import com.shevelev.foldable.repository.model.ColorCode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class ControlPanelViewModel
@Inject
constructor(
    private val repository: ColorRepository,
) : ViewModel() {
    val state = repository.colorState.map {
        ControlPanelViewState(
            colors = it.colors.toImmutableList(),
            selectedColor = it.selectedColor,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    fun selectColor(color: ColorCode) {
        viewModelScope.launch {
            repository.updateColor(color)
        }
    }
}