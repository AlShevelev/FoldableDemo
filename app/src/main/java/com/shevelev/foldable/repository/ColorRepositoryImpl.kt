package com.shevelev.foldable.repository

import com.shevelev.foldable.repository.model.ColorCode
import com.shevelev.foldable.repository.model.ColorsState
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

internal class ColorRepositoryImpl @Inject constructor() : ColorRepository {
    override val colorState: StateFlow<ColorsState>
        field = MutableStateFlow< ColorsState>(
            ColorsState(
                colors = listOf(ColorCode.RED, ColorCode.GREEN, ColorCode.BLUE),
                selectedColor = ColorCode.RED
            )
        )

    override fun updateColor(color: ColorCode) =
        colorState.update { it.copy(selectedColor = color) }
}