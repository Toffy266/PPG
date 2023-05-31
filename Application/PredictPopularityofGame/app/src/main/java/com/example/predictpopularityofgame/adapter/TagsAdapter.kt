package com.example.predictpopularityofgame.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.predictpopularityofgame.FormDialogFragment
import com.example.predictpopularityofgame.FormFragment
import com.example.predictpopularityofgame.databinding.FormItemChildBinding

class TagsAdapter(
    private var tagssList: ArrayList<String>,
    private val fragment: FormFragment
) : RecyclerView.Adapter<TagsAdapter.ViewHolder>() {

    private val dialog = FormDialogFragment()
    inner class ViewHolder(val binding: FormItemChildBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FormItemChildBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tagssList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, positon: Int) {
        val viewItem =  tagssList[positon]

        with(viewHolder){
            binding.apply{
                if (tagssList[0] == "+ Tags"){
                    chooseButton.text = viewItem
                }
                else {
                    chooseButton.text = "# $viewItem"
                }

                chooseButton.setOnClickListener {
                    fragment.let {
                        dialog.isCancelable = false
                        dialog.show(it.childFragmentManager, "Tags")
                    }
                }

            }
        }
    }

}