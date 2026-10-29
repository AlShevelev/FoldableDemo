package com.shevelev.foldable.ui.controlPanel

import com.shevelev.foldable.repository.model.ColorCode
import kotlinx.collections.immutable.ImmutableList

internal data class ControlPanelViewState(
    val colors: ImmutableList<ColorCode>,
    val selectedColor: ColorCode,
)