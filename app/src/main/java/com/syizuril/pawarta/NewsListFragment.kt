package com.syizuril.pawarta

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.syizuril.pawarta.adapter.ListNewsAdapter
import com.syizuril.pawarta.adapter.TopHeadlineAdapter
import com.syizuril.pawarta.databinding.NewsListFragmentBinding
import com.syizuril.pawarta.model.ApiStatus
import com.syizuril.pawarta.model.Articles

class NewsListFragment : Fragment() {

    companion object {
        fun newInstance() = NewsListFragment()
    }

    private val source: NewsListFragmentArgs by navArgs()
    private val category: NewsListFragmentArgs by navArgs()
    private lateinit var viewModel: NewsListViewModel
    private var _binding: NewsListFragmentBinding? = null
    private lateinit var recyclerView: RecyclerView
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewsListFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[NewsListViewModel::class.java]
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(binding.mainAppToolbar)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        if(!source.source?.name.isNullOrEmpty()){
            source.source?.name?.let { viewModel.getListNews(it) }

            if(activity is AppCompatActivity){
                (activity as AppCompatActivity).title = source.source?.name
            }
        }else{
            category.category?.name.let {
                if (it != null) {
                    viewModel.getListNewsByCategory(it)
                }
            }
            if(activity is AppCompatActivity){
                (activity as AppCompatActivity).title = category.category?.name
            }
        }

        viewModel.status.observe(viewLifecycleOwner){
            when {
                it.equals(ApiStatus.LOADING) -> {
                    binding.skCheck.visibility = View.VISIBLE
                }
                it.equals(ApiStatus.ERROR) -> {
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("Tidak Dapat Terhubung !")
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setMessage("Oopss... sepertinya kamu tidak terhubung ke internet nih, aktifkan koneksimu lalu coba lagi atau pergi kepengaturan")
                        .setNeutralButton("Coba Lagi") { dialog, _ ->
                            if(!source.source?.name.isNullOrEmpty()){
                                source.source?.name?.let { cat ->
                                    viewModel.getListNews(cat)
                                }

                                if(activity is AppCompatActivity){
                                    (activity as AppCompatActivity).title = source.source?.name
                                }
                            }else{
                                category.category?.name.let { cat ->
                                    if (cat != null) {
                                        viewModel.getListNewsByCategory(cat)
                                    }
                                }
                                if(activity is AppCompatActivity){
                                    (activity as AppCompatActivity).title = category.category?.name
                                }
                            }
                            dialog.dismiss()
                        }
                        .setPositiveButton("Pengaturan") { dialog, _ ->
                            val intent = Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)
                            activity?.startActivity(intent)
                            dialog.dismiss()
                        }
                        .setCancelable(false)
                        .show()
                }
                else -> {
                    binding.skCheck.visibility = View.GONE
                }
            }
        }

        recyclerView = binding.listNewsRv
        val adapter = ListNewsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        viewModel.listResult.observe(viewLifecycleOwner){
            val listResult = ArrayList<Articles>()
            if(it!=null){
                it.articles?.let { list -> listResult.addAll(list) }
            }
            adapter.submitList(listResult)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}