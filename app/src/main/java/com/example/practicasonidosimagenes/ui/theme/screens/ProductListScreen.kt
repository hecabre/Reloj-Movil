
package com.example.practicasonidosimagenes.ui.screens
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practicasonidosimagenes.data.Product
import com.example.practicasonidosimagenes.ui.theme.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductViewModel,
    onAddClick: () -> Unit,
    onEditClick: (product: Product) -> Unit
) {
    val products by viewModel.products.observeAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Productos") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Producto")
            }
        }
    ) { paddingValues ->
        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "No hay productos. Usa el botón '+' para añadir uno.",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Hernandez Cabrera Aaron",
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Santillan Balmaceda Dante",
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(products) { product ->
                    ProductItem(
                        product = product,
                        onEditClick = onEditClick,
                        onDeleteClick = { viewModel.deleteProduct(product) }
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onEditClick: (product: Product) -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEditClick(product) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
            Text(text = product.description, style = MaterialTheme.typography.titleMedium)
            Text(text = "$${String.format("%.2f", product.price)}", style = MaterialTheme.typography.bodySmall)
        }
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
        }
    }
}