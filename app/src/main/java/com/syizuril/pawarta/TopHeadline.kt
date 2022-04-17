package com.syizuril.pawarta

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.syizuril.pawarta.adapter.TopHeadlineAdapter
import com.syizuril.pawarta.databinding.TopHeadlineFragmentBinding
import com.syizuril.pawarta.model.ApiStatus
import com.syizuril.pawarta.model.Articles

class TopHeadline : Fragment() {

    companion object {
        fun newInstance() = TopHeadline()
    }

    private lateinit var viewModel: TopHeadlineViewModel
    private var _binding: TopHeadlineFragmentBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TopHeadlineFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[TopHeadlineViewModel::class.java]
        recyclerView = binding.topHeadlineRv

        viewModel.getTopHeadline()

        val adapter = TopHeadlineAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

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
                            viewModel.getTopHeadline()
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

        viewModel.topHeadlineResult.observe(viewLifecycleOwner){
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