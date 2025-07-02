package com.devyd.main.ui.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.devyd.main.databinding.FragmentArticlelistBinding
import com.devyd.main.ui.common.Constants

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

        binding?.btnSendToFragment1?.setOnClickListener {
            val dataBundle = bundleOf(
                Constants.ARTICLE_ID to 1,
            )

            parentFragmentManager.setFragmentResult(
                Constants.ARTICLE_CLICK,
                dataBundle
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}