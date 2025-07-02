package com.devyd.main.ui.child

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devyd.main.R

class ChildFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = TapList.titles.size

    override fun createFragment(position: Int): Fragment {
       return ArticleListFragment()
    }
}


object TapList {
     val titles = listOf(
        R.string.tab_home,
        R.string.tab_all,
        R.string.tab_society,
        R.string.tab_economy,
        R.string.tab_favorites,
    )
}
