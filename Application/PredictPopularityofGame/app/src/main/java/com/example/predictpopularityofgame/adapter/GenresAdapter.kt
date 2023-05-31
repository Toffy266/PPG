package com.example.predictpopularityofgame.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.predictpopularityofgame.FormDialogFragment
import com.example.predictpopularityofgame.FormFragment
import com.example.predictpopularityofgame.databinding.FormItemChildBinding


class GenresAdapter(
    private var genresList: ArrayList<String>,
    private val fragment: FormFragment,
) : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    private val dialog = FormDialogFragment()
    inner class ViewHolder(val binding: FormItemChildBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FormItemChildBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, positon: Int) {
        val viewItem =  genresList[positon]

        with(viewHolder){
            binding.apply {
                chooseButton.text = viewItem
                chooseButton.setOnClickListener {
                    fragment.let {
                        dialog.isCancelable = false
                        dialog.show(it.childFragmentManager, "Genres")
                    }
                }
            }
        }

    }

}