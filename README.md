Weather App

Description

Real-Time Weather Updates: Get instant weather forecasts including temperature, humidity, wind
speed, and more.
City Search: Easily search for any city to view current weather conditions.
Offline Support: Access cached weather data when offline.
User-Friendly Interface: A clean and simple design makes it easy to navigate and view weather
details.

Prerequisites
Android Studio (version 2024.2.2 or higher)
Java JDK (version 21 or higher)
Gradle (gradle-8.4-bin )

Setting Up the Environment
Open the project in Android Studio:
Select Get From Vcs

git clone https://github.com/rakibesachin166/Weather-App.git


Install Dependencies:

Ensure all dependencies are downloaded by syncing Gradle.
File > Sync Project with Gradle Files
Set up native builds:

Make sure that external native builds ( CMake or NDK (version 25 )) are configured correctly in the
build.gradle files.
Running the App
Run the app:
Select the desired device/emulator.
Click on the Run button or Shift + F10 to build and run the application.
Building the App
Build the app:
You can also build the app by navigating to Build > Build Bundle(s) / APK(s) from the toolbar to
generate APKs or other build types.
Troubleshooting
If you encounter build errors, check the build.gradle files and Ndk Installed Check For cMake u have
enable in Sdk Setup and download Ndk for configuration issues.
For native build issues, ensure that CMake or NDK configurations are correctly set up.

If the Api Key Get Expire Please check below website get new Api Key
https://weatherstack.com/

In the project Directory native-lib.cpp File Contain the Api Key Please Change That

