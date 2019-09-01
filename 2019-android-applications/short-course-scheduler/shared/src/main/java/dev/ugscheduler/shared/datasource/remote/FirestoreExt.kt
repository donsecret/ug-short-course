package dev.ugscheduler.shared.datasource.remote

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dev.ugscheduler.shared.util.Constants

fun FirebaseFirestore.studentDocument(id: String): DocumentReference =
    collection(Constants.STUDENTS).document(id)

fun FirebaseFirestore.anonymousDocument(id: String): DocumentReference =
    collection(Constants.ANONYMOUS).document(id)

fun FirebaseFirestore.facilitatorDocument(id: String): DocumentReference =
    collection(Constants.FACILITATORS).document(id)

fun FirebaseFirestore.courseDocument(id: String): DocumentReference =
    collection(Constants.COURSES).document(id)

fun FirebaseFirestore.feedbackDocument(id: String): DocumentReference =
    collection(Constants.FEEDBACK).document(id)