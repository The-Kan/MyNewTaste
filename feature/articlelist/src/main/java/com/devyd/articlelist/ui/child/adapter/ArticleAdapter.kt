package com.devyd.articlelist.ui.child.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devyd.articlelist.databinding.ItemArticleBinding
import com.devyd.domain.models.Article

class ArticleAdapter(
    private var articleList: List<Article>,
    private val onClick: (Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.title.text = article.title
            // 예시: Glide 등을 이용해 실제 URL 로드 가능
            Glide.with(binding.thumbnail.context)
                .load(article.urlToImage)
                .centerCrop()
                .into(binding.thumbnail)

            binding.root.setOnClickListener { onClick(article) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount(): Int = articleList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateArticles(articleList: List<Article>) {
        this.articleList = articleList
        notifyDataSetChanged()
    }
}