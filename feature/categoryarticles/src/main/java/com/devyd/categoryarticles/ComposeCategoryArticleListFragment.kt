package com.devyd.categoryarticles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.devyd.common.Constants
import com.devyd.ui.models.ArticleUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeCategoryArticleListFragment : Fragment() {

    private val category: String by lazy {
        arguments?.getString(CATEGORY) ?: "default"
    }

    companion object {
        fun newInstance(category: String): ComposeCategoryArticleListFragment {
            return ComposeCategoryArticleListFragment().apply {
                arguments = bundleOf(CATEGORY to category)
            }
        }

        private const val CATEGORY = "category"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val onArticleClick: (ArticleUiState) -> Unit = {
            val dataBundle = bundleOf(Constants.ARTICLE to it)
            parentFragmentManager.setFragmentResult(Constants.ARTICLE_CLICK, dataBundle)
        }


        return ComposeView(requireContext()).apply {
            setContent {
                CategoryArticleListScreen(category, onArticleClick)
            }
        }
    }
}