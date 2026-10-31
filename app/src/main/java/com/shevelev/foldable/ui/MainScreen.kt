package com.shevelev.foldable.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateBounds
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.adaptive.collectFoldingFeaturesAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.constrain
import androidx.compose.ui.unit.dp
import androidx.window.layout.FoldingFeature
import com.shevelev.foldable.HorizontalHingeBottomRuler
import com.shevelev.foldable.HorizontalHingeTopRuler
import com.shevelev.foldable.LocalLookaheadScope
import com.shevelev.foldable.VerticalHingeLeftRuler
import com.shevelev.foldable.VerticalHingeRightRuler
import com.shevelev.foldable.ui.controlPanel.ControlPanel
import com.shevelev.foldable.ui.mainArea.MainArea
import com.shevelev.foldable.ui.shared.HingeType
import kotlin.math.roundToInt

@Composable
internal fun MainScreen(
    hingeType: HingeType,
    modifier: Modifier = Modifier,
    lookaheadScope: LookaheadScope? = LocalLookaheadScope.current,
) {
    val foldingFeatures by collectFoldingFeaturesAsState()

    val fold = foldingFeatures.firstOrNull()

    Box(modifier.safeDrawingPadding()) {
        MainArea(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (fold?.state == FoldingFeature.State.HALF_OPENED) {
                        when (hingeType) {
                            HingeType.HORIZONTAL -> Modifier.alignAboveHinge()
                            HingeType.VERTICAL -> Modifier.alignToLeftOfHinge()
                            HingeType.NONE -> Modifier
                        }
                    } else {
                        Modifier
                    }
                )
                // To animate preview area bound (folded/unfolded)
                .animateBounds(requireNotNull(lookaheadScope) { "No LookaheadScope found" })
                .padding(16.dp)
        )

        AnimatedVisibility(
            visible = fold?.state == FoldingFeature.State.HALF_OPENED,
            enter = fadeIn() + when (hingeType) {
                HingeType.HORIZONTAL -> slideInVertically { it / 2 }
                HingeType.VERTICAL -> slideInHorizontally { it / 2 }
                HingeType.NONE -> EnterTransition.None
            },
            exit = fadeOut() + when (hingeType) {
                HingeType.HORIZONTAL -> slideOutVertically { it / 2 }
                HingeType.VERTICAL -> slideOutHorizontally { it / 2 }
                HingeType.NONE -> ExitTransition.None
            },
            modifier = when (hingeType) {
                HingeType.HORIZONTAL -> Modifier.alignBelowHinge()
                HingeType.VERTICAL -> Modifier.alignToRightOfHinge()
                HingeType.NONE -> Modifier
            }
        ) {
            ControlPanel(hingeType)
        }
    }
}

// Place the composable above the horizontal hinge, if a hinge is present.
// Ruler values are only available during the placement phase, so this modifier
// *measures* with max constraints, and then *places* the content above the hinge.
private fun Modifier.alignAboveHinge(): Modifier = this then
        Modifier.layout { measurable, constraints ->
            layout(constraints.maxWidth, constraints.maxHeight) {
                // Get current hinge top, or NaN if not available
                val hingeTop = HorizontalHingeTopRuler.current(defaultValue = Float.NaN)

                // Constrain the height of the composable to the hinge top (if available)
                val childConstraints = if (hingeTop.isNaN()) {
                    constraints
                } else {
                    Constraints(maxHeight = hingeTop.roundToInt()).constrain(constraints)
                }

                // Place the composable above the hinge
                val placeable = measurable.measure(childConstraints)
                placeable.place(0, 0)
            }
        }

// Place the composable below the horizontal hinge, if a hinge is present.
// Ruler values are only available during the placement phase, so this modifier
// *measures* with max constraints, and then *places* the content below the hinge.
private fun Modifier.alignBelowHinge(): Modifier = this then
        Modifier.layout { measurable, constraints ->
            layout(constraints.maxWidth, constraints.maxHeight) {
                // Get current hinge bottom, or default to 0 if not available
                val hingeBottom = HorizontalHingeBottomRuler.current(defaultValue = 0f).roundToInt()

                // Constrain the height of the composable to the hinge bottom (if available)
                val childConstraints = Constraints(maxHeight = constraints.maxHeight - hingeBottom)
                    .constrain(constraints)

                // Place the composable below the hinge
                val placeable = measurable.measure(childConstraints)
                placeable.place(0, hingeBottom)
            }
        }

// Place the composable to the left of vertical hinge, if a hinge is present.
// Ruler values are only available during the placement phase, so this modifier
// *measures* with max constraints, and then *places* the content to the left of the hinge.
private fun Modifier.alignToLeftOfHinge(): Modifier = this then
        Modifier.layout { measurable, constraints ->
            layout(constraints.maxWidth, constraints.maxHeight) {
                val hingeLeft = VerticalHingeLeftRuler.current(defaultValue = Float.NaN)

                // Constrain the height of the composable to the hinge top (if available)
                val childConstraints = if (hingeLeft.isNaN()) {
                    constraints
                } else {
                    Constraints(maxWidth = hingeLeft.roundToInt()).constrain(constraints)
                }

                // Place the composable above the hinge
                val placeable = measurable.measure(childConstraints)
                placeable.place(0, 0)
            }
        }

// Place the composable to the right of the vertical hinge, if a hinge is present.
// Ruler values are only available during the placement phase, so this modifier
// *measures* with max constraints, and then *places* the content to the right of the hinge.
private fun Modifier.alignToRightOfHinge(): Modifier = this then
        Modifier.layout { measurable, constraints ->
            layout(constraints.maxWidth, constraints.maxHeight) {
                // Get current hinge bottom, or default to 0 if not available
                val hingeRight = VerticalHingeRightRuler.current(defaultValue = 0f).roundToInt()

                // Constrain the height of the composable to the hinge bottom (if available)
                val childConstraints = Constraints(maxWidth = constraints.maxWidth - hingeRight)
                    .constrain(constraints)

                // Place the composable below the hinge
                val placeable = measurable.measure(childConstraints)
                placeable.place(hingeRight, 0)
            }
        }
