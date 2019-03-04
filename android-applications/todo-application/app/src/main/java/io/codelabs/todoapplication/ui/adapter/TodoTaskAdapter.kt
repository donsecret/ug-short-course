package io.codelabs.todoapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.codelabs.todoapplication.R
import io.codelabs.todoapplication.data.TodoItem

class TodoTaskAdapter constructor(private val listener: ClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * Empty list of [TodoItem]s
     */
    private val dataset: MutableList<TodoItem> = mutableListOf()

    override fun getItemViewType(position: Int): Int = when {
        dataset.isEmpty() -> TYPE_EMPTY
        dataset[position].completed -> TYPE_TODO_COMPLETED
        else -> TYPE_TODO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EMPTY -> EmptyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_empty, parent, false)
            )

            TYPE_TODO -> TodoViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_todo, parent, false)
            )

            else /*TYPE_TODO_COMPLETED*/ -> TodoViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_todo_completed, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = if (dataset.isEmpty()) 1 else dataset.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {

            TYPE_TODO -> {
                if (holder is TodoViewHolder) {
                    //todo: bind views
                }
            }

            TYPE_TODO_COMPLETED -> {
                if (holder is TodoViewHolder) {
                    //todo: bind views
                }
            }

        }
    }


    // todo: create a function to add new items to the adapter through the activity
    fun addItems(items: MutableList<TodoItem>) {
        dataset.clear()
        dataset.addAll(items)
        notifyDataSetChanged()
    }

    interface ClickListener {

        fun onClick(item: TodoItem)

    }

    companion object {
        private const val TYPE_EMPTY = 0
        private const val TYPE_TODO = 1
        private const val TYPE_TODO_COMPLETED = 2
    }
}