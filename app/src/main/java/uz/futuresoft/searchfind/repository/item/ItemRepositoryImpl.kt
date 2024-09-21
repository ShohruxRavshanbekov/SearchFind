package uz.futuresoft.searchfind.repository.item

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import uz.futuresoft.searchfind.local.storage.LocalStorage
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.utils.Constants
import uz.futuresoft.searchfind.utils.Constants.TAG
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val localStorage: LocalStorage,
    private val firebaseStorage: FirebaseStorage,
    private val firestore: FirebaseFirestore,
) : ItemRepository {
    override suspend fun addItem(picture: Uri?, item: ItemModel): Flow<Result<Unit>> =
        callbackFlow {
            var pictureUrl = ""
            var uploadTask: UploadTask.TaskSnapshot? = null
            val storageReference = firebaseStorage.getReference("images/items/${item.title}")
            val itemDocument = firestore.collection(Constants.Firebase.COLLECTION_ITEMS).document()

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

                val itemWithPicture = ItemModel(
                    ownerId = item.ownerId,
                    categoryId = item.categoryId,
                    picture = pictureUrl,
                    title = item.title,
                    description = item.description,
                    place = item.place,
                    date = item.date,
                    status = item.status,
                    contactInfo = item.contactInfo
                )

                itemDocument.set(itemWithPicture)
                    .addOnSuccessListener { trySend(element = Result.success(value = Unit)) }
                    .addOnFailureListener { exception -> close(cause = exception.cause) }
                    .await()
            } else {
                itemDocument.set(item)
                    .addOnSuccessListener { trySend(element = Result.success(value = Unit)) }
                    .addOnFailureListener { exception -> close(cause = exception.cause) }
                    .await()
            }
            awaitClose { cancel() }
        }

    override suspend fun addItemToSaved(itemId: String): Flow<Result<Unit>> = flow {
        localStorage.saveItem(
            key = Constants.SharedPreferences.SAVED_ITEMS,
            itemId = itemId
        )
        emit(value = Result.success(value = Unit))
    }

    override suspend fun removeItemFromSaved(itemId: String): Flow<Result<Unit>> = flow {
        localStorage.removeItem(
            key = Constants.SharedPreferences.SAVED_ITEMS,
            itemId = itemId
        )
        emit(value = Result.success(value = Unit))
    }

    override suspend fun getItems(): Flow<Result<List<ItemModel>>> = callbackFlow {
        firestore.collection(Constants.Firebase.COLLECTION_ITEMS)
            .addSnapshotListener { value, error ->

                value?.let {
                    val items = ArrayList<ItemModel>()
                    val savedItemsIds =
                        localStorage.getSavedItems(Constants.SharedPreferences.SAVED_ITEMS)
                    it.toObjects(ItemModel::class.java).forEach { item ->
                        item.saved = savedItemsIds.contains(item.id)
                        items.add(item)
                    }
                    trySend(element = Result.success(value = items))
                }

                error?.let { close(cause = it.cause) }
            }
        awaitClose { cancel() }
    }

    override suspend fun getSavedItems(): Flow<Result<List<ItemModel>>> = callbackFlow {
        firestore.collection(Constants.Firebase.COLLECTION_ITEMS)
            .addSnapshotListener { value, error ->

                value?.let {
                    val savedItems = ArrayList<ItemModel>()
                    val savedItemsIds =
                        localStorage.getSavedItems(Constants.SharedPreferences.SAVED_ITEMS)
                    it.toObjects(ItemModel::class.java).forEach { item ->
                        if (savedItemsIds.contains(item.id)) {
                            item.saved = true
                            savedItems.add(item)
                        }
                    }
                    trySend(element = Result.success(value = savedItems))
                }

                error?.let { close(cause = it.cause) }
            }
        awaitClose { cancel() }
    }

    override suspend fun getItemsByCategory(categoryId: String): Flow<Result<List<ItemModel>>> =
        callbackFlow {
            firestore.collection(Constants.Firebase.COLLECTION_ITEMS)
                .addSnapshotListener { value, error ->

                    value?.let {
                        val items = ArrayList<ItemModel>()
                        it.toObjects(ItemModel::class.java).forEach { item ->
                            if (item.categoryId == categoryId) items.add(item)
                        }
                        trySend(element = Result.success(value = items))
                    }

                    error?.let { close(cause = it.cause) }
                }
            awaitClose { cancel() }
        }

    override suspend fun getItemsByUser(userId: String): Flow<Result<List<ItemModel>>> =
        callbackFlow {
            firestore.collection(Constants.Firebase.COLLECTION_ITEMS)
                .addSnapshotListener { value, error ->

                    value?.let {
                        val items = ArrayList<ItemModel>()
                        it.toObjects(ItemModel::class.java).forEach { item ->
                            if (item.ownerId == userId) items.add(item)
                        }
                        trySend(element = Result.success(value = items))
                    }

                    error?.let { close(cause = it.cause) }
                }
            awaitClose { cancel() }
        }

    override suspend fun getItemsByOrder(order: String): Flow<Result<List<ItemModel>>> =
        callbackFlow {
            firestore.collection(Constants.Firebase.COLLECTION_ITEMS)
                .addSnapshotListener { value, error ->

                    value?.let {
                        val items = ArrayList<ItemModel>()
                        it.toObjects(ItemModel::class.java).forEach { item ->
                            if (item.title.lowercase().contains(order.lowercase())) {
                                items.add(item)
                            }
                        }
                        trySend(element = Result.success(value = items))
                    }

                    error?.let { close(cause = it.cause) }
                }
            awaitClose { cancel() }
        }

    override suspend fun getItemById(itemId: String): Flow<Result<ItemModel>> = callbackFlow {
        firestore.collection(Constants.Firebase.COLLECTION_ITEMS)
            .addSnapshotListener { value, error ->

                value?.let {
                    val item = it.toObjects(ItemModel::class.java)
                        .find { item -> item.id == itemId } ?: ItemModel()
                    val savedItemsIds =
                        localStorage.getSavedItems(Constants.SharedPreferences.SAVED_ITEMS)
                    item.saved = savedItemsIds.contains(item.id)
                    trySend(element = Result.success(value = item))
                }

                error?.let { close(cause = it.cause) }
            }
        awaitClose { cancel() }
    }

    override suspend fun editItem(picture: Uri?, item: ItemModel): Flow<Result<Unit>> = callbackFlow {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(id: String): Flow<Result<Unit>> = callbackFlow {
        TODO("Not yet implemented")
    }
}