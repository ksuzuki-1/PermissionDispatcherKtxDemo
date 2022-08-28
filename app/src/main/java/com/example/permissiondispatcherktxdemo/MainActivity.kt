package com.example.permissiondispatcherktxdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
