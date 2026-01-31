package com.chame.myapplication.features.pizzeriadistrito.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Mantenemos tu import de datos
import com.chame.myapplication.features.pizzeriadistrito.data.datasources.local.OrderStorage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(onBackClick: () -> Unit) {
    // Obtenemos las ordenes (invertidas para ver las más recientes primero, opcional)
    val orders = OrderStorage.orders.reversed()

    // Colores de la marca
    val pizzaPrimaryColor = Color(0xFFE65100)
    val backgroundColor = Color(0xFFF5F5F5)
    val successColor = Color(0xFF2E7D32) // Verde más elegante

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Historial de Ventas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = pizzaPrimaryColor,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        if (orders.isEmpty()) {
            // Estado vacío más bonito
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Aún no hay ventas registradas",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(orders) { order ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            // Encabezado: Nombre de Pizza y Fecha
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = order.pizzaName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.Black
                                )
                                Surface(
                                    color = backgroundColor, // Fondo grisecito para la fecha
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = order.date,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        color = Color.Gray
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Sección de Cliente
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = order.clientName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.DarkGray
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp),
                                thickness = 1.dp,
                                color = Color(0xFFEEEEEE)
                            )

                            // Detalles Financieros
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Columna Izquierda: Detalles
                                Column {
                                    Text("Precio Pizza:", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                    Text("$${order.price}", fontWeight = FontWeight.SemiBold)

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text("Recibido:", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                    Text("$${order.totalPaid}", fontWeight = FontWeight.SemiBold)
                                }

                                // Columna Derecha: El cambio (Resaltado)
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("CAMBIO", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = successColor)
                                    Text(
                                        text = "$${String.format("%.2f", order.changeReturned)}",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = successColor
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}