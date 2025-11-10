package com.example.practicasonidosimagenes

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicasonidosimagenes.ui.theme.PracticaSonidosImagenesTheme
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticaSonidosImagenesTheme {
                RelojScreen()
            }
        }
    }
}

@Composable
fun RelojScreen() {
    var hora by remember { mutableStateOf(LocalTime.now()) }
    val contexto = LocalContext.current

    // Actualizar hora cada minuto
    LaunchedEffect(Unit) {
        while (true) {
            hora = LocalTime.now()
            delay(60_000)
        }
    }

    // Formato solo hora:minutos
    val formato = DateTimeFormatter.ofPattern("HH:mm")
    val horaFormateada = hora.format(formato)

    // Animación ligera para el texto
    val scale by animateFloatAsState(targetValue = 1f)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Hora actual",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = horaFormateada,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                fontSize = 72.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.scale(scale)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    val mediaPlayers = mutableListOf<MediaPlayer>()
                    val horaActual = hora.hour
                    val minutos = hora.minute

                    val hora12 = when {
                        horaActual == 0 -> 12
                        horaActual > 12 -> horaActual - 12
                        else -> horaActual
                    }

                    val nombreHora = when (hora12) {
                        1 -> "un"
                        else -> numeroEnPalabra(hora12)
                    }

                    val nombreMinuto = numeroEnPalabra(minutos)

                    val audioHoraId = contexto.resources.getIdentifier(nombreHora, "raw", contexto.packageName)
                    val audioMinutoId = contexto.resources.getIdentifier(nombreMinuto, "raw", contexto.packageName)

                    if (audioHoraId != 0) mediaPlayers.add(MediaPlayer.create(contexto, audioHoraId))
                    if (audioMinutoId != 0) mediaPlayers.add(MediaPlayer.create(contexto, audioMinutoId))

                    if (mediaPlayers.isNotEmpty()) {
                        playSequentially(mediaPlayers)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text("Reproducir hora", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

fun numeroEnPalabra(numero: Int): String {
    return when (numero) {
        0 -> "cero"
        1 -> "un"
        2 -> "dos"
        3 -> "tres"
        4 -> "cuatro"
        5 -> "cinco"
        6 -> "seis"
        7 -> "siete"
        8 -> "ocho"
        9 -> "nueve"
        10 -> "diez"
        11 -> "once"
        12 -> "doce"
        13 -> "trece"
        14 -> "catorce"
        15 -> "quince"
        16 -> "dieciseis"
        17 -> "diecisiete"
        18 -> "dieciocho"
        19 -> "diecinueve"
        20 -> "veinte"
        21 -> "veintiuno"
        22 -> "veintidos"
        23 -> "veintitres"
        24 -> "veinticuatro"
        25 -> "veinticinco"
        26 -> "veintiseis"
        27 -> "veintisiete"
        28 -> "veintiocho"
        29 -> "veintinueve"
        30 -> "treinta"
        31 -> "treintayuno"
        32 -> "treintaydos"
        33 -> "treintaytres"
        34 -> "treintaycuatro"
        35 -> "treintaycinco"
        36 -> "treintayseis"
        37 -> "treintaysiete"
        38 -> "treintayocho"
        39 -> "treintaynueve"
        40 -> "cuarenta"
        41 -> "cuarentayuno"
        42 -> "cuarentaydos"
        43 -> "cuarentaytres"
        44 -> "cuarentaycuatro"
        45 -> "cuarentaycinco"
        46 -> "cuarentayseis"
        47 -> "cuarentaysiete"
        48 -> "cuarentayocho"
        49 -> "cuarentaynueve"
        50 -> "cincuenta"
        51 -> "cincuentayuno"
        52 -> "cincuentaydos"
        53 -> "cincuentaytres"
        54 -> "cincuentaycuatro"
        55 -> "cincuentaycinco"
        56 -> "cincuentayseis"
        57 -> "cincuentaysiete"
        58 -> "cincuentayocho"
        59 -> "cincuentaynueve"
        else -> "cero"
    }
}

// Reproduce los audios en secuencia
fun playSequentially(players: List<MediaPlayer>, index: Int = 0) {
    if (index >= players.size) return
    val player = players[index]
    player.setOnCompletionListener {
        player.release()
        playSequentially(players, index + 1)
    }
    player.start()
}
