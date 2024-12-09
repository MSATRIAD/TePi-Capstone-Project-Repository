import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.pref.UserModel
import com.example.tepiapp.data.response.ListProductItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

class CatalogViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _productList = MutableLiveData<List<ListProductItem>>()
    val productList: LiveData<List<ListProductItem>> = _productList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _userSession = MutableLiveData<UserModel>()
    val userSession: LiveData<UserModel> = _userSession

    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val products = userRepository.getProducts()
                _productList.postValue(products)
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is SocketTimeoutException -> "Request timed out. Please try again."
                    is IOException -> "Failed to connect. Please check your internet connection."
                    else -> "An unexpected error occurred: ${e.localizedMessage}"
                }
                _errorMessage.postValue(errorMessage)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun getSession() {
        viewModelScope.launch {
            try {
                val session = userRepository.getSession().first()
                _userSession.value = session
            } catch (e: Exception) {
                _errorMessage.value = "Failed to get session: ${e.message}"
            }
        }
    }
}
