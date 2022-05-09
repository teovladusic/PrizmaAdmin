package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prizma_distribucija.prizmaadmin.databinding.FolderItemBinding
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Folder

class FoldersAdapter(
    private val folders: List<Folder>,
    private val folderClickListener: FolderClickListener,
    private val icon: Int
) : RecyclerView.Adapter<FoldersAdapter.FoldersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoldersViewHolder {
        val binding =
            FolderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoldersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoldersViewHolder, position: Int) {
        val folder = folders[position]
        holder.bind(folder)
    }

    override fun getItemCount(): Int = folders.size

    inner class FoldersViewHolder(
        private val binding: FolderItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                folderClickListener.onFolderClick(adapterPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(folder: Folder) {
            binding.tvFolderTitle.text = folder.name
            binding.imageViewFolder.setImageResource(icon)
            binding.newRouteIndicator.visibility = if (folder.seen) View.GONE else View.VISIBLE
        }
    }

    interface FolderClickListener {
        fun onFolderClick(position: Int)
    }
}