package com.devyd.main.ui.parent


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.devyd.main.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainParentFragment : Fragment(R.layout.fragment_mainparent) {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)

        viewPager.adapter = ChildFragmentStateAdapter(this)



        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TapList.titles[position]
        }.attach()
    }
}