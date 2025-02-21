@file:Suppress("DEPRECATION")

package com.mikepenz.storyblok.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mikepenz.storyblok.App
import com.mikepenz.storyblok.viewmodel.AppViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class SampleActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModel<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(viewModel)
        }
    }
}
