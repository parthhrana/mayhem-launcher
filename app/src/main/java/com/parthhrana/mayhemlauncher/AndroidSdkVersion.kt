package com.parthhrana.mayhemlauncher

import android.os.Build

fun androidSdkAtLeast(version: Int) = Build.VERSION.SDK_INT >= version
