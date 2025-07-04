package com.devyd.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.devyd.settings.R
import com.devyd.settings.databinding.FragmentCategorySettingsBinding
import com.google.android.material.slider.Slider

class CategorySettingsFragment : Fragment(){
    private var _binding: FragmentCategorySettingsBinding? = null
    private val binding get() = _binding!!

    private val categories = listOf("과학", "사회", "경제", "주식", "IT", "스포츠", "문화")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategorySettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddCategory.setOnClickListener {
            addCategoryItem()
        }
    }

    private fun addCategoryItem() {
        val itemView = layoutInflater.inflate(
            R.layout.item_category_slider,
            binding.containerCategories,
            false
        )

        val spinner = itemView.findViewById<Spinner>(R.id.spinner_category)
        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        val tvPercent = itemView.findViewById<TextView>(R.id.tv_percentage)
        val slider = itemView.findViewById<Slider>(R.id.slider_percentage)
        slider.addOnChangeListener { _, value, _ ->
            tvPercent.text = "${value.toInt()}"
        }

        val deleteBtn = itemView.findViewById<ImageButton>(R.id.btn_delete)
        deleteBtn.setOnClickListener {
            binding.containerCategories.removeView(itemView)
        }

        val insertIndex = binding.containerCategories.childCount - 1
        binding.containerCategories.addView(itemView, insertIndex)

        binding.scrollViewCategories.post {
            binding.scrollViewCategories.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}