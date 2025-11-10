package com.example.practicasonidosimagenes.data
import kotlinx.coroutines.flow.Flow

class ProductQuerys(private val dao: ProductDao) {

    val products: Flow<List<Product>> = dao.getAllProducts()

    suspend fun addProduct(product: Product) {
        dao.addProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        dao.deleteProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        dao.updateProduct(product)
    }
}

