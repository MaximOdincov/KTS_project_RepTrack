package com.example.ktsproject_reptrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.ktsproject_reptrack.presentation.navigation.AppNavigation
import com.example.ktsproject_reptrack.ui.theme.KTSProjectRepTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            KTSProjectRepTrackTheme(darkTheme = true) {
                AppNavigation(onFinish = { finishAffinity() })
            }
        }
    }
}
