# PRD: Homely Widgets — v1.0

## 1. Overview

**Product Name:** Homely Widgets  
**Platform:** Android only (min SDK 26 / Android 8.0)  
**Version:** 1.0.0  
**Goal:** A collection of aesthetic, animated Android home screen widgets for quick system controls and live status display, following Material You design language with dynamic color theming.

---

## 2. Companion App (Flutter)

The Flutter app serves as a **launcher** only in v1:

- Splash screen with app branding
- Onboarding screen: step-by-step guide on how to add a widget to the home screen
- Widget gallery: preview cards of all 7 widgets (static, not interactive)
- Permission setup flow: prompt users to grant Device Admin (lock) and Camera (flashlight) permissions
- No widget configuration or customization in v1 (deferred to v2)

---

## 3. Widgets — v1 Scope

All widgets follow **Material You** with dynamic color extraction from the system wallpaper.

### 3.1 Lock Button Widget

- **Size:** 1×1
- **Action:** Lock device immediately via `DevicePolicyManager.lockNow()`
- **Requirement:** User must activate Device Admin once (guided in onboarding)
- **Visual:** Padlock icon, animated press ripple, subtle bounce animation on tap

### 3.2 Battery Status Widget

- **Size:** 2×1
- **Action:** Display only (no control)
- **Data:** Battery percentage, charging state (charging / discharging / full)
- **Visual:** Circular animated battery indicator, color shifts (green → amber → red) based on level, lightning bolt icon when charging
- **Updates:** Via `BatteryManager` broadcast, refreshed every 5 minutes or on change

### 3.3 Airplane Mode Widget

- **Size:** 1×1
- **Action:** Cannot toggle directly on Android 4.2+ (system-app privilege required). Tapping opens the **Quick Settings panel** (`Settings.ACTION_AIRPLANE_MODE_SETTINGS`)
- **Status display:** Reads current state via `Settings.Global.AIRPLANE_MODE_ON`
- **Visual:** Plane icon, on/off tint overlay, animated icon rotation on state change

### 3.4 Wi-Fi Toggle Widget

- **Size:** 1×1
- **Action:** Direct toggle deprecated on Android 10+. Tapping opens **`Settings.Panel.ACTION_WIFI`** bottom sheet panel
- **Status display:** Shows connected SSID or "Off"
- **Visual:** Wi-Fi arc icon with signal bars animation on connect state change

### 3.5 Flashlight Toggle Widget

- **Size:** 1×1
- **Action:** Real toggle via **Camera2 API** `CameraManager.setTorchMode()`
- **Permission:** `CAMERA`
- **Visual:** Torch icon with warm yellow glow when ON, greyed when OFF

### 3.6 Clock Widget

- **Size:** 2×1 (digital) or 2×2 (analog style)
- **Action:** Display only
- **Data:** Live time + date, updates every minute via `AlarmManager`
- **Visual:** Material You typography, smooth hand animation for analog variant

### 3.7 Volume / Ringer Mode Widget

- **Size:** 1×1
- **Action:** Cycle ringer modes: Ring → Vibrate → Silent via `AudioManager`
- **Permission:** `MODIFY_AUDIO_SETTINGS`
- **Visual:** Icon cycles between bell / vibration / mute icons with a morph animation

---

## 4. Design Guidelines

| Token          | Spec                                                     |
| -------------- | -------------------------------------------------------- |
| Color system   | Material You / Monet dynamic color (wallpaper-based)     |
| Corner radius  | 20dp (widget container), 12dp (inner cards)              |
| Elevation      | Glance widget surface elevation via tonal overlay        |
| Animation      | Tap ripple + icon morph; state-change transitions ~200ms |
| Dark/Light     | Follows system theme automatically                       |
| Typography     | Material 3 type scale (Roboto)                           |
| Widget padding | 12dp all sides                                           |

---

## 5. Technical Architecture

### 5.1 Stack

