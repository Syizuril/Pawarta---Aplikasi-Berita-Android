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
import com.syizuril.pawarta.CategoryFragmentDirections
import com.syizuril.pawarta.SourceFragmentDirections
import com.syizuril.pawarta.TopHeadlineDirections
import com.syizuril.pawarta.databinding.HeadTopheadlineBinding
import com.syizuril.pawarta.databinding.SourceNewsLayoutBinding
import com.syizuril.pawarta.databinding.TopHeadlineLayoutBinding
import com.syizuril.pawarta.model.Category
import com.syizuril.pawarta.model.Source
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Syekh Syihabuddin Azmil Umri on 16/04/2022.
 */
class CategoryAdapter(): ListAdapter<Category, CategoryAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(
        val binding: SourceNewsLayoutBinding, val context: Context
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category){
            category.icon?.let { binding.logoSource.setImageResource(it) }
            binding.descSource.text = category.name
            binding.cardView.setOnClickListener {
                it.findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToNewsListFragment(category = category))
            }
        }

        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SourceNewsLayoutBinding.inflate(layoutInflater, parent, false)
                val context: Context = parent.context
                return ViewHolder(binding, context)
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CategoryDiffCallback: DiffUtil.ItemCallback<Category>(){
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}