package com.devyd.articledetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devyd.articledetail.R
import com.devyd.articledetail.databinding.FragmentArticledetailBinding
import com.devyd.articledetail.models.Article
import com.devyd.common.Constants

class ArticleDetailFragment: Fragment() {

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

        val articleId = requireArguments().getInt(Constants.ARTICLE_ID)
        val article = getArticle(articleId)

        binding.apply {
            headerImage.setImageResource(article.imageRes)
            articleTitle.text = article.title
            articleContent.text = article.content
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getArticle(id: Int): Article {
        return when (id) {
            1 -> Article(
                1,
                "첫 번째 기사 제목",
                R.drawable.landscape_1,
                getString(R.string.sample_content1)
            )
            2 -> Article(
                2,
                "두 번째 기사 제목",
                R.drawable.landscape_2,
                getString(R.string.sample_content2)
            )
            3 -> Article(
                3,
                "세 번째 기사 제목",
                R.drawable.landscape_3,
                getString(R.string.sample_content3)
            )
            else -> Article(
                0,
                "제목 없음",
                R.drawable.landscape_3,
                "내용 없음"
            )
        }
    }
}
