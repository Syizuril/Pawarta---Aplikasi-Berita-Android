package com.syizuril.pawarta

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.syizuril.pawarta.databinding.DetailArticleFragmentBinding
import java.text.SimpleDateFormat
import java.util.*


class DetailArticle : Fragment() {

    companion object {
        fun newInstance() = DetailArticle()
    }

    private val article: DetailArticleArgs by navArgs()
    private lateinit var viewModel: DetailArticleViewModel
    private var _binding: DetailArticleFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailArticleFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[DetailArticleViewModel::class.java]
        binding.content.text = article.article?.content
        binding.collapseToolbar.title = article.article?.title
        binding.sourceIcon.load("https://www.google.com/s2/favicons?sz=64&domain_url=${article.article?.url}"){
            crossfade(true)
        }
        binding.imageIv.load(article.article?.urlToImage){
            crossfade(true)
        }
        binding.author.text = article.article?.author
        binding.source.text = article.article?.source?.name

        val dateTime = article.article?.publishedAt?.let {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(
                it
            )
        }
        val dateFormatted = dateTime?.let {
            SimpleDateFormat("dd MMM, yyyy", Locale("id","ID")).format(
                it
            )
        }
        binding.date.text = dateFormatted
        binding.btnRead.setOnClickListener {
            val openBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.article?.url))
            activity?.startActivity(openBrowserIntent)
        }

        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(binding.appToolbar)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding.appBarLayout.addOnOffsetChangedListener( AppBarLayout.OnOffsetChangedListener{
            appBarLayout, verticalOffset ->
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    val colorComponent =
                        kotlin.math.abs((255 * (verticalOffset.toFloat() / -appBarLayout.totalScrollRange)).toInt())
                    binding.appToolbar.navigationIcon?.colorFilter =
                        PorterDuffColorFilter(Color.rgb(colorComponent, colorComponent, colorComponent), PorterDuff.Mode.SRC_ATOP)
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    val colorComponent =
                        kotlin.math.abs(255 - (255 * (verticalOffset.toFloat() / -appBarLayout.totalScrollRange)).toInt())
                    binding.appToolbar.navigationIcon?.colorFilter =
                        PorterDuffColorFilter(Color.rgb(colorComponent, colorComponent, colorComponent), PorterDuff.Mode.SRC_ATOP)
                }
            }


            when {
                verticalOffset == 0 -> {
                    binding.sourceIcon.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                        setMargins(0,0,0,0)
                    }
                }
                kotlin.math.abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                    binding.sourceIcon.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                        setMargins(0,40,0,0)
                    }
                }
                else -> {
                    binding.sourceIcon.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                        setMargins(0,0,0,0)
                    }
                }
            }
        })
        
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}