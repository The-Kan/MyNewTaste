package com.devyd.articlelist.ui.child.taste.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devyd.articlelist.R
import com.devyd.articlelist.databinding.CategoryArticleBinding
import com.devyd.articlelist.models.ArticleUiState

class TasteArticleAdapter(
    private var articleUiStateList: List<ArticleUiState>,
    private val onClick: (ArticleUiState) -> Unit
) : RecyclerView.Adapter<TasteArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(
        private val binding: CategoryArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(articleUiState: ArticleUiState) {
            binding.title.text = articleUiState.title
            // 예시: Glide 등을 이용해 실제 URL 로드 가능
            Glide.with(binding.thumbnail.context)
                .load(articleUiState.urlToImage)
                .centerCrop()
                .error(R.drawable.landscape_1)
                .into(binding.thumbnail)

            binding.root.setOnClickListener { onClick(articleUiState) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = CategoryArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleUiStateList[position])
    }

    override fun getItemCount(): Int = articleUiStateList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateArticlesUiState(articleUiStateList: List<ArticleUiState>) {
        this.articleUiStateList = articleUiStateList
        notifyDataSetChanged()
    }
}