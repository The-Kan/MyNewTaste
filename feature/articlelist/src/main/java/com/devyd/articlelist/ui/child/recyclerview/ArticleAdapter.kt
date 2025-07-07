package com.devyd.articlelist.ui.child.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devyd.articlelist.databinding.ItemArticleBinding
import com.devyd.domain.models.Article
import com.devyd.domain.models.News

class ArticleAdapter(
    private var news: News,
    private val onClick: (Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.title.text = article.title
            // 예시: Glide 등을 이용해 실제 URL 로드 가능
            //binding.thumbnail.setImageResource(article.urlToImage)
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
        holder.bind(news.articles[position])
    }

    override fun getItemCount(): Int = news.totalResults

    @SuppressLint("NotifyDataSetChanged")
    fun updateArticles(newItems: News) {
        this.news = newItems
        notifyDataSetChanged()
    }
}