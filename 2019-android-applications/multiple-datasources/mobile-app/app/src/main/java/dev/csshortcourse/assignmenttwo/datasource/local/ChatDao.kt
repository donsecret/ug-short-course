package dev.csshortcourse.assignmenttwo.datasource.local

import dev.csshortcourse.assignmenttwo.model.Chat

/**
 * [Chat] Data Access Object
 */
interface ChatDao : BaseDao<Chat> {
    // @Query("select * from chats where sender = :sender order by timestamp desc")
    fun getMyChats(sender: String): MutableList<Chat>
}