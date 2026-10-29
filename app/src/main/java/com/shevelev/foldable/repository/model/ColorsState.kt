package com.shevelev.foldable.repository.model

internal data class ColorsState(
    val colors: List<ColorCode>,
    val selectedColor: ColorCode,
)