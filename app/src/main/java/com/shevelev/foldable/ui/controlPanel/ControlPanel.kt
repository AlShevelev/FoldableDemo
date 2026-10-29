package com.shevelev.foldable.ui.controlPanel

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shevelev.foldable.ui.shared.HingeType

@Composable
internal fun ControlPanel(
    hingeType: HingeType,
    modifier: Modifier = Modifier,
    viewModel: ControlPanelViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    state?.let {
        ColorSelector(
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize(),
            colors = it.colors,
            selectedColor = it.selectedColor,
            hingeType = hingeType,
            onColorPick = viewModel::selectColor
        )
    }
}
