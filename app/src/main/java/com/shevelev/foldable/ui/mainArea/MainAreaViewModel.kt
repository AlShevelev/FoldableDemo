package com.shevelev.foldable.ui.mainArea

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.foldable.repository.ColorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
internal class MainAreaViewModel
@Inject
constructor(
    repository: ColorRepository,
) : ViewModel() {
    val state = repository.colorState.map {
        MainAreaViewState(color = it.selectedColor)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )
}