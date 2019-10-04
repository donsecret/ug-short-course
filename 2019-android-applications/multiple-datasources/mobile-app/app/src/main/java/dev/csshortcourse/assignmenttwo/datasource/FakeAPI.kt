package dev.csshortcourse.assignmenttwo.datasource

import dev.csshortcourse.assignmenttwo.model.Chat
import java.util.*

object FakeAPI {

    fun loadFakeResponse(sender: String, recipient: String): MutableList<Chat> {
        return mutableListOf<Chat>().apply {
            for (i in 0 until 5) {
                val chat =
                    Chat(UUID.randomUUID().toString(), sender, recipient, "Some cool response")
                this@apply.add(chat)
            }
        }
    }
}