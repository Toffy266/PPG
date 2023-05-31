package com.example.predictpopularityofgame.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.predictpopularityofgame.databinding.FormDialogItemBinding
import com.example.predictpopularityofgame.model.CheckBoxModel

class DialogAdapter(
    private var chooseList: ArrayList<CheckBoxModel>
) : RecyclerView.Adapter<DialogAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: FormDialogItemBinding) : RecyclerView.ViewHolder(binding.root)
    private var alreadyExists : Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FormDialogItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return chooseList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(viewHolder: ViewHolder, positon: Int) {
        val viewItem = chooseList[positon]

        with(viewHolder){
            binding.apply {
                formChooseInput.text = viewItem.name
                formChooseInput.isChecked = viewItem.isSelected
                formChooseInput.setOnClickListener {
                    viewItem.isSelected = formChooseInput.isChecked
                    notifyDataSetChanged()
                }
            }
        }

    }

}