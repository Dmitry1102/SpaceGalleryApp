package com.playsdev.testsecond.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playsdev.testsecond.databinding.ItemMainBinding
import com.playsdev.testsecond.responce.Photo
import com.squareup.picasso.Picasso

class MainAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MainViewHolder>() {

    private val list:MutableList<Photo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(updated: MutableList<Photo>) {
        list.clear()
        list.addAll(updated)
        notifyDataSetChanged()
    }
}

interface OnItemClickListener{
    fun openFragment(image: String)
}

class MainViewHolder(
    private val binding: ItemMainBinding,
    private val listener: OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Photo) {
        Picasso.get().load(item.img_src).into(binding.ivSpace)
        binding.tvLabel.text = item.camera.name
        binding.tvInfo.text = item.camera.full_name
        binding.tvShowImage.setOnClickListener {
            listener.openFragment(item.img_src)
        }
    }
}

