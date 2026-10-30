package com.shevelev.foldable.repository

import com.shevelev.foldable.repository.model.ColorCode
import com.shevelev.foldable.repository.model.ColorsState
import kotlinx.coroutines.flow.Flow

internal interface ColorRepository {
    val colorState: Flow<ColorsState>

    fun updateColor(color: ColorCode)
}