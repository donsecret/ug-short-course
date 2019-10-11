/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.ui.about

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dev.ugscheduler.databinding.ItemLibBinding
import dev.ugscheduler.shared.util.debugger
import java.io.IOException
import java.io.InputStreamReader

data class Library(
    val id: String,
    val name: String,
    val desc: String,
    val url: String,
    val icon: String? = null,
    val circleCrop: Boolean = false
)

object LibraryDeserializer {
    fun deserialize(context: Context): MutableList<Library> {
        val libs = mutableListOf<Library>()
        val gson = GsonBuilder().setPrettyPrinting().create()
        val type = object : TypeToken<List<Library>>() {}.type
        try {
            val items = gson.fromJson<List<Library>>(
                InputStreamReader(context.assets.open("libs.json")), type
            )
            libs.addAll(items)
        } catch (ex: IOException) {
            debugger(ex.localizedMessage)
        }
        return libs
    }
}

class AboutLibsAdapter : ListAdapter<Library, AboutLibsAdapter.LibraryViewHolder>(DIFF) {
    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        return LibraryViewHolder(
            ItemLibBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    class LibraryViewHolder(val binding: ItemLibBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lib: Library) {
            //binding.lib = lib
        }
    }

    companion object {
        private val DIFF: DiffUtil.ItemCallback<Library> =
            object : DiffUtil.ItemCallback<Library>() {
                override fun areItemsTheSame(oldItem: Library, newItem: Library): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Library, newItem: Library): Boolean =
                    oldItem == newItem
            }
    }
}