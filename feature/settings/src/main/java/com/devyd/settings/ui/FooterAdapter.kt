package com.devyd.settings.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devyd.settings.databinding.ItemAddButtonBinding

class FooterAdapter(
    private val onClick: () -> Unit
) : RecyclerView.Adapter<FooterAdapter.VH>() {

    inner class VH(binding: ItemAddButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnAddCategory.setOnClickListener {
                onClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            ItemAddButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        /* 특별히 바인딩할 데이터 없음 */
    }

    override fun getItemCount(): Int = 1
}