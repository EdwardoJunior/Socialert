package com.eduardo.socialert.ui.screens.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.net.Uri
import android.widget.Space
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.AdPlaybackState
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.eduardo.socialert.R
import com.eduardo.socialert.data.model.ReportDetails
import com.eduardo.socialert.navigation.AppScreens
import com.eduardo.socialert.navigation.MenuScreens
import com.eduardo.socialert.ui.components.CIcon
import com.eduardo.socialert.ui.viewmodel.auth.LoginViewModel
import com.eduardo.socialert.ui.viewmodel.report.FormReportRegisterViewModel
import com.eduardo.socialert.ui.viewmodel.report.RegisterReportViewModel
import com.eduardo.socialert.ui.viewmodel.report.ReportViewModel
import com.eduardo.socialert.ui.viewmodel.report.formatDate
import com.eduardo.socialert.ui.viewmodel.report.formatHour
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    navController: NavController,
    context: Context,
    registerReportViewModel: RegisterReportViewModel,
    formReportRegisterViewModel: FormReportRegisterViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CurrentLocation(context = context, formReportRegisterViewModel = formReportRegisterViewModel)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = Color(0x9f2C5FAA),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("REPORTES", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = {
                        navController.navigate(AppScreens.ReportRegisterScreen.route)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Text("Nuevo", style = MaterialTheme.typography.bodyMedium)
                }

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.eye_regular),
                            contentDescription = "",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Text("Ver reportes", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                registerReportViewModel.registerReport(
                    context,
                    type_id = 1,
                    description = "ESTO ES UNA ALERTA",
                    file = null,
                    latitude = formReportRegisterViewModel.latitude,
                    longitude = formReportRegisterViewModel.longitude,
                    alert = 1
                )
            },
            modifier = Modifier
                .height(357.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = Color(0x9f2C5FAA),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary
            )

        ) {
            if(registerReportViewModel.isLoading.value){
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    strokeWidth = 15.dp,
                    color = Color.White
                )
            }else{
                Icon(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                )
            }
        }

        LaunchedEffect(registerReportViewModel.isLoading.value) {
            if (!registerReportViewModel.isLoading.value && (registerReportViewModel.message.value == "Reporte enviado correctamente.")) {
                Toast.makeText(context, "Alerta enviada correctamente", Toast.LENGTH_LONG).show()
                navController.navigate(AppScreens.MenuScreen.route)
                formReportRegisterViewModel.clearFields()
                registerReportViewModel.message.value = ""
            }
        }

        registerReportViewModel.errorMessage.value.let { errorMessage ->
            if(errorMessage.isNotBlank()){
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}


@Composable
fun CurrentLocation(context: Context, formReportRegisterViewModel: FormReportRegisterViewModel) {
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var accuracy by remember { mutableStateOf<Float?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                requestLocationUpdates(fusedLocationClient) { location ->
                    formReportRegisterViewModel.latitude = location?.latitude
                    formReportRegisterViewModel.longitude = location?.longitude
                    accuracy = location?.accuracy
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

@SuppressLint("MissingPermission")
fun requestLocationUpdates(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationResult: (Location?) -> Unit
) {
    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,  // High accuracy for the best result
        null
    ).addOnSuccessListener { location: Location? ->
        if (location != null) {
            onLocationResult(location) // Return the current location
        } else {
            onLocationResult(null) // No location available
        }
    }
}



//@Composable
//fun HomeScreen(
//    navController: NavController,
//    authViewModel: LoginViewModel,
//    context: Context,
//    reportViewModel: ReportViewModel
//) {
//
////    Column(
////        modifier = Modifier.fillMaxSize().background(Color.Gray),
////        horizontalAlignment = Alignment.CenterHorizontally,
////        verticalArrangement = Arrangement.Center
////    ) {
////        Text(text = "HOME")
////
////        Button(onClick = {
////            authViewModel.logoutUser(context)
////        }) {
////            Text("Cerrar sesión")
////        }
////
////        authViewModel.logoutResponse.value?.let {
////            if (it.message == "Cierre de sesión exitoso") {
////                Toast.makeText(context, "Cierre de sesión exitoso", Toast.LENGTH_LONG).show()
////
////                navController.navigate(AppScreens.LoginScreen.route)}
////        }
////    }
//
//    val reports by reportViewModel.reportsState
//    val errorMessage by reportViewModel.errorMessage
//
//    LaunchedEffect(Unit) {
//        reportViewModel.getAllReports(context)
//    }
//
//
//    if (reports.isNotEmpty()) {
//        val orderedReports = reports.sortedByDescending { it.updated_at }
//        LazyColumn{
//            items(orderedReports) { report ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { }
//                        .padding(vertical = 17.dp, horizontal = 25.dp)
//                ) {
//                    Column{
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//
//                            val (painter, contentDescription) = when (report.type_name) {
//                                "Vandalismo" ->
//                                    painterResource(id = R.drawable.vandalism_icon) to "vandalism_icon"
//
//                                "Asalto" ->
//                                    painterResource(id = R.drawable.assautl_icon) to "assault_icon"
//
//                                "Violencia doméstica" ->
//                                    painterResource(id = R.drawable.domestic_violence_icon) to "domestic_violence_icon"
//
//                                "Accidente vehicular" ->
//                                    painterResource(id = R.drawable.acciden_icon) to "accident_icon"
//
//                                else ->
//                                    painterResource(id = R.drawable.logo) to "default_icon"
//                            }
//
//                            CIcon(
//                                painter = painter,
//                                contentDescription = contentDescription,
//                                height = 45
//                            )
//                            Spacer(modifier = Modifier.width(10.dp))
//                            Text(report.type_name, fontSize = 20.sp)
//                            Text(
//                                formatHour(report.updated_at),
//                                style = MaterialTheme.typography.bodySmall,
//                                modifier = Modifier.fillMaxWidth(),
//                                textAlign = TextAlign.End
//                            )
//                        }
////                        Text(formatDate(report.updated_at), style = MaterialTheme.typography.bodySmall)
//                        Spacer(modifier = Modifier.height(15.dp))
//                        if (report.description?.isBlank() == true) {
//                            Text(
//                                text = "Sin descripción",
//                                style = MaterialTheme.typography.bodySmall
//                            )
//                        } else {
//                            report.description?.let {
//                                Text(
//                                    text = it,
//                                    style = MaterialTheme.typography.bodySmall
//                                )
//                            }
//                        }
//                    }
//                }
//                HorizontalDivider()
//
//            }
//            item {
//                Spacer(modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp))
//            }
//        }
//    } else {
//        if (errorMessage.isNotEmpty()) {
//            Text(
//                errorMessage,
//                modifier = Modifier.fillMaxSize(),
//                textAlign = TextAlign.Center,
//                style = MaterialTheme.typography.titleLarge
//            )
//        } else {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    "Cargando reportes...",
//                    style = MaterialTheme.typography.titleLarge
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//                CircularProgressIndicator(color = Color(0XFF2C5FAA))
//            }
//        }
//
//    }
//}

//@Composable
//fun HomeScreen(
//navController: NavController,
//authViewModel: LoginViewModel,
//context: Context,
//reportViewModel: ReportViewModel
//) {
//
//    val reports by reportViewModel.reports.observeAsState(listOf())
//    val message by reportViewModel.message.observeAsState()
//
//    LaunchedEffect(Unit) {
//        reportViewModel.getAllReports(context)
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp)
//    ) {
//        Text("Reportes", fontSize = 40.sp)
//
//        if (reports.isNotEmpty()) {
//            LazyColumn(modifier = Modifier.fillMaxSize()) {
//                items(reports) { report ->
//                    ReportItem(report = report, context)
//                }
//            }
//        } else {
//            message?.let { Text(text = it) }
//        }
//    }
//
//
////
//}
//
//@Composable
//fun ReportItem(report: ReportDetails, context: Context) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    ) {
//        Text("Tipo: ${report.type_name}")
//        Text(report.description)
//        Spacer(modifier = Modifier.height(8.dp))
//
//        if (report.file.endsWith(".jpg") || report.file.endsWith(".png")) {
//            AsyncImage(
//                model = report.file,
//                contentDescription = "",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .height(250.dp),
////                contentScale = ContentScale.Crop
//            )
//        } else if (report.file.endsWith(".mp4")) {
//            VideoPlayer(videoUrl = report.file, context)
//        }
//        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
//    }
//}
//
//@Composable
//fun VideoPlayer(videoUrl: String, context: Context) {
//
//    val exoPlayer = remember {
//        createExoPlayer(context, videoUrl)
//    }
//    var isPlaying by remember { mutableStateOf(false) }
//    var currentPosition by remember { mutableLongStateOf(0L) }
//    var videoDuration by remember { mutableLongStateOf(0L) }
//
//
//    LaunchedEffect(exoPlayer) {
//        exoPlayer.addListener(object : Player.Listener{
//            override fun onPlaybackStateChanged(playbackState: Int){
//                if(playbackState == ExoPlayer.STATE_READY){
//                    videoDuration = exoPlayer.duration
//                }
//            }
//        })
//    }
//
//    LaunchedEffect(Unit) {
//        videoDuration = exoPlayer.duration
//        while (true){
//            currentPosition = exoPlayer.currentPosition
//            delay(500)
//        }
//    }
//
//    Column(modifier = Modifier.fillMaxWidth()) {
//        AndroidView(
//            factory = {
//                PlayerView(context).apply {
//                    player = exoPlayer
//                    useController = false
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//        )
//
//        if (videoDuration > 0L) { // Asegurarnos de que la duración del video es válida
//            Slider(
//                value = currentPosition.toFloat(),
//                onValueChange = { position ->
//                    exoPlayer.seekTo(position.toLong()) // Mover a la posición seleccionada
//                },
//                valueRange = 0f..videoDuration.toFloat(),
//                modifier = Modifier.padding(horizontal = 16.dp)
//            )
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(
//                onClick = {
//                    if (!isPlaying) {
//                        exoPlayer.play()
//                        isPlaying = true
//                    }
//                }
//            ) {
//                Text("Play")
//            }
//
//            Button(
//                onClick = {
//                    if (isPlaying) {
//                        exoPlayer.pause()
//                        isPlaying = false
//                    }
//                }
//            ) {
//                Text("Pause")
//            }
//
//            Button(
//                onClick = {
//                    exoPlayer.stop()
//                    isPlaying = false
//                }
//            ) {
//                Text("Stop")
//            }
//        }
//    }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            exoPlayer.release() // Liberar los recursos de ExoPlayer
//        }
//    }
//}
//
//fun createExoPlayer(context: Context, videoUrl: String): ExoPlayer {
//    return ExoPlayer.Builder(context).build().apply {
//        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
//        setMediaItem(mediaItem)
//        prepare()
//    }
//}