| Layer                        | Technology                                                       |
| ---------------------------- | ---------------------------------------------------------------- |
| Companion app                | Flutter + Dart                                                   |
| Widget rendering             | Android Jetpack Glance (Kotlin)                                  |
| Flutter ↔ Widget data bridge | `home_widget` Flutter package                                    |
| System interactions          | Native Kotlin (DevicePolicyManager, AudioManager, CameraManager) |
| Widget state updates         | BroadcastReceiver + `GlanceAppWidgetManager.updateIf<>()`        |

### 5.2 Project Structure

```
lib/
  main.dart
  screens/
    splash_screen.dart
    onboarding_screen.dart
    widget_gallery_screen.dart
  widgets/
    gallery_card.dart

android/app/src/main/kotlin/.../
  widgets/
    BatteryWidget.kt
    ClockWidget.kt
    LockWidget.kt
    AirplaneModeWidget.kt
    WifiWidget.kt
    FlashlightWidget.kt
    VolumeWidget.kt
  receivers/
    BatteryReceiver.kt
    AirplaneModeReceiver.kt
  services/
    DeviceAdminReceiver.kt
    WidgetUpdateService.kt
```

### 5.3 Permissions (AndroidManifest.xml)

```
BIND_DEVICE_ADMIN        → Lock widget
CAMERA                   → Flashlight widget
MODIFY_AUDIO_SETTINGS    → Volume widget
ACCESS_WIFI_STATE        → Wi-Fi status display
RECEIVE_BOOT_COMPLETED   → Restore widget state on reboot
FOREGROUND_SERVICE       → Widget update service (if needed)
```

---

## 6. Known Android Limitations

| Feature              | Limitation                                           | Handled by                        |
| -------------------- | ---------------------------------------------------- | --------------------------------- |
| Airplane mode toggle | Requires system app on Android 4.2+                  | Show status + open Settings panel |
| Wi-Fi direct toggle  | Deprecated on Android 10+ (`setWifiEnabled`)         | Open `Settings.Panel.ACTION_WIFI` |
| Lock button          | Requires one-time Device Admin activation by user    | Onboarding permission setup flow  |
| Widget animations    | RemoteViews/Glance animations are limited vs Flutter | Icon morphs and tonal overlays    |

---

## 7. Out of Scope (v1)

- Widget customization (size, color, labels)
- iOS / other platforms
- Widgets for third-party apps (e.g. Spotify, Calendar)
- Widget configuration screen
- Cloud sync of preferences
- Bluetooth toggle (Android 12+ restrictions make this impractical)

---

## 8. Success Criteria

- [ ] All 7 widgets installable from Android launcher via long-press
- [ ] Lock, Flashlight, Volume widgets perform real system actions
- [ ] Airplane Mode and Wi-Fi widgets open correct system panels
- [ ] Battery and Clock update on schedule without noticeable battery drain
- [ ] All widgets adapt to system dark/light theme and wallpaper color (Material You)
- [ ] Companion app guides new users end-to-end to add widgets
- [ ] Tested on Android 8.0 (API 26 — min), 12, 13, 14, 15

---

## 9. Implementation Phases

| Phase   | Description                                                    | Status         |
| ------- | -------------------------------------------------------------- | -------------- |
| Phase 0 | PRD file creation                                              | ✅ Done        |
| Phase 1 | Project setup: `home_widget` package, Glance deps, permissions | ✅ Done        |
| Phase 2 | Flutter companion app: splash, onboarding, gallery screens     | 🔲 Not started |
| Phase 3 | Glance widget skeletons (all 7) with Material You theming      | 🔲 Not started |
| Phase 4 | System interactions per widget (Kotlin native code)            | 🔲 Not started |
| Phase 5 | BroadcastReceivers + update scheduling                         | 🔲 Not started |
| Phase 6 | Polish: animations, icon morphs, tonal overlays                | 🔲 Not started |
| Phase 7 | Testing across Android versions                                | 🔲 Not started |
