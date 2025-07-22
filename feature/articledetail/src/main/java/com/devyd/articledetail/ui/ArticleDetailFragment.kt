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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticledetailBinding.inflate(inflater, container, false)

        var articleUiState: ArticleUiState? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            articleUiState =
                requireArguments().getParcelable(Constants.ARTICLE, ArticleUiState::class.java)
        } else {
            @Suppress("DEPRECATION")
            articleUiState = requireArguments().getParcelable(Constants.ARTICLE) as? ArticleUiState
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

                binding.apply {
                    articleUiState.also {
                        Glide.with(headerImage.context)
                            .load(it.urlToImage)
                            .centerCrop()
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
                        var isBookmarked = false

                        // 저장을 시작하면, 저장 기능을 잠시 막아야함. 그리고, isBookmarked 검사 기능이 있어야함.
                        saveLotti.setOnClickListener {
                            if (!isBlocked) {
                                isBlocked = true

                                if(!isBookmarked){
                                    saveLotti.setMinAndMaxProgress(0f, 1f)
                                    saveLotti.speed = 1.2f
                                    saveLotti.playAnimation()

                                    viewLifecycleOwner.lifecycleScope.launch {
                                        repeatOnLifecycle(Lifecycle.State.STARTED){
                                            val result = viewModel.bookMarkArticleResult.first()
                                            when(result){
                                                is BookmarkResult.Failure -> {
                                                    Toast.makeText(context, R.string.bookmark_fail, Toast.LENGTH_SHORT).show()
                                                }
                                                is BookmarkResult.Success -> {
                                                    LogUtil.i(logTag(),"Deok 저장 ${result.idx}")
                                                    isBookmarked = true
                                                    isBlocked = false
                                                }

                                            }
                                        }
                                    }

                                    viewModel.bookMarkArticle(articleUiState)

                                } else {
                                    saveLotti.cancelAnimation()
                                    saveLotti.progress = 0f

                                    viewLifecycleOwner.lifecycleScope.launch {
                                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                                            val result = viewModel.unBookMarkArticleResult.first()
                                            when(result) {
                                                is UnBookMarkResult.Failure -> {
                                                    Toast.makeText(context, R.string.unbookmark_fail, Toast.LENGTH_SHORT).show()
                                                }
                                                is UnBookMarkResult.Success -> {
                                                    LogUtil.i(logTag(),"Deok 삭제 ${result.num}")
                                                    isBookmarked = false
                                                    isBlocked = false
                                                }
                                            }
                                        }
                                    }

                                    viewModel.unBookMarkArticle(articleUiState)
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
