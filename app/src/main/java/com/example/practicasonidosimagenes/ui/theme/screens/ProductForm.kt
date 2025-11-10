package com.example.practicasonidosimagenes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.practicasonidosimagenes.ui.theme.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    viewModel: ProductViewModel,
    onDone: () -> Unit
) {
    val editingProduct by viewModel.editingProduct.observeAsState()

    var name by remember { mutableStateOf(editingProduct?.name ?: "") }
    var price by remember { mutableStateOf(editingProduct?.price?.toString() ?: "") }
    var description by remember {mutableStateOf(value = editingProduct?.description ?: "")}
    LaunchedEffect(editingProduct) {
        name = editingProduct?.name ?: ""
        price = if (editingProduct?.price != null && editingProduct?.price!! > 0.0)
            editingProduct!!.price.toString()
        else ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (editingProduct == null) "Añadir Producto" else "Editar Producto") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del Producto") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripcion") },
                modifier = Modifier.fillMaxWidth()
            )

            val isButtonEnabled = name.isNotBlank() && price.toDoubleOrNull() != null

            Button(
                onClick = {
                    viewModel.addOrUpdateProduct(name, price, description)
                    onDone()
                },
                enabled = isButtonEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (editingProduct == null) "Guardar Producto" else "Actualizar Producto")
            }

            if (editingProduct != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.deleteProduct(editingProduct!!)
                        onDone()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Eliminar Producto")
                }
            }
        }
    }
}