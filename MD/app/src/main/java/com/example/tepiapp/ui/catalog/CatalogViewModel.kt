import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.pref.UserModel
import com.example.tepiapp.data.response.ListProductItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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
            _isLoading.value = true
            try {
                val products = userRepository.getProducts()
                _productList.value = products
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getSession() {
        viewModelScope.launch {
            try {
                val session = userRepository.getSession().first() // Mengambil data sesi pengguna
                _userSession.value = session
            } catch (e: Exception) {
                _errorMessage.value = "Failed to get session: ${e.message}"
            }
        }
    }
}
