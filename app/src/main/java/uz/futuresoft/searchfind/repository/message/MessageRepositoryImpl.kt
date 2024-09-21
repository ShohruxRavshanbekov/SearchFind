package uz.futuresoft.searchfind.repository.message

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.models.MessageModel
import uz.futuresoft.searchfind.utils.Constants
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : MessageRepository {

    override suspend fun addMessage(message: MessageModel): Flow<Result<Unit>> = callbackFlow {
        firestore.collection(Constants.Firebase.COLLECTION_MESSAGES).document()
            .set(message)
            .addOnSuccessListener { trySend(element = Result.success(value = Unit)) }
            .addOnFailureListener { exception -> close(cause = exception.cause) }
            .await()
        awaitClose { cancel() }
    }

    override suspend fun getMessages(userId: String): Flow<Result<List<MessageModel>>> =
        callbackFlow {
            firestore.collection(Constants.Firebase.COLLECTION_MESSAGES)
                .addSnapshotListener { value, error ->

                    value?.let {
                        val messages = ArrayList<MessageModel>()
                        it.toObjects(MessageModel::class.java).forEach { message ->
                            if (message.itemOwnerId == userId) messages.add(message)
                        }
                        trySend(element = Result.success(value = messages))
                    }

                    error?.let { close(cause = it.cause) }
                }
            awaitClose { cancel() }
        }

    override suspend fun deleteMessage(id: String): Flow<Result<Unit>> = callbackFlow {
        firestore.collection(Constants.Firebase.COLLECTION_MESSAGES)
            .document(id)
            .delete()
            .addOnSuccessListener { trySend(element = Result.success(value = Unit)) }
            .addOnFailureListener { close(cause = it.cause) }
            .await()

        awaitClose { cancel() }
    }
}