package com.devyd.articlelist.ui.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.devyd.articlelist.R
import com.devyd.articlelist.databinding.FragmentArticlelistBinding
import com.devyd.articlelist.common.Constants
import com.devyd.articlelist.models.Article
import com.devyd.articlelist.ui.child.recyclerview.ArticleAdapter

class ArticleListFragment : Fragment() {

    private var binding: FragmentArticlelistBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticlelistBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sampleArticles = listOf(
            Article(1, "첫 번째 글", R.drawable.landscape_1),
            Article(2, "두 번째 글", R.drawable.landscape_2),
            Article(3, "세 번째 글", R.drawable.landscape_3),
            // … 원하는 만큼 추가
        )

        binding?.rvArticles?.apply {
            // 2열 그리드 레이아웃
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = ArticleAdapter(sampleArticles) { article ->
                // 아이템 클릭 시 FragmentResult 전송
                val dataBundle = bundleOf(
                    Constants.ARTICLE_ID to article.id
                )
                parentFragmentManager.setFragmentResult(
                    Constants.ARTICLE_CLICK,
                    dataBundle
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}