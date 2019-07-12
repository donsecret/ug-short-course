package io.codelabs.whatsappclone

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Tasks
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_chat.view.*
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = ChatAdapter()
        chat_list.adapter = adapter
        chat_list.layoutManager = LinearLayoutManager(this)

        ioScope.launch {
            val users = Tasks.await(userCollection.get())
            debugThis(users)

            uiScope.launch {
                if (users != null && users.documents.isNotEmpty()) {
                    val usersList = users.toObjects(WhatsappUser::class.java)
                    adapter.addChat(usersList)
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                // Sign out user
                auth.signOut()

                /// Navigate back to the login screen
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finishAfterTransition()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // todo: add new chat
    fun addNewChat(view: View) {}


    /**
     * Adapter implementation
     */
    inner class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
        private val dataset = mutableListOf<WhatsappUser>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
            return ChatViewHolder(layoutInflater.inflate(R.layout.item_chat, parent, false))
        }

        override fun getItemCount(): Int = dataset.size

        override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
            val user = dataset[position]
            holder.v.chat_phone_number.text = user.phone


            holder.v.setOnClickListener {
                startActivity(Intent(applicationContext, ChatActivity::class.java).apply {
                    putExtras(
                        bundleOf(
                            Pair(ChatActivity.UID, user.uid),
                            Pair(ChatActivity.PHONE_NUMBER, user.phone)
                        )
                    )
                })
            }
        }

        fun addChat(users: MutableList<WhatsappUser>) {
            dataset.clear()
            dataset.addAll(users)
            notifyDataSetChanged()
        }


        inner class ChatViewHolder(val v: View) : RecyclerView.ViewHolder(v)
    }

}