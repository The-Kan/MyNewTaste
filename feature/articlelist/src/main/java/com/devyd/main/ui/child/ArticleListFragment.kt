package com.devyd.main.ui.child

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.devyd.main.R
import com.devyd.main.ui.common.Constants

class ArticleListFragment : Fragment(R.layout.fragment_articlelist) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_send_to_fragment1)
            .setOnClickListener {
                val dataBundle = bundleOf(
                    Constants.ARTICLE_ID to 1,
                )

                parentFragmentManager.setFragmentResult(
                    Constants.ARTICLE_CLICK,
                    dataBundle
                )
            }
    }
}