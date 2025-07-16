package com.devyd.settings.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devyd.common.CategoryStrings
import com.devyd.common.Constants.CATEGORY_SLIDER_MAX
import com.devyd.common.Constants.CATEGORY_SLIDER_MIN
import com.devyd.common.Constants.NEWS_API_TOP_HEADLINES_CATEGORY_LIST
import com.devyd.domain.models.CategoryWeight
import com.devyd.settings.common.setWidthByLongestItem
import com.devyd.settings.databinding.ItemCategorySliderBinding
import com.google.android.material.slider.Slider

class CategoryAdapter(
    private val categories: List<String>,
    private val onModify: (id: Int, category: String, weight: Int) -> Unit,
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
            slider.apply {
                valueFrom = CATEGORY_SLIDER_MIN
                valueTo = CATEGORY_SLIDER_MAX
            }

            var dropdownAdapter = ArrayAdapter(
                itemView.context,
                android.R.layout.simple_list_item_1,
                categories
            )

            actvCategory.setAdapter(dropdownAdapter)
            actvCategory.setWidthByLongestItem(categories)
            actvCategory.setText(getCategorySystemName(categoryWeight.category), false)


            actvCategory.setOnItemClickListener { parent, _, position, _ ->
                onModify(
                    categoryWeight.id,
                    CategoryStrings.validValues[position],
                    categoryWeight.weight
                )
            }

            slider.value = categoryWeight.weight.toFloat()
            tvWeight.text = categoryWeight.weight.toString()

            slider.clearOnSliderTouchListeners()
            slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) { /* no-op */
                }

                override fun onStopTrackingTouch(slider: Slider) {
                    val w = slider.value.toInt()
                    tvWeight.text = "$w"
                    onModify(categoryWeight.id, categoryWeight.category, w)
                }
            })

            btnDelete.setOnClickListener {
                onDelete(categoryWeight)
            }
        }

        private fun getCategorySystemName(categoryFromLocal: String): String {
            val categoryPosition = CategoryStrings.validValues.indexOf(categoryFromLocal)
            return NEWS_API_TOP_HEADLINES_CATEGORY_LIST.map { itemView.context.getString(it) }[categoryPosition]
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