package io.codelabs.ugcloudchat.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.codelabs.ugcloudchat.R
import io.codelabs.ugcloudchat.model.WhatsappUser
import io.codelabs.ugcloudchat.util.layoutInflater
import kotlinx.android.synthetic.main.item_chat.view.*

/**
 * Adapter implementation
 */
class ChatAdapter(private val listener: OnChatItemClickListener) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    private val dataset = mutableListOf<WhatsappUser>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            parent.context.layoutInflater.inflate(
                R.layout.item_chat,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val user = dataset[position]
        holder.v.chat_phone_number.text = user.phone
        holder.v.setOnClickListener {
            // Get user's information form the cursor
            listener.onChatClick(position, user.id)

        }
    }

    fun addChats(users: MutableList<WhatsappUser>) {
        dataset.clear()
        dataset.addAll(users)
        notifyDataSetChanged()
    }


    inner class ChatViewHolder(val v: View) : RecyclerView.ViewHolder(v)

}