package com.bytecode.homely_widgets.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.text.Text

// Phase 3: Full Volume widget UI — icon morphs Ring / Vibrate / Silent.
// Phase 4: Cycles ringer modes via AudioManager (requires MODIFY_AUDIO_SETTINGS).
class VolumeWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent { VolumeWidgetContent() }
    }
}

@Composable
private fun VolumeWidgetContent() {
    Text(text = "Volume")
}

class VolumeWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = VolumeWidget()
}
