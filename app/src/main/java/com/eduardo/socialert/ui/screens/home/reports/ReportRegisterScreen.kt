package com.eduardo.socialert.ui.screens.home.reports

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.eduardo.socialert.R
import com.eduardo.socialert.navigation.AppScreens
import com.eduardo.socialert.ui.components.CButton
import com.eduardo.socialert.ui.components.CIcon
import com.eduardo.socialert.ui.components.CTextError
import com.eduardo.socialert.ui.components.CTextField
import com.eduardo.socialert.ui.viewmodel.report.FormReportRegisterViewModel
import com.eduardo.socialert.ui.viewmodel.report.RegisterReportViewModel
import com.eduardo.socialert.ui.viewmodel.report.ReportViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

@Composable
fun ReportRegisterScreen(
    navController: NavHostController,
    context: Context,
    reportViewModel: ReportViewModel,
    formReportRegisterViewModel: FormReportRegisterViewModel,
    registerReportViewModel: RegisterReportViewModel
) {

    val reportsType by reportViewModel.reportsTypeState
    val errorMessage by reportViewModel.getTypesErrorMessage

    var selectedId by remember { mutableStateOf<Int?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        reportViewModel.getAllReportsType(context)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                })
            }
    ) {
        TextHeader()
        Spacer(modifier = Modifier.height(10.dp))

        if (reportsType.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.imePadding(),
            ) {
                items(reportsType) { type ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedId = type.id
                                formReportRegisterViewModel.type_id = selectedId
                            }
                            .background(if (selectedId == type.id) Color(0X402C5FAA) else Color.Transparent)
                            .padding(vertical = 15.dp, horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val (painter, contentDescription) = when (type.name) {
                            "Vandalismo" ->
                                painterResource(id = R.drawable.vandalism_icon) to "vandalism_icon"

                            "Asalto" ->
                                painterResource(id = R.drawable.assautl_icon) to "assault_icon"

                            "Violencia doméstica" ->
                                painterResource(id = R.drawable.domestic_violence_icon) to "domestic_violence_icon"

                            "Incidente de tránsito" ->
                                painterResource(id = R.drawable.acciden_icon) to "accident_icon"

                            else ->
                                painterResource(id = R.drawable.logo) to "default_icon"
                        }
                        CIcon(
                            painter = painter,
                            contentDescription = contentDescription,
                            height = 45
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(type.name, style = MaterialTheme.typography.bodyMedium)
                    }
                    HorizontalDivider()
                }

                if (formReportRegisterViewModel.typeIdError.isNotBlank()) {
                    item {
                        CTextError(errorText = formReportRegisterViewModel.typeIdError)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    VictimButtons(formReportRegisterViewModel)
                    Spacer(modifier = Modifier.height(20.dp))
                    ButtonSelectorFile(context, formReportRegisterViewModel)
                    Spacer(modifier = Modifier.height(20.dp))
                    DescriptionTextField(formReportRegisterViewModel)
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    OptionButtons(
                        navController = navController,
                        formReportRegisterViewModel,
                        registerReportViewModel,
                        context
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    CurrentLocation(context = context, formReportRegisterViewModel)
                }
            }


        } else {
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, style = MaterialTheme.typography.bodyMedium)
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Cargando...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CircularProgressIndicator(color = Color(0XFF2C5FAA))
                }
            }
        }
    }
}

