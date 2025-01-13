package com.example.pieskoapka

kotlin("kapt")
import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class DogsApp: Application()