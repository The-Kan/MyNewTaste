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
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainParentFragment : Fragment(R.layout.fragment_mainparent) {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private var articleClickListener: ArticleClickListener? = null
    private var settingClickListener: SettingClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.i(logTag(), "onViewCreated")

        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager.adapter = ChildFragmentStateAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(TapList.titles[position])
        }.attach()


        val topToolbar = view.findViewById<MaterialToolbar>(R.id.top_toolbar)

        // 2) 메뉴 아이템 클릭 리스너 설정
        topToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> {
                    /**
                     *  Todo
                     *  Search is useful to show Article you want.
                     *  It will be implement
                     */
                    true
                }
                R.id.action_settings -> {
                    settingClickListener?.onSettingClicked()
                    true
                }
                else -> false
            }
        }


        childFragmentManager.setFragmentResultListener(
            Constants.ARTICLE_CLICK,
            this.viewLifecycleOwner
        ) { requestKey, bundle ->
            if(requestKey == Constants.ARTICLE_CLICK){
                val articleId = bundle.getInt(Constants.ARTICLE_ID)
                articleClickListener?.onArticleClicked(articleId)
            }
        }

    }

    fun setArticleClickListener(l: ArticleClickListener) {
        articleClickListener = l
    }

    fun setSettingClickListener(l: SettingClickListener) {
        settingClickListener = l
    }
}

fun interface ArticleClickListener {
    fun onArticleClicked(articleId: Int)
}

fun interface SettingClickListener {
    fun onSettingClicked()
}