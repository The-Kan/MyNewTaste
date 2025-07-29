package com.devyd.allcategoryarticles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.devyd.ui.models.ArticleUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeAllArticleListFragment(private val onArticleClick: (ArticleUiState) -> Unit) :
    Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AllArticleListScreen(onArticleClick)
            }
        }
    }
}