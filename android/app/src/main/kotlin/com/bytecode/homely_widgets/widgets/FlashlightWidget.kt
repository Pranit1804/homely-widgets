package com.bytecode.homely_widgets.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.text.Text

// Phase 3: Full Flashlight widget UI — torch icon, warm yellow glow when ON.
// Phase 4: Real toggle via CameraManager.setTorchMode() (requires CAMERA permission).
class FlashlightWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent { FlashlightWidgetContent() }
    }
}

@Composable
private fun FlashlightWidgetContent() {
    Text(text = "Flashlight")
}

class FlashlightWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = FlashlightWidget()
}
