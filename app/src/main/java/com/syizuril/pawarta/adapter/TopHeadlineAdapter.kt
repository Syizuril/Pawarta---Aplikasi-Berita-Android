package com.syizuril.pawarta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.github.marlonlom.utilities.timeago.TimeAgoMessages
import com.syizuril.pawarta.TopHeadlineDirections
import com.syizuril.pawarta.databinding.HeadTopheadlineBinding
import com.syizuril.pawarta.databinding.TopHeadlineLayoutBinding
import com.syizuril.pawarta.model.Articles
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Syekh Syihabuddin Azmil Umri on 16/04/2022.
 */
class TopHeadlineAdapter(): ListAdapter<Articles, RecyclerView.ViewHolder>(TopHeadlineDiffCallback()) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if(viewType == 1){
            ViewHolderHead.from(parent)
        }else{
            ViewHolder.from(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) 1
        else 2
    }

    class ViewHolder private constructor(
        val binding: TopHeadlineLayoutBinding, val context: Context
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(articles: Articles){
            binding.title.text = articles.title
            binding.source.text = articles.source?.name
            binding.description.text = articles.description

            val localeBylanguageTag: Locale = Locale.forLanguageTag("id")
            val messages: TimeAgoMessages = TimeAgoMessages.Builder().withLocale(localeBylanguageTag).build()
            val dateTime = articles.publishedAt?.let {
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(
                    it
                )?.time
            }
            val text = dateTime?.let { TimeAgo.using(it, messages) }
            binding.date.text = text

            binding.imageNews.load(articles.urlToImage){
                crossfade(true)
            }

            binding.iconSource.load("https://www.google.com/s2/favicons?sz=64&domain_url=${articles.url}"){
                crossfade(true)
            }

            binding.card.setOnClickListener {
                it.findNavController().navigate(TopHeadlineDirections.actionTopHeadlineToDetailArticle(articles))
            }
        }

        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TopHeadlineLayoutBinding.inflate(layoutInflater, parent, false)
                val context: Context = parent.context
                return ViewHolder(binding, context)
            }
        }

    }

    class ViewHolderHead private constructor(
        val binding: HeadTopheadlineBinding, val context: Context
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(articles: Articles){
            binding.title.text = articles.title
            binding.source.text = articles.source?.name

            val localeBylanguageTag: Locale = Locale.forLanguageTag("id")
            val messages: TimeAgoMessages = TimeAgoMessages.Builder().withLocale(localeBylanguageTag).build()
            val dateTime = articles.publishedAt?.let {
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(
                    it
                )?.time
            }
            val text = dateTime?.let { TimeAgo.using(it, messages) }
            binding.date.text = text
            binding.imageNews.load(articles.urlToImage){
                crossfade(true)
            }

            binding.iconSource.load("https://www.google.com/s2/favicons?sz=64&domain_url=${articles.url}"){
                crossfade(true)
            }

            binding.card.setOnClickListener {
                it.findNavController().navigate(TopHeadlineDirections.actionTopHeadlineToDetailArticle(articles))
            }
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolderHead {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HeadTopheadlineBinding.inflate(layoutInflater, parent, false)
                val context: Context = parent.context
                return ViewHolderHead(binding, context)
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(position: Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                holder.bind(getItem(position))
            }
            is ViewHolderHead -> {
                holder.bind(getItem(position))
            }
        }
    }
}

class TopHeadlineDiffCallback: DiffUtil.ItemCallback<Articles>(){
    override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem == newItem
    }

}