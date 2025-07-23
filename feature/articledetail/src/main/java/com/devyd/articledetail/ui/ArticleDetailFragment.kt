package com.devyd.articledetail.ui

import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.devyd.articledetail.R
import com.devyd.articledetail.databinding.FragmentArticledetailBinding
import com.devyd.articledetail.models.ArticleUiStateResult
import com.devyd.articledetail.models.BookmarkResult
import com.devyd.articledetail.models.IsBookMarkedResult
import com.devyd.articledetail.models.UnBookMarkResult
import com.devyd.articledetail.vm.ArticleDetailViewModel
import com.devyd.common.Constants
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.ui.models.ArticleUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private var _binding: FragmentArticledetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ArticleDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticledetailBinding.inflate(inflater, container, false)

        var articleUiState: ArticleUiState? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            articleUiState =
                requireArguments().getParcelable(Constants.ARTICLE, ArticleUiState::class.java)
        } else {
            @Suppress("DEPRECATION")
            articleUiState =
                requireArguments().getParcelable(Constants.ARTICLE) as? ArticleUiState
        }

        viewModel.setArticleUiState(articleUiState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articleUiState.collect {
                    updateUi(it)
                }
            }
        }
    }

    private fun updateUi(articleUiStateResult: ArticleUiStateResult) {
        when (articleUiStateResult) {
            ArticleUiStateResult.Loading -> {
                renderState(true, false, false)
            }

            is ArticleUiStateResult.Failure -> {
                renderState(false, true, false)
            }

            is ArticleUiStateResult.Success -> {
                val articleUiState = articleUiStateResult.articleUiState
                viewModel.isBookMarked(articleUiState)

                var isBookmarked = false

                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        when (val result = viewModel.isBookMarkedResult.first()) {
                            is IsBookMarkedResult.Failure -> {}
                            is IsBookMarkedResult.Success -> {
                                isBookmarked = result.isBookmarked
                                binding.saveLotti.progress = if (isBookmarked) 1f else 0f
                            }
                        }
                    }
                }


                binding.apply {
                    articleUiState.also {
                        Glide.with(headerImage.context).load(it.urlToImage).centerCrop()
                            .into(headerImage)

                        articlePublishedAt.text = getString(R.string.published_at, it.publishedAt)
                        articleAuthor.text = getString(R.string.author, it.author)
                        articleSourceName.text = getString(R.string.from, it.sourceUiState.name)

                        articleTitle.text = it.title
                        articleContent.text = it.content

                        articleDescription.text = it.description

                        val html = "<a href=\"${articleUiState?.url}\">read more</a>"
                        val spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)

                        this.articleUrl.apply {
                            text = spanned
                            linksClickable = true
                            movementMethod = LinkMovementMethod.getInstance()
                        }

                        var isBlocked = false

                        saveLotti.setOnClickListener {
                            if (!isBlocked) {
                                isBlocked = true

                                if (!isBookmarked) {
                                    saveLotti.setMinAndMaxProgress(0f, 1f)
                                    saveLotti.speed = 1.2f
                                    saveLotti.playAnimation()

                                    viewModel.bookMarkArticle(articleUiState)

                                } else {
                                    saveLotti.cancelAnimation()
                                    saveLotti.progress = 0f

                                    viewModel.unBookMarkArticle(articleUiState)
                                }
                            }
                        }

                        viewLifecycleOwner.lifecycleScope.launch {
                            repeatOnLifecycle(Lifecycle.State.STARTED) {
                                viewModel.bookMarkArticleResult
                                    .collect { result ->
                                        when (result) {
                                            is BookmarkResult.Failure -> {
                                                Toast.makeText(
                                                    context,
                                                    R.string.bookmark_fail,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                            is BookmarkResult.Success -> {
                                                isBookmarked = true
                                                isBlocked = false
                                            }
                                        }
                                    }
                            }
                        }


                        viewLifecycleOwner.lifecycleScope.launch {
                            repeatOnLifecycle(Lifecycle.State.STARTED) {
                                viewModel.unBookMarkArticleResult
                                    .collect { result ->
                                        when (result) {
                                            is UnBookMarkResult.Failure -> {
                                                Toast.makeText(
                                                    context,
                                                    R.string.unbookmark_fail,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                            is UnBookMarkResult.Success -> {
                                                isBookmarked = false
                                                isBlocked = false
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
                renderState(false, false, true)
            }
        }
    }

    private fun renderState(isLoading: Boolean, isFail: Boolean, isSuccess: Boolean) {
        binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.failTextView.visibility = if (isFail) View.VISIBLE else View.GONE
        binding.coordinatorLayout.visibility = if (isSuccess) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
