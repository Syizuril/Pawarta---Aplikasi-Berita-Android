package com.syizuril.pawarta

import android.content.res.Resources
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syizuril.pawarta.adapter.SourceAdapter
import com.syizuril.pawarta.databinding.SourceFragmentBinding
import com.syizuril.pawarta.model.Source
import com.syizuril.pawarta.utils.GridAutofitLayoutManager
import com.syizuril.pawarta.utils.GridSpacesItemDecoration

class SourceFragment : Fragment() {

    companion object {
        fun newInstance() = SourceFragment()
    }

    private lateinit var viewModel: SourceViewModel
    private var _binding: SourceFragmentBinding? = null
    val binding get() = _binding !!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SourceFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SourceViewModel::class.java]
        recyclerView = binding.sourceRv
        recyclerView.layoutManager = GridAutofitLayoutManager(this.requireContext(), dpToPx(116))
        recyclerView.addItemDecoration(GridSpacesItemDecoration(dpToPx(4), true))

        val adapter = SourceAdapter()
        recyclerView.adapter = adapter

        viewModel.source.observe(viewLifecycleOwner){
            val listResult = ArrayList<Source>()
            if(it!=null){
                it.sources?.let {list -> listResult.addAll(list)}
            }
            adapter.submitList(listResult)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}