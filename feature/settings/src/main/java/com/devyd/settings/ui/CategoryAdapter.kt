package com.devyd.settings.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
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
    fun submitList(categoryWeightList: List<CategoryWeight>) {
        items.clear()
        items.addAll(categoryWeightList)
        notifyDataSetChanged()
    }

    inner class VH(binding: ItemCategorySliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val actvCategory = binding.actvCategory
        private val slider = binding.sliderWeight
        private val tvWeight = binding.tvWeight
        private val btnDelete = binding.btnDelete

        fun bind(categoryWeight: CategoryWeight) {

            var dropdownAdapter = ArrayAdapter(
                itemView.context,
                android.R.layout.simple_list_item_1,
                categories
            )

            actvCategory.setAdapter(dropdownAdapter)

            actvCategory.setText(categoryWeight.category, false)

            // ④ 사용자 선택 시에만 호출
            actvCategory.setOnItemClickListener { parent, _, position, _ ->
                val selected = parent.getItemAtPosition(position) as String
                categoryWeight.category = selected
                onModify(categoryWeight)
            }

            // Slider 초기화
            slider.value = categoryWeight.weight.toFloat()
            tvWeight.text = categoryWeight.weight.toString()

            slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) { /* no-op */
                }

                override fun onStopTrackingTouch(slider: Slider) {
                    val w = slider.value.toInt()
                    categoryWeight.weight = w
                    tvWeight.text = "$w"
                    onModify(categoryWeight)
                }
            })

            btnDelete.setOnClickListener {
                onDelete(categoryWeight)
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