package uz.futuresoft.searchfind.repository.user

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.utils.Constants
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage,
    private val firestore: FirebaseFirestore,
) : UserRepository {

    override suspend fun addUser(picture: Uri?, user: UserModel): Flow<Result<Unit>> =
        callbackFlow {
            var pictureUrl = ""
            var uploadTask: UploadTask.TaskSnapshot? = null
            val storageReference = firebaseStorage.getReference("images/users/${user.name}")
            val userDocument = firestore.collection(Constants.Firebase.COLLECTION_USERS)
                .document(auth.currentUser?.uid ?: "")

            if (picture != null) {
                storageReference.child(System.currentTimeMillis().toString())
                    .putFile(picture)
                    .addOnSuccessListener { uploadTask = it }
                    .addOnFailureListener { close(cause = it.cause) }
                    .await()

                uploadTask!!.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { pictureUrl = it.toString() }
                    .addOnFailureListener { close(cause = it.cause) }
                    .await()

                val userWithPicture = UserModel(
                    name = user.name,
                    surname = user.surname,
                    phoneNumber = user.phoneNumber,
                    picture = pictureUrl
                )

                userDocument.set(userWithPicture)
                    .addOnSuccessListener { trySend(element = Result.success(value = Unit)) }
                    .addOnFailureListener { exception -> close(cause = exception.cause) }
                    .await()
            } else {
                userDocument.set(user)
                    .addOnSuccessListener { trySend(element = Result.success(value = Unit)) }
                    .addOnFailureListener { exception -> close(cause = exception.cause) }
                    .await()
            }
            awaitClose { cancel() }
        }

    override suspend fun getUser(id: String): Flow<Result<UserModel>> = callbackFlow {
        firestore.collection(Constants.Firebase.COLLECTION_USERS)
            .document(id)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(UserModel::class.java)
                trySend(element = Result.success(value = user ?: UserModel()))
            }
            .addOnFailureListener { exception -> close(cause = exception.cause) }
        awaitClose { cancel() }
    }

    override suspend fun editUser(picture: Uri?, user: UserModel): Flow<Result<Unit>> =
        callbackFlow {
            var pictureUrl = ""
            var uploadTask: UploadTask.TaskSnapshot? = null
            val storageReference = firebaseStorage.getReference("images/users/${user.name}")
            val userDocument =
                firestore.collection(Constants.Firebase.COLLECTION_USERS).document(user.id)

            if (picture != null) {
                storageReference.child(System.currentTimeMillis().toString())
                    .putFile(picture)
                    .addOnSuccessListener { uploadTask = it }
                    .addOnFailureListener { close(cause = it.cause) }
                    .await()

                uploadTask!!.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { pictureUrl = it.toString() }
                    .addOnFailureListener { close(cause = it.cause) }
                    .await()

                val editedUser = mapOf(
                    "name" to user.name,
                    "surname" to user.surname,
                    "phoneNumber" to user.phoneNumber,
                    "picture" to pictureUrl
                )

                userDocument.update(editedUser)
                    .addOnSuccessListener { trySend(element = Result.success(value = Unit)) }
                    .addOnFailureListener { close(cause = it.cause) }
                    .await()
            } else {
                val editedUser = mapOf(
                    "name" to user.name,
                    "surname" to user.surname,
                    "phoneNumber" to user.phoneNumber,
                    "picture" to user.picture
                )

                userDocument.update(editedUser)
                    .addOnSuccessListener { trySend(element = Result.success(value = Unit)) }
                    .addOnFailureListener { exception -> close(cause = exception.cause) }
                    .await()
            }

            awaitClose { cancel() }
        }

    override suspend fun deleteUser(id: String): Flow<Result<Unit>> = callbackFlow {
        firestore.collection(Constants.Firebase.COLLECTION_USERS)
            .document(id)
            .delete()
            .addOnSuccessListener { trySend(element = Result.success(value = Unit)) }
            .addOnFailureListener { close(cause = it.cause) }
            .await()

        awaitClose { cancel() }
    }
}