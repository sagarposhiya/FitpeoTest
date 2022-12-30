package com.example.fitpeoassignment.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fitpeoassignment.BR
import com.example.fitpeoassignment.R
import com.example.fitpeoassignment.api.models.Data
import com.example.fitpeoassignment.databinding.ItemListBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*
import javax.inject.Inject


class DataAdapter @Inject constructor ():
    RecyclerView.Adapter<DataAdapter.NetworkDataViewHolder>() {

    var networkDataList = arrayListOf<Data>()

    var onItemClick: ((recordList: Data) -> Unit)? = null

    var context: Context? = null
    var layoutInflater: LayoutInflater? = null

    fun addItemList(networkDataList2: ArrayList<Data>) {
        this.networkDataList = networkDataList2
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.NetworkDataViewHolder {

        if (layoutInflater == null) {
            context = parent.context
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: ItemListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_list, parent, false
        )

        return NetworkDataViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DataAdapter.NetworkDataViewHolder, position: Int) {
        holder.bind(networkDataList.get(position));
    }

    override fun getItemCount(): Int {
        return networkDataList.size
    }


    inner class NetworkDataViewHolder(view: ItemListBinding) : RecyclerView.ViewHolder(view.root) {
        var itemListBinding: ItemListBinding? = view

        fun bind(data: Data?) {
            itemListBinding?.setVariable(BR.model, data)
            itemListBinding?.executePendingBindings()
           // itemListBinding?.model = data

            itemView.setOnClickListener {
                onItemClick?.invoke(data!!)
            }
        }

    }
}