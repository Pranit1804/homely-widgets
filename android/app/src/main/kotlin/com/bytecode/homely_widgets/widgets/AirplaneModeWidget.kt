package com.bytecode.homely_widgets.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.text.Text

// Phase 3: Full Airplane Mode widget UI — icon tinted on/off, reads Settings.Global.AIRPLANE_MODE_ON.
// Phase 4: Tap opens Settings.ACTION_AIRPLANE_MODE_SETTINGS.
// Phase 5: Refreshes via android.intent.action.AIRPLANE_MODE broadcast.
class AirplaneModeWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent { AirplaneModeWidgetContent() }
    }
}

@Composable
private fun AirplaneModeWidgetContent() {
    Text(text = "Airplane")
}

class AirplaneModeWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = AirplaneModeWidget()
}
