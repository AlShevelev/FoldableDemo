package com.shevelev.foldable.ui.mainArea

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shevelev.foldable.ui.shared.uiColor

@Composable
internal fun MainArea(
    modifier: Modifier = Modifier,
    viewModel: MainAreaViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    state?.let {
        Spacer(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .border(4.dp, Color.Gray, RoundedCornerShape(16.dp))
                .background(color = it.color.uiColor)
        )
    }
}