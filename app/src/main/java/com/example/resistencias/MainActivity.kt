package com.example.resistencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resistencias.ui.theme.ResistenciasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ResistenciasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ResistanceCalculatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ResistanceCalculatorScreen(modifier: Modifier = Modifier) {
    val colores = listOf(
        "Negro" to Color.Black,
        "Marrón" to Color(0xFFA52A2A), // Marrón
        "Rojo" to Color.Red,
        "Naranja" to Color(0xFFFFA500), // Naranja
        "Amarillo" to Color.Yellow,
        "Verde" to Color(0xFF008000), // Verde
        "Azul" to Color.Blue,
        "Violeta" to Color(0xFF8A2BE2), // Violeta
        "Gris" to Color.Gray,
        "Blanco" to Color.White
    )

    var banda1 by remember { mutableStateOf(colores[0].first) }
    var banda2 by remember { mutableStateOf(colores[0].first) }
    var multiplicador by remember { mutableStateOf(colores[0].first) }
    var resultado by remember { mutableStateOf(0) }

    fun calcularResistencia(banda1: String, banda2: String, multiplicador: String): Int {
        val valoresColores = mapOf(
            "Negro" to 0, "Marrón" to 1, "Rojo" to 2, "Naranja" to 3,
            "Amarillo" to 4, "Verde" to 5, "Azul" to 6, "Violeta" to 7,
            "Gris" to 8, "Blanco" to 9
        )
        val multiplicadorValores = mapOf(
            "Negro" to 1, "Marrón" to 10, "Rojo" to 100, "Naranja" to 1_000,
            "Amarillo" to 10_000, "Verde" to 100_000, "Azul" to 1_000_000,
            "Violeta" to 10_000_000, "Gris" to 100_000_000, "Blanco" to 1_000_000_000
        )

        val valor1 = valoresColores[banda1] ?: 0
        val valor2 = valoresColores[banda2] ?: 0
        val multiplicadorValor = multiplicadorValores[multiplicador] ?: 1

        return ((valor1 * 10) + valor2) * multiplicadorValor
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Calculadora de Resistencias", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Selecciona la Banda 1")
        ColorButtonGroup(selectedColor = banda1, colors = colores) { banda1 = it }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Selecciona la Banda 2")
        ColorButtonGroup(selectedColor = banda2, colors = colores) { banda2 = it }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Selecciona el Multiplicador")
        ColorButtonGroup(selectedColor = multiplicador, colors = colores) { multiplicador = it }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            resultado = calcularResistencia(banda1, banda2, multiplicador)
        }) {
            Text(text = "Calcular")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Valor de la resistencia: $resultado ohms")
    }
}

@Composable
fun ColorButtonGroup(selectedColor: String, colors: List<Pair<String, Color>>, onColorSelected: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        colors.forEach { (colorName, color) ->
            Button(
                onClick = { onColorSelected(colorName) },
                colors = ButtonDefaults.buttonColors(containerColor = color), // Cambiado aquí
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(text = colorName, color = Color.White) // Nombre del color
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResistanceCalculatorPreview() {
    ResistenciasTheme {
        ResistanceCalculatorScreen()
    }
}
