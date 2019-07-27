package io.codelabs.ugcloudchat.view

import io.codelabs.ugcloudchat.model.WhatsappUser

interface OnChatItemClickListener {

    fun onChatClick(user: WhatsappUser, id: Long)
}