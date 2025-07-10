package com.devyd.settings.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.settings.databinding.ItemCategorySliderBinding
import com.devyd.settings.model.CategoryWeight
import com.google.android.material.slider.Slider

class CategoryAdapter(
    private val categories: List<String>,
    private val onModify: (CategoryWeight) -> Unit,
    private val onDelete: (CategoryWeight) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.VH>() {

    private val items = mutableListOf<CategoryWeight>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<CategoryWeight>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    inner class VH(binding: ItemCategorySliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val actvCategory = binding.actvCategory
        private val slider = binding.sliderWeight
        private val tvWeight = binding.tvWeight
        private val btnDelete = binding.btnDelete

        fun bind(data: CategoryWeight) {

            var dropdownAdapter = ArrayAdapter(
                itemView.context,
                android.R.layout.simple_list_item_1,
                categories
            )

            actvCategory.setAdapter(dropdownAdapter)

            actvCategory.setText(data.category, false)

            // ④ 사용자 선택 시에만 호출
            actvCategory.setOnItemClickListener { parent, _, position, _ ->
                val selected = parent.getItemAtPosition(position) as String
                data.category = selected
                onModify(data)
            }

            // Slider 초기화
            slider.value = data.weight.toFloat()
            tvWeight.text = data.weight.toString()

            slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) { /* no-op */
                }

                override fun onStopTrackingTouch(slider: Slider) {
                    val w = slider.value.toInt()
                    data.weight = w
                    tvWeight.text = "$w"
                    onModify(data)
                }
            })

            btnDelete.setOnClickListener {
                onDelete(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            ItemCategorySliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }


    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size
}