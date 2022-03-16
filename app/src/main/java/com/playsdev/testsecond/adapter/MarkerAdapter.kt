package com.playsdev.testsecond.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.playsdev.testsecond.databinding.ItemSheetBinding
import com.playsdev.testsecond.responce.MarkersValue

class MarkerAdapter(
    private val listMarker: ArrayList<MarkersValue>,
    private val listener: OnCrossClickListener
) : ListAdapter<MarkersValue, MarkerViewHolder>(DiffUtilItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerViewHolder {
        return MarkerViewHolder(
            ItemSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: MarkerViewHolder, position: Int) {
        holder.bind(listMarker[position])
    }
}

class DiffUtilItemCallback : DiffUtil.ItemCallback<MarkersValue>() {
    override fun areItemsTheSame(oldItem: MarkersValue, newItem: MarkersValue): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MarkersValue, newItem: MarkersValue): Boolean {
        return oldItem.name == newItem.name && oldItem.longitude == newItem.longitude &&
                oldItem.width == newItem.width && newItem.id == oldItem.id
    }
}

interface OnCrossClickListener {
    fun deleteItem(position: Int)
}

class MarkerViewHolder(
    private val binding: ItemSheetBinding,
    private val listener: OnCrossClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MarkersValue) {
        binding.tvMarkerTitle.text = item.name
        binding.tvLongitudeValue.text = item.longitude.toString()
        binding.tvWidthValue.text = item.width.toString()
        binding.ivDelete.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.deleteItem(position)
            }
        }
    }



}
