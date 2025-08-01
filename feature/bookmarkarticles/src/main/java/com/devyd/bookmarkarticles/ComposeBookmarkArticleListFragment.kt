package com.devyd.bookmarkarticles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.devyd.bookmarkarticles.adapter.BookmarkArticleAdapter
import com.devyd.bookmarkarticles.databinding.FragmentBookmarkArticlelistBinding
import com.devyd.bookmarkarticles.vm.BookmarkArticleListViewModel
import com.devyd.bookmarkarticles.vm.ComposeBookmarkArticleListViewModel
import com.devyd.common.Constants
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.ui.models.ArticleResult
import com.devyd.ui.models.ArticleUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComposeBookmarkArticleListFragment : Fragment() {


    private val viewModel: ComposeBookmarkArticleListViewModel by viewModels<ComposeBookmarkArticleListViewModel>()


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
                BookmarkArticleListScreen(onArticleClick)
            }
        }
    }




    override fun onStart() {
        super.onStart()
        viewModel.refreshArticle(false)
    }
}