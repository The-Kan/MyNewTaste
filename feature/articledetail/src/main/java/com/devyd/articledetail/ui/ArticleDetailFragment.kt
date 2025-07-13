package com.devyd.articledetail.ui

import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.devyd.articledetail.R
import com.devyd.articledetail.databinding.FragmentArticledetailBinding
import com.devyd.articledetail.models.ArticleUiState
import com.devyd.common.Constants

class ArticleDetailFragment : Fragment() {

    private var _binding: FragmentArticledetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticledetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        var articleUiState: ArticleUiState? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            articleUiState =
                requireArguments().getParcelable(Constants.ARTICLE, ArticleUiState::class.java)
        } else {
            @Suppress("DEPRECATION")
            articleUiState = requireArguments().getParcelable(Constants.ARTICLE) as? ArticleUiState
        }


        binding.apply {
            articleUiState?.also {
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
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
