package uz.futuresoft.searchfind.repository.auth

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.futuresoft.searchfind.utils.Constants.TAG
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {

    override suspend fun authenticate(
        activity: Activity,
        phoneNumber: String,
    ): Flow<Result<String>> = callbackFlow {
        firebaseAuth.useAppLanguage()
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                trySend(element = Result.success(value = verificationId))
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d(TAG, "onVerificationFailed: ${e.message}")
                close(cause = e.cause)
            }
        }

        val phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)

        awaitClose { cancel() }
    }

    override suspend fun setCode(verificationId: String, code: String): Flow<Result<String>> =
        callbackFlow {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)

            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(element = Result.success(value = task.result.user?.uid ?: ""))
                    } else {
                        if (task.exception is FirebaseAuthInvalidCredentialsException)
                            trySend(element = Result.failure(exception = Throwable("invalid credential")))
                        else
                            trySend(element = Result.failure(exception = Throwable(message = task.exception?.message)))

                        close(cause = task.exception?.cause)
                    }
                }

            awaitClose { cancel() }
        }
}