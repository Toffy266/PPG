package com.example.predictpopularityofgame.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.predictpopularityofgame.FormDialogFragment
import com.example.predictpopularityofgame.databinding.FormItemChildBinding

class CategoriesAdapter(
    private var categoriesList: ArrayList<String>,
    private val fragment: Fragment,
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private val dialog = FormDialogFragment()
    inner class ViewHolder(val binding: FormItemChildBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FormItemChildBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, positon: Int) {
        val viewItem =  categoriesList[positon]

        with(viewHolder){
            binding.apply {
                chooseButton.text = viewItem
                chooseButton.setOnClickListener {
                    fragment.let {
                        dialog.isCancelable = false
                        dialog.show(it.childFragmentManager, "Categories")
                    }
                }
            }
        }

    }

}