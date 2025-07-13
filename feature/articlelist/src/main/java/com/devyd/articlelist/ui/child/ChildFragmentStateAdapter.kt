package com.devyd.articlelist.ui.child

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ChildFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = TapList.titles.size

    override fun createFragment(position: Int): Fragment {
        return ArticleListFragment()
    }
}


object TapList {
    val titles = listOf(
        com.devyd.articlelist.R.string.tab_home,
        com.devyd.articlelist.R.string.tab_all,
        com.devyd.common.R.string.category_1,
        com.devyd.common.R.string.category_2,
        com.devyd.common.R.string.category_3,
        com.devyd.common.R.string.category_4,
        com.devyd.common.R.string.category_5,
        com.devyd.common.R.string.category_6,
        com.devyd.common.R.string.category_7,
        com.devyd.articlelist.R.string.tab_favorites,
    )
}
