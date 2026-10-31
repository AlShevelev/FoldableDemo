package com.shevelev.foldable.ui.controlPanel

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shevelev.foldable.repository.model.ColorCode
import com.shevelev.foldable.ui.shared.HingeType
import com.shevelev.foldable.ui.shared.uiColor
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun ColorSelector(
    colors: ImmutableList<ColorCode>,
    selectedColor: ColorCode,
    hingeType: HingeType,
    onColorPick: (ColorCode) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (hingeType) {
        HingeType.HORIZONTAL -> {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                colors.forEach { color ->
                    ColorSelectorButton(
                        modifier = Modifier.size(64.dp),
                        color = color,
                        selected = selectedColor == color,
                        onClick = { onColorPick(color) }
                    )
                }
            }
        }

        HingeType.VERTICAL -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                colors.forEach { color ->
                    ColorSelectorButton(
                        modifier = Modifier.size(64.dp),
                        color = color,
                        selected = selectedColor == color,
                        onClick = { onColorPick(color) }
                    )
                }
            }
        }

        HingeType.NONE -> { }
    }
}


@Composable
private fun ColorSelectorButton(
    color: ColorCode,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier
            .clip(CircleShape)
            .border(
                width = 4.dp,
                color = if (selected) Color.DarkGray else Color.Transparent,
                shape = CircleShape,
            )
            .background(color = color.uiColor)
            .clickable(onClick = onClick)
    )
}
