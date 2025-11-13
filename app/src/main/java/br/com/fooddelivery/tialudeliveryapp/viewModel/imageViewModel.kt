package br.com.fooddelivery.tialudeliveryapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.net.Uri

class ProductImageViewModel : ViewModel() {

    var imageUri by mutableStateOf<Uri?>(null)
        private set

    var isUploading by mutableStateOf(false)
        private set

    fun setImage(uri: Uri) {
        imageUri = uri
    }

    fun uploadImage(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        isUploading = true
        try {
            val uploadedUrl = imageUri.toString()
            onSuccess(uploadedUrl)
        } catch (e: Exception) {
            onError("Erro ao enviar imagem: ${e.message}")
        } finally {
            isUploading = false
        }
    }

    fun clearImage() {
        imageUri = null
    }
}
