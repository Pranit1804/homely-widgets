package com.bytecode.homely_widgets.receivers

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent

// Device Admin receiver required for DevicePolicyManager.lockNow() in the Lock widget.
// Users must manually activate this as a Device Admin via the onboarding flow (one-time setup).
// Only the force-lock policy is declared in res/xml/device_admin_policies.xml — no data wipe.
class HomelyDeviceAdminReceiver : DeviceAdminReceiver() {

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        // Phase 2: Notify companion app that Device Admin is active.
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        // Phase 2: Notify companion app that Device Admin was revoked.
    }
}
