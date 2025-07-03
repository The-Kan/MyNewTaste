package com.devyd.articlelist.ui.parent


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.articlelist.R
import com.devyd.articlelist.databinding.FragmentArticlelistcontainerBinding
import com.devyd.articlelist.ui.child.ChildFragmentStateAdapter
import com.devyd.articlelist.ui.child.TapList
import com.devyd.common.Constants
import com.google.android.material.tabs.TabLayoutMediator

class ArticleListContainerFragment : Fragment() {

    private var binding: FragmentArticlelistcontainerBinding? = null

    private var articleClickListener: ArticleClickListener? = null
    private var settingClickListener: SettingClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticlelistcontainerBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.i(logTag(), "onViewCreated")

        val viewPager = binding?.viewPager!!
        viewPager.adapter = ChildFragmentStateAdapter(this)

        val tabLayout = binding?.tabLayout!!

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(TapList.titles[position])
        }.attach()


        binding?.topToolbar?.setOnMenuItemClickListener { menuItem ->
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
            if (requestKey == Constants.ARTICLE_CLICK) {
                val articleId = bundle.getInt(Constants.ARTICLE_ID)
                articleClickListener?.onArticleClicked(articleId)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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