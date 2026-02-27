PACKAGE  := it.lo.exp.weander
ACTIVITY := .ui.home.HomeActivity
APK      := app/build/outputs/apk/debug/app-debug.apk

.PHONY: build install run release clean distclean logcat

build:
	gradle --no-daemon assembleDebug

install:
	gradle --no-daemon installDebug

run: install
	adb shell am start -n $(PACKAGE)/$(ACTIVITY)

release:
	gradle --no-daemon assembleRelease

clean:
	gradle --no-daemon clean

# Remove build outputs AND the local Gradle cache (.gradle/)
distclean: clean
	rm -rf .gradle/

logcat:
	adb logcat -s "Weander"
