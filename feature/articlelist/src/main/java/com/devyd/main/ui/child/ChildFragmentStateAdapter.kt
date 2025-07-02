package com.devyd.main.ui.child

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ChildFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = TapList.fragments.size

    override fun createFragment(position: Int): Fragment = TapList.fragments[position]
}


object TapList {

    private val list = listOf(
        "MyNewsTaste" to ArticleListFragment(),
        "종합 뉴스" to ArticleListFragment(),
        "사회" to ArticleListFragment(),
        "경제" to ArticleListFragment(),
        "찜" to ArticleListFragment(),
    )
    val titles = list.map { it.first }
    val fragments = list.map { it.second }
}
