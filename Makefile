PACKAGE  := it.lo.exp.weander
ACTIVITY := .ui.home.HomeActivity
APK      := app/build/outputs/apk/debug/app-debug.apk

.PHONY: build install run release clean logcat

build:
	gradle assembleDebug

install:
	gradle installDebug

run: install
	adb shell am start -n $(PACKAGE)/$(ACTIVITY)

release:
	gradle assembleRelease

clean:
	gradle clean

logcat:
	adb logcat -s "Weander"
