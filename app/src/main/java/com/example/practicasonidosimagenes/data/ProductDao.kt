package com.example.practicasonidosimagenes.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)
}
