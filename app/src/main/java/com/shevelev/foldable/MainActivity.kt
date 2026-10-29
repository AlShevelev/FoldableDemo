package com.shevelev.foldable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.allHorizontalHingeBounds
import androidx.compose.material3.adaptive.allVerticalHingeBounds
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.HorizontalRuler
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.VerticalRuler
import androidx.compose.ui.layout.layout
import com.shevelev.foldable.ui.shared.HingeType
import com.shevelev.foldable.ui.MainScreen
import dagger.hilt.android.AndroidEntryPoint

val HorizontalHingeTopRuler = HorizontalRuler()
val HorizontalHingeBottomRuler = HorizontalRuler()

val VerticalHingeLeftRuler = VerticalRuler()
val VerticalHingeRightRuler = VerticalRuler()

// To animate preview area bound (folded/unfolded)
val LocalLookaheadScope = compositionLocalOf<LookaheadScope?> { null }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()
    setContent {
      MaterialTheme {
        // Receiving a hinge bounds on foldable devices
        val horizontalHinge = currentWindowAdaptiveInfoV2()
          .windowPosture
          .allHorizontalHingeBounds.firstOrNull()

        val verticalHinge = currentWindowAdaptiveInfoV2()
          .windowPosture
          .allVerticalHingeBounds.firstOrNull()

        LookaheadScope {
          CompositionLocalProvider(LocalLookaheadScope provides this) {
            Box(
              Modifier.layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                layout(
                  width = constraints.maxWidth,
                  height = constraints.maxHeight,
                  rulers = {
                    if (horizontalHinge != null) {
                      val bounds = coordinates.windowToLocal(horizontalHinge)
                      HorizontalHingeTopRuler provides bounds.top
                      HorizontalHingeBottomRuler provides bounds.bottom
                    }

                    if (verticalHinge != null) {
                      val bounds = coordinates.windowToLocal(verticalHinge)
                      VerticalHingeLeftRuler provides bounds.left
                      VerticalHingeRightRuler provides bounds.right
                    }
                  }
                ) { placeable.place(0, 0) }
              }
            ) {
              MainScreen(
                hingeType = when {
                  horizontalHinge != null -> HingeType.HORIZONTAL
                  verticalHinge != null -> HingeType.VERTICAL
                  else -> HingeType.NONE
                }
              )
            }
          }
        }
      }
    }
  }
}

private fun LayoutCoordinates.windowToLocal(rect: Rect): Rect =
  Rect(
    topLeft = windowToLocal(rect.topLeft),
    bottomRight = windowToLocal(rect.bottomRight),
  )