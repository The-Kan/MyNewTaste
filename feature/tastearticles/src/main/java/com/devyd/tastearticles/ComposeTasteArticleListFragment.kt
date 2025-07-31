package com.devyd.tastearticles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devyd.common.Constants
import com.devyd.tastearticles.vm.TasteArticleListViewModel
import com.devyd.ui.models.ArticleUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeTasteArticleListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val onArticleClick: (ArticleUiState) -> Unit = {
            val dataBundle = bundleOf(Constants.ARTICLE to it)
            parentFragmentManager.setFragmentResult(Constants.ARTICLE_CLICK, dataBundle)
        }

        val onSetCategoryClick: () -> Unit = {
            parentFragmentManager.setFragmentResult(
                Constants.CATEGORY_SETTING_GUIDE_CLICK,
                Bundle()
            )
        }

        return ComposeView(requireContext()).apply {
            setContent {
                TasteArticleListScreen(onArticleClick, onSetCategoryClick)
            }
        }
    }

    fun refreshArticle() {
        val viewModel by viewModels<TasteArticleListViewModel>()
        viewModel.refreshArticle(false)
    }
}