@Composable
fun TextHeader() {
    Spacer(
        modifier = Modifier
            .fillMaxHeight(0.07F)
            .fillMaxWidth(1F)
    )
    Text(
        "¿Qué tipo de reporte quieres realizar?",
        modifier = Modifier
            .padding(start = 20.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun VictimButtons(formReportRegisterViewModel: FormReportRegisterViewModel) {

    Text(
        "¿Eres tú la victima?",
        modifier = Modifier
            .padding(start = 20.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        CButton(
            onClick = { formReportRegisterViewModel.victim = "SI" },
            text = "SI",
            width = 80,
            cornerShape = 50,
            enabled = formReportRegisterViewModel.victim != "SI"
        )
        Spacer(modifier = Modifier.width(40.dp))
        CButton(
            onClick = { formReportRegisterViewModel.victim = "NO" },
            text = "NO",
            width = 80,
            cornerShape = 50,
            enabled = formReportRegisterViewModel.victim != "NO"
        )
    }
}

@Composable
fun ButtonSelectorFile(context: Context, formReportRegisterViewModel: FormReportRegisterViewModel) {
//    var selectedUriVideo by remember { mutableStateOf<Uri?>(null) }
//    var selectedUriImage by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            formReportRegisterViewModel.selectedUriVideo =
                null // Asegúrate de limpiar la URI del video
            formReportRegisterViewModel.selectedUriImage = uri
//            selectedUriImage = uri // Guarda la URI de la imagen
        }
    )

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            formReportRegisterViewModel.selectedUriImage =
                null // Asegúrate de limpiar la URI de la imagen
            formReportRegisterViewModel.selectedUriVideo = uri // Guarda la URI del video
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Envíanos una foto o video (opcional)",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row {
            CButton(
                onClick = {
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                text = "Foto",
                width = 100
            )
            Spacer(modifier = Modifier.width(40.dp))
            CButton(
                onClick = {
                    videoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.VideoOnly
                        )
                    )
                },
                text = "Video",
                width = 100
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        formReportRegisterViewModel.selectedUriImage?.let {
            AsyncImage(
                model = it,
                contentDescription = "Imagen seleccionada",
                modifier = Modifier.size(200.dp)
            )
        } ?: formReportRegisterViewModel.selectedUriVideo?.let {
            VideoPlayer(uri = it, context)
        }
    }
}

@Composable
fun VideoPlayer(uri: Uri?, context: Context) {
    val exoPlayer = remember(uri) {
        if (uri != null) {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(uri))
                prepare()
            }
        } else {
            null
        }
    }

    if (exoPlayer != null) {
        AndroidView(
            factory = { PlayerView(context).apply { player = exoPlayer } },
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp),
            update = { playerView ->
                playerView.player = exoPlayer
            }
        )
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer?.release()
        }
    }
}

@Composable
fun DescriptionTextField(formReportRegisterViewModel: FormReportRegisterViewModel) {
    Text(
        "Escribe una breve descripción (opcional)",
        modifier = Modifier
            .padding(start = 20.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(10.dp))

    CTextField(
        value = formReportRegisterViewModel.description,
        onValueChange = { formReportRegisterViewModel.description = it },
        label = "Descripción",
        singleLine = false,
        maxLines = 3
    )
    if (formReportRegisterViewModel.descriptionError.isNotEmpty()){
        CTextError(errorText = formReportRegisterViewModel.descriptionError)
    }
}

@Composable
fun OptionButtons(
    navController: NavHostController,
    formReportRegisterViewModel: FormReportRegisterViewModel,
    registerReportViewModel: RegisterReportViewModel,
    context: Context
) {
    val isLoading = registerReportViewModel.isLoading.value

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center

    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color(0XFF2C5FAA))
        } else {
            CButton(
                onClick = {
                    formReportRegisterViewModel.clearFields()
                    navController.navigate(route = AppScreens.MenuScreen.route)
                },
                text = "Cancelar",
                width = 150,
                cornerShape = 30,
                containerColor = Color.Red,
                contentColor = Color.White
            )
            Spacer(modifier = Modifier.width(40.dp))
            CButton(
                onClick = {
                    formReportRegisterViewModel.selectedUriImage?.let {
                        formReportRegisterViewModel.file = it
                    }

                    formReportRegisterViewModel.selectedUriVideo?.let {
                        formReportRegisterViewModel.file = it
                    }

                    formReportRegisterViewModel.type_id?.let {
                        registerReportViewModel.registerReport(
                            context,
                            it,
                            formReportRegisterViewModel.description,
                            formReportRegisterViewModel.file,
                            formReportRegisterViewModel.latitude,
                            formReportRegisterViewModel.longitude,
                            alert = 0
                        )
                    }

                },
                enabled = formReportRegisterViewModel.formValidation(),
                text = "Continuar",
                width = 150,
                cornerShape = 30,
                containerColor = Color(0XFF46ff33),
                contentColor = Color.Black,
                disableContainerColor = Color(0X4046ff33)
            )
        }
    }

    LaunchedEffect(isLoading) {
        if (!isLoading && (registerReportViewModel.message.value == "Reporte enviado correctamente.")) {
            Toast.makeText(context, registerReportViewModel.message.value, Toast.LENGTH_LONG).show()
            navController.navigate(AppScreens.MenuScreen.route) {
                popUpTo(AppScreens.ReportRegisterScreen.route) { inclusive = true }
            }
            formReportRegisterViewModel.clearFields()
            registerReportViewModel.message.value = ""
        }
    }

    registerReportViewModel.errorMessage.value.let { errorMessage ->
        if (errorMessage.isNotBlank()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
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