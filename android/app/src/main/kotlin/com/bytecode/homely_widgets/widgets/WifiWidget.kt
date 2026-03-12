package com.bytecode.homely_widgets.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.text.Text

// Phase 3: Full Wi-Fi widget UI — arc signal icon, SSID or "Off" label.
// Phase 4: Tap opens Settings.Panel.ACTION_WIFI (direct toggle deprecated Android 10+).
// Phase 5: Refreshes via android.net.wifi.WIFI_STATE_CHANGED broadcast.
class WifiWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent { WifiWidgetContent() }
    }
}

@Composable
private fun WifiWidgetContent() {
    Text(text = "Wi-Fi")
}

class WifiWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = WifiWidget()
}
