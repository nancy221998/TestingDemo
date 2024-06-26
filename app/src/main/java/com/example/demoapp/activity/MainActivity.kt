package com.example.demoapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.demoapp.activity.pages.GridScreen
import com.example.demoapp.activity.pages.TopAppBarWithIcons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
              Scaffold(
                Modifier.background(Color.Transparent),
                topBar = {
                    TopAppBarWithIcons(
                        title = "Acharya Prashant",
                        onNavigationIconClick = { },
                        onActionIconClick = { }
                    )
                },

                content = { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding))
                    {
                        MyApp()
                    }
                }
            )
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun MyApp(){
        GridScreen(this)
    }
}