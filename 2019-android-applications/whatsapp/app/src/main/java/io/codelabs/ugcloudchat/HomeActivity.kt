package io.codelabs.ugcloudchat

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class HomeActivity : BaseActivity(), OnChatItemClickListener {

    val perms = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = ChatAdapter(this)
        chat_list.adapter = adapter
        chat_list.layoutManager = LinearLayoutManager(this)

        // Kick off initial load
        getAllContacts()
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

    @AfterPermissionGranted(RC_CONTACTS)
    private fun getAllContacts() {
        if (EasyPermissions.hasPermissions(this, perms[0])) {
            debugThis("Has contacts read permission")
            // todo: get all contacts on the application platform from the room database

        } else {
            EasyPermissions.requestPermissions(
                this,
                String.format(getString(R.string.permission_rationale), "load your contacts"),
                RC_CONTACTS,
                perms[0]
            )
        }
    }

    override fun onChatClick(position: Int, id: Long) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    companion object {
        private const val RC_CONTACTS = 8
    }

}