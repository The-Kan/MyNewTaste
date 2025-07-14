package com.devyd.articlelist.ui.child

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devyd.articlelist.ui.child.TapList.titles
import com.devyd.articlelist.ui.child.category.CategoryArticleListFragment
import com.devyd.common.CategoryStrings
import java.util.Locale

class ChildFragmentStateAdapter(private val fragment: Fragment) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = titles.size

    override fun createFragment(position: Int): Fragment {
        val string = fragment.requireContext().getEnglishString(titles[position])
        if (CategoryStrings.contains(string)) return CategoryArticleListFragment.newInstance(string)

        return CategoryArticleListFragment.newInstance(CategoryStrings.BUSINESS)
    }

    private fun Context.getEnglishString(@StringRes resId: Int): String {
        val config = Configuration(resources.configuration).apply {
            setLocale(Locale.ENGLISH)
        }
        val englishContext = createConfigurationContext(config)
        return englishContext.resources.getString(resId)
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
