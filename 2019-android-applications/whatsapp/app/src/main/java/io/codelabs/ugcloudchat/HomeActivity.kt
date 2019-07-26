package io.codelabs.ugcloudchat

import android.Manifest
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_chat.view.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class HomeActivity : BaseActivity(),
    OnChatItemClickListener {
    private var cursor: Cursor? = null

    override fun onChatClick(position: Int, id: Long) {
        // Get the Cursor
        cursor?.apply {
            // Move to the selected contact
            moveToPosition(position)
            // Get the _ID value
            val contactId = getLong(CONTACT_ID_INDEX)
            debugThis("Clicked: $contactId")
            // Get the selected LOOKUP KEY
            val mContactKey = getString(CONTACT_KEY_INDEX)
            debugThis("Clicked: $mContactKey")
            // Create the contact's content Uri
            debugThis(
                "Lookup returned: ${ContactsContract.Contacts.getLookupUri(
                    contactId,
                    mContactKey
                )}"
            )
            /*
             * You can use contactUri as the content URI for retrieving
             * the details for a contact.
             */
        }
    }

    val perms = arrayOf(Manifest.permission.READ_CONTACTS)
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

        // todo:
        /* ioScope.launch {
             val users = Tasks.await(userCollection.get())
             debugThis(users)

             uiScope.launch {
                 if (users != null && users.documents.isNotEmpty()) {
                     val usersList = users.toObjects(WhatsappUser::class.java)
                     adapter.addChat(usersList)
                 }
             }
         }*/

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

        } else {
            EasyPermissions.requestPermissions(
                this,
                String.format(getString(R.string.permission_rationale), "load your contacts"),
                RC_CONTACTS,
                perms[0]
            )
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

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

        /**
         * Defines an array that contains column names to move from
         * the [Cursor] to the [RecyclerView].
         */
        private val FROM_COLUMNS: Array<String> get() = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)

        private val PROJECTION: Array<out String>
            get() = arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.LOOKUP_KEY,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
            )

        private val SELECTION: String get() = "${ContactsContract.Contacts.DISPLAY_NAME} LIKE ?"
        private val PHONE_NUMBER_SELECTION: String get() = "${ContactsContract.Contacts.HAS_PHONE_NUMBER} = ?"

        // Defines the array to hold values that replace the ?
        private val selectionArgs = arrayOf<String>("0554022344")

        // The column index for the _ID column
        private const val CONTACT_ID_INDEX: Int = 0
        // The column index for the CONTACT_KEY column
        private const val CONTACT_KEY_INDEX: Int = 1

        /*
        protected interface ContactsColumns {
            String CONTACT_LAST_UPDATED_TIMESTAMP = "contact_last_updated_timestamp";
            String DISPLAY_NAME = "display_name";
            String HAS_PHONE_NUMBER = "has_phone_number";
            String IN_DEFAULT_DIRECTORY = "in_default_directory";
            String IN_VISIBLE_GROUP = "in_visible_group";
            String IS_USER_PROFILE = "is_user_profile";
            String LOOKUP_KEY = "lookup";
            String NAME_RAW_CONTACT_ID = "name_raw_contact_id";
            String PHOTO_FILE_ID = "photo_file_id";
            String PHOTO_ID = "photo_id";
            String PHOTO_THUMBNAIL_URI = "photo_thumb_uri";
            String PHOTO_URI = "photo_uri";
        }
         */
    }

}