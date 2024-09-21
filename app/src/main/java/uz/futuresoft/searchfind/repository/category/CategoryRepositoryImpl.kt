package uz.futuresoft.searchfind.repository.category

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.futuresoft.searchfind.models.CategoryModel
import uz.futuresoft.searchfind.utils.Constants
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : CategoryRepository {

    override suspend fun getCategories(): Flow<Result<List<CategoryModel>>> = callbackFlow {
        firestore.collection(Constants.Firebase.COLLECTION_CATEGORY)
            .addSnapshotListener { value, error ->

                value?.let {
                    val categories = it.toObjects(CategoryModel::class.java)
                        .sortedBy { category -> category.name }

                    trySend(element = Result.success(value = categories))
                }

                error?.let {
                    close(cause = it.cause)
                }
            }
        awaitClose { cancel() }
    }
}


/** 2nd way to get data (without realtime updates) */
//        withContext(Dispatchers.IO) {
//            firestore.collection(Constants.Firebase.COLLECTION_CATEGORY)
//                .get()
//                .addOnSuccessListener { result ->
//                    trySend(element = Result.success(value = result.toObjects(CategoryModel::class.java)))
//                }
//                .addOnFailureListener { exception ->
//                    close(cause = exception.cause)
//                }
//        }
//        awaitClose { cancel() }
