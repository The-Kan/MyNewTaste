package com.devyd.main.ui.parent

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devyd.main.ui.child.ArticleListFragment

class ChildFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(ArticleListFragment(), ArticleListFragment(), ArticleListFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}