package com.devyd.main.ui.child

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.devyd.main.R

class ArticleListFragment : Fragment(R.layout.fragment_articlelist) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.btn_send_to_fragment1)
            .setOnClickListener {
                val dataBundle = bundleOf(
                    "articleId" to 123,
                    "articleTitle" to "안드로이드 모듈 통신"
                )

                requireActivity().supportFragmentManager.setFragmentResult(
                    "NAV_TO_FEATURE3",
                    dataBundle
                )
            }
    }
}