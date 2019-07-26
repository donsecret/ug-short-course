package io.codelabs.ugcloudchat.view

import android.os.Bundle
import android.view.View
import com.google.android.gms.tasks.Tasks
import io.codelabs.ugcloudchat.model.Chat
import io.codelabs.ugcloudchat.R
import io.codelabs.ugcloudchat.util.debugThis
import io.codelabs.ugcloudchat.util.toast
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.launch

class ChatActivity : BaseActivity() {
    // Store recipient's details
    private var phoneNumber: String? = null
    private var uid: String? = null


    // Store current user's uid
    private val currentUser: String? by lazy { auth.currentUser?.uid }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        if (intent.hasExtra(UID)) {
            debugThis(intent.getStringExtra(UID))
            debugThis(intent.getStringExtra(PHONE_NUMBER))

            // todo: bind user interface here
            uid = intent.getStringExtra(UID)
            phoneNumber = intent.getStringExtra(PHONE_NUMBER)

        }
    }

    /**
     * Send message
     */
    fun sendMessage(view: View?) {
        if (uid.isNullOrEmpty() && phoneNumber.isNullOrEmpty()) {
            toast("Cannot send message to this user. He/ She has no valid details")
            return
        }


        val senderRef = db.collection("users/$currentUser/chats/$uid/messages").document()
        val recipientRef = db.collection("users/$uid/chats/$currentUser/messages").document()
        val messageId = senderRef.id

        // Create message
        val chat = Chat(
            messageId,
            message_input.text.toString(),
            currentUser!!,
            uid!!
        )
        ioScope.launch {
            Tasks.await(senderRef.set(chat))
            Tasks.await(recipientRef.set(chat))
        }
    }


    private fun readAllMessages() {
        if (uid.isNullOrEmpty() && phoneNumber.isNullOrEmpty()) {
            toast("Cannot send message to this user. He/ She has no valid details")
            return
        }

        db.collection("users/$currentUser/chats/$uid/messages")
            .addSnapshotListener(this) { snapshot, exception ->
                if (exception != null) {
                    toast(exception.localizedMessage)
                    return@addSnapshotListener
                }

                val chats = snapshot?.toObjects(Chat::class.java)
                debugThis(chats)
                // todo: add to adapter
            }
    }

    companion object {
        const val UID = "uid"
        const val PHONE_NUMBER = "phone_number"
    }
}
