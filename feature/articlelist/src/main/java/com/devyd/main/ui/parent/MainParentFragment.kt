package com.devyd.main.ui.parent


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.main.R
import com.devyd.main.ui.child.ChildFragmentStateAdapter
import com.devyd.main.ui.child.TapList
import com.devyd.main.ui.common.Constants
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainParentFragment : Fragment(R.layout.fragment_mainparent) {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private var hostListener: ArticleClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.i(logTag(), "onViewCreated")

        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager.adapter = ChildFragmentStateAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(TapList.titles[position])
        }.attach()

        childFragmentManager.setFragmentResultListener(
            Constants.ARTICLE_CLICK,
            this.viewLifecycleOwner
        ) { requestKey, bundle ->
            if(requestKey == Constants.ARTICLE_CLICK){
                val articleId = bundle.getInt(Constants.ARTICLE_ID)
                hostListener?.onArticleClicked(articleId)
            }
        }
    }

    fun setArticleClickListener(l: ArticleClickListener) {
        hostListener = l
    }

    fun interface ArticleClickListener {
        fun onArticleClicked(articleId: Int)
    }
}