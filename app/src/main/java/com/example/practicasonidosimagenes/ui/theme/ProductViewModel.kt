package com.example.practicasonidosimagenes.ui.theme
import android.app.Application
import androidx.lifecycle.*
import com.example.practicasonidosimagenes.data.*
import kotlinx.coroutines.launch

class ProductViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: ProductQuerys

    private val _editingProduct = MutableLiveData<Product?>(null)
    val editingProduct: LiveData<Product?> = _editingProduct

    val products: LiveData<List<Product>>

    init {
        val dao = ProductDatabase.getDatabase(app).productDao()
        repository = ProductQuerys(dao)
        products = repository.products.asLiveData()
    }

    fun setEditingProduct(product: Product?) {
        _editingProduct.value = product
    }

    fun addOrUpdateProduct(name: String, price: String, description: String) {
        viewModelScope.launch {
            val parsedPrice = price.toDoubleOrNull() ?: 0.0
            val product = _editingProduct.value
            if (product == null) {
                repository.addProduct(Product(name = name, price = parsedPrice, description = description))
            } else {
                repository.updateProduct(product.copy(name = name, price = parsedPrice, description = description))
            }
            _editingProduct.value = null
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }
    class Factory(private val app: Application) : ViewModelProvider.AndroidViewModelFactory(app) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProductViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}