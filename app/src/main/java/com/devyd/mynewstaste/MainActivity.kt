package com.devyd.mynewstaste

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.main.ui.parent.MainParentFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Add Padding of Edge to Edge to
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController: NavController = navHost.navController

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if(destination.id == R.id.fragment1){
               for(childFragment in navHost.childFragmentManager.fragments){
                   if(childFragment is MainParentFragment){
                       childFragment.setArticleClickListener {
                           LogUtil.i(logTag(), "${it} 디테일 화면으로 전환")
                       }
                   }
               }
            }
        }

    }
}