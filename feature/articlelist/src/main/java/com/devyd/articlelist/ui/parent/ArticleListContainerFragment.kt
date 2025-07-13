package com.devyd.articlelist.ui.parent


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devyd.articlelist.R
import com.devyd.articlelist.databinding.FragmentArticlelistcontainerBinding
import com.devyd.articlelist.ui.child.ChildFragmentStateAdapter
import com.devyd.articlelist.ui.child.TapList
import com.devyd.common.Constants
import com.devyd.articlelist.models.ArticleUiState
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.google.android.material.tabs.TabLayoutMediator

class ArticleListContainerFragment : Fragment() {

    private var _binding: FragmentArticlelistcontainerBinding? = null
    private val binding get() = _binding!!

    private var articleClickListener: ArticleClickListener? = null
    private var settingClickListener: SettingClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlelistcontainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.i(logTag(), "onViewCreated")

        val viewPager = binding.viewPager
        val adapter = ChildFragmentStateAdapter(this)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = adapter.itemCount

        val tabLayout = binding.tabLayout

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(TapList.titles[position])
        }.attach()


        binding.topToolbar.setOnMenuItemClickListener { menuItem ->
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

                var articleUiState: ArticleUiState? = null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    articleUiState =
                        bundle.getParcelable(Constants.ARTICLE, ArticleUiState::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    articleUiState = bundle.getParcelable(Constants.ARTICLE) as? ArticleUiState
                }


                articleClickListener?.onArticleClicked(articleUiState)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setArticleClickListener(l: ArticleClickListener) {
        articleClickListener = l
    }

    fun setSettingClickListener(l: SettingClickListener) {
        settingClickListener = l
    }
}

fun interface ArticleClickListener {
    fun onArticleClicked(articleUiState: ArticleUiState?)
}

fun interface SettingClickListener {
    fun onSettingClicked()
}