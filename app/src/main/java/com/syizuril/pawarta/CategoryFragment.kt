package com.syizuril.pawarta

import android.content.res.Resources
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syizuril.pawarta.adapter.CategoryAdapter
import com.syizuril.pawarta.adapter.SourceAdapter
import com.syizuril.pawarta.databinding.CategoryFragmentBinding
import com.syizuril.pawarta.model.Category
import com.syizuril.pawarta.model.Source
import com.syizuril.pawarta.utils.GridAutofitLayoutManager
import com.syizuril.pawarta.utils.GridSpacesItemDecoration

class CategoryFragment : Fragment() {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private lateinit var viewModel: CategoryViewModel
    private var _binding: CategoryFragmentBinding? = null
    val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CategoryFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        recyclerView = binding.categoryRv

        recyclerView.layoutManager = GridAutofitLayoutManager(this.requireContext(),  dpToPx(116))
        recyclerView.addItemDecoration(GridSpacesItemDecoration(dpToPx(4), true))

        val adapter = CategoryAdapter()
        recyclerView.adapter = adapter

        viewModel.category.observe(viewLifecycleOwner){
            val listResult = ArrayList<Category>()
            if(it!=null){
                it.categories?.let {list -> listResult.addAll(list)}
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