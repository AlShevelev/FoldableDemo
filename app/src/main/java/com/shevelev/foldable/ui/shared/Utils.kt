package com.shevelev.foldable.ui.shared

import androidx.compose.ui.graphics.Color
import com.shevelev.foldable.repository.model.ColorCode

internal val ColorCode.uiColor: Color
    get() = when (this) {
        ColorCode.RED -> Color.Red
        ColorCode.GREEN -> Color.Green
        ColorCode.BLUE -> Color.Blue
    }