package com.devyd.mynewstaste

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.articlelist.ui.parent.ArticleListContainerFragment
import com.devyd.common.Constants
import com.devyd.mynewstaste.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add Padding of Edge to Edge to
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHost =
            supportFragmentManager.findFragmentById(binding.navHostContainer.id) as NavHostFragment
        val navController: NavController = navHost.navController

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if(destination.id == R.id.articleListContainerFragment){
                val articleListContainerFragment = navHost
                    .childFragmentManager
                    .fragments
                    .filterIsInstance<ArticleListContainerFragment>()
                    .firstOrNull()

                articleListContainerFragment?.apply {
                    setArticleClickListener { id ->
                        val args = bundleOf(Constants.ARTICLE_ID to id)
                        navController.navigate(
                            R.id.articleDetailFragment,
                            args
                        )
                        LogUtil.i(logTag(), "$id 디테일 화면으로 전환")
                    }
                    setSettingClickListener {
                        navController.navigate(
                            R.id.categorySettingsFragment,
                        )
                        LogUtil.i(logTag(), "셋팅 화면으로 전환")
                    }
                }
            }
        }
    }
}