package com.devyd.mynewstaste

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.devyd.articlelist.ui.parent.ArticleListContainerFragment
import com.devyd.common.Constants
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.mynewstaste.databinding.ActivityMainBinding
import com.devyd.mynewstaste.mapper.toUiState
import com.devyd.settings.ui.CategorySettingsFragment
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


        val articleListContainerFragment = navHost
            .childFragmentManager
            .fragments
            .filterIsInstance<ArticleListContainerFragment>()
            .firstOrNull()


        navHost.childFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentViewCreated(
                    fragmentManager: FragmentManager,
                    fragment: Fragment,
                    view: View,
                    savedInstanceState: Bundle?
                ) {
                    if(fragment is ArticleListContainerFragment){
                        fragment.apply {
                            setArticleClickListener { articleUiState ->

                                val detailArticleUiState = articleUiState?.toUiState()

                                val args = bundleOf(Constants.ARTICLE to detailArticleUiState)
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
                            setCategorySettingGuideClickListener {
                                navController.navigate(
                                    R.id.categorySettingsFragment,
                                )
                                LogUtil.i(logTag(), "셋팅 화면으로 전환")
                            }
                        }
                    }
                    else if (fragment is CategorySettingsFragment) {
                        fragment.setBackPressListener {
                            articleListContainerFragment?.registerViewLifecycleOwnerLiveData()
                        }
                    }
                }
            },
            true
        )
    }
}