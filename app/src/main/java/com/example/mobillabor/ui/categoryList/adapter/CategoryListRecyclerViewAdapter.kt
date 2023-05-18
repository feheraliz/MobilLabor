package com.example.mobillabor.ui.categoryList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobillabor.databinding.CategoryListItemBinding
import com.example.mobillabor.model.Category

class CategoryListRecyclerViewAdapter(
    private val values: List<Category>,
    private val onClick: CategoryListClickListener
) : RecyclerView.Adapter<CategoryListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            CategoryListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemView.setOnClickListener {
            onClick.onClickCategoryList(item.strCategory)
        }
        holder.titleTv.text = item.strCategory
        Glide.with(holder.itemView.context)
            .load(item.strCategoryThumb)
            .into(holder.categoryImage)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleTv: TextView = binding.categoryTitle
        val categoryImage: ImageView = binding.categoryImage
    }

    interface CategoryListClickListener {
        fun onClickCategoryList(category: String)
    }
}