package io.codelabs.churchinc.core.datasource.remote

import io.codelabs.churchinc.core.RootActivity
import io.codelabs.churchinc.model.User
import kotlinx.coroutines.launch


fun RootActivity.getLiveUser(callback: DataCallback<User>) {
    firestore.collection("users").document(auth.uid!!)
        .addSnapshotListener(this) { snapshot, exception ->
            if (exception != null) {
                callback.onError(exception.localizedMessage)
                return@addSnapshotListener
            }

            // Get user's data from the database
            val user = snapshot?.toObject(User::class.java)
            if (user == null) {
                callback.onError("User could not be found")
            } else {
                ioScope.launch {
                    dao.updateUser(user)

                    uiScope.launch {
                        callback.onComplete(user)
                    }
                }
            }

        }
}

interface DataCallback<T> {

    fun onError(e: String?)

    fun onComplete(result: T?)

}