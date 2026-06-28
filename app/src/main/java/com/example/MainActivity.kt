package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingFlat
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.CompositionLocalProvider
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.graphics.SolidColor
import android.content.Context
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.window.Dialog
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppDatabase
import com.example.data.ConfluenceRepository
import com.example.data.ConfluenceSetup
import com.example.ui.ConfluenceViewModel
import com.example.ui.ConfluenceViewModelFactory
import com.example.ui.LiveConfluenceResult
import com.example.ui.AiSummaryState
import com.example.ui.theme.BullishGreen
import com.example.ui.theme.BearishRed
import com.example.ui.theme.GoldGold
import com.example.ui.theme.DeepSpaceDb
import com.example.ui.theme.CardSurfaceDb
import com.example.ui.theme.BorderDb
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.StrixaLogo
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextMuted
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ConfluenceRepository(database.confluenceDao())
        val factory = ConfluenceViewModelFactory(repository)

        setContent {
            MyApplicationTheme {
                var showSplash by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(3800) // Stay a little bit longer with all the animations as requested
                    showSplash = false
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    if (showSplash) {
                        SplashScreen()
                    } else {
                        val viewModel: ConfluenceViewModel by viewModels { factory }
                        MainDashboardScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_scale"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_alpha"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepSpaceDb)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1.0f))

        // Center visual icon showing Candlesticks + Rings
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(scale)
                .alpha(alpha)
        ) {
            // Custom brand logo via reusable StrixaLogo component
            StrixaLogo(
                modifier = Modifier
                    .size(120.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "STRIXA",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 6.sp,
                color = TextPrimary,
                fontFamily = FontFamily.SansSerif
            )
            
            Text(
                text = "Where Discipline Meets Precision",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = GoldGold,
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1.0f))

        // Circular loading icon with amazing motion
        Box(
            contentAlignment = Alignment.Center
        ) {
            val infiniteRotation = rememberInfiniteTransition(label = "spinner_rotate")
            val angle by infiniteRotation.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "angle"
            )
            
            // Background glow halo
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(ElectricBlue.copy(alpha = 0.08f), shape = CircleShape)
            )

            // Dynamic cosmic ring
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .graphicsLayer { rotationZ = angle },
                color = ElectricBlue,
                strokeWidth = 3.dp
            )
            
            // Outer small planetary dot inside the rotation to make it look extra premium
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .graphicsLayer { rotationZ = -angle * 1.5f },
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(BullishGreen, shape = CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Built by Srijan credit exactly as requested
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Designed & Built with ❤️ by ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = TextPrimary)) {
                        append("SRIJAN")
                    }
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.8.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "v2.0.2",
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                color = TextMuted,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashboardScreen(viewModel: ConfluenceViewModel) {
    val symbol by viewModel.symbol.collectAsState()
    val tf1w by viewModel.tf1w.collectAsState()
    val tf1d by viewModel.tf1d.collectAsState()
    val tf4h by viewModel.tf4h.collectAsState()
    val tf1h by viewModel.tf1h.collectAsState()
    val tf30m by viewModel.tf30m.collectAsState()
    val tf15m by viewModel.tf15m.collectAsState()
    val note by viewModel.note.collectAsState()

    val aoiTouches by viewModel.aoiTouches.collectAsState()
    val selectedMarketStructure by viewModel.selectedMarketStructure.collectAsState()
    val selectedCandlesticks by viewModel.selectedCandlesticks.collectAsState()
    val selectedPatterns by viewModel.selectedPatterns.collectAsState()
    val newsImpact by viewModel.newsImpact.collectAsState()

    val liveResult by viewModel.liveResult.collectAsState()
    val loggedSetups by viewModel.loggedSetups.collectAsState()

    var showResultsPage by remember { mutableStateOf(false) }
    var isDrawerOpen by remember { mutableStateOf(false) }
    var isRiskLabCalculatorOpen by remember { mutableStateOf(false) }

    var marketStructureExpanded by remember { mutableStateOf(false) }
    var candlesticksExpanded by remember { mutableStateOf(false) }
    var patternsExpanded by remember { mutableStateOf(false) }

    var selectedTab by remember { mutableStateOf("CONFLUENCE") }
    val listState = rememberLazyListState()
    var previousIndex by remember { mutableStateOf(0) }
    var previousScrollOffset by remember { mutableStateOf(0) }
    var isBottomBarVisible by remember { mutableStateOf(true) }
    var isConfluenceRefreshing by remember { mutableStateOf(false) }
    val confluencePullState = rememberPullToRefreshState()
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    var lastBackPressTime by remember { mutableStateOf(0L) }
    var showBackExitToast by remember { mutableStateOf(false) }

    LaunchedEffect(showBackExitToast) {
        if (showBackExitToast) {
            delay(2000)
            showBackExitToast = false
        }
    }

    BackHandler(enabled = true) {
        if (showResultsPage) {
            showResultsPage = false
        } else if (selectedTab != "CONFLUENCE") {
            selectedTab = "CONFLUENCE"
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastBackPressTime < 2000) {
                val activity = context as? android.app.Activity
                activity?.finishAndRemoveTask()
            } else {
                lastBackPressTime = currentTime
                showBackExitToast = true
            }
        }
    }

    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        val currentIndex = listState.firstVisibleItemIndex
        val currentOffset = listState.firstVisibleItemScrollOffset
        if (currentIndex > previousIndex) {
            isBottomBarVisible = false
        } else if (currentIndex < previousIndex) {
            isBottomBarVisible = true
        } else {
            if (currentOffset > previousScrollOffset + 15) {
                isBottomBarVisible = false
            } else if (currentOffset < previousScrollOffset - 15) {
                isBottomBarVisible = true
            }
        }
        previousIndex = currentIndex
        previousScrollOffset = currentOffset
    }

    if (showResultsPage) {
        ConfluenceResultsPage(
            viewModel = viewModel,
            onBack = { showResultsPage = false }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            KeepAliveContainer(visible = selectedTab == "CONFLUENCE") {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DeepSpaceDb),
                    containerColor = DeepSpaceDb,
                topBar = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CardSurfaceDb)
                            .border(width = 1.dp, color = BorderDb)
                            .statusBarsPadding()
                            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "STRIXA CONFLUENCE",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.5.sp,
                                    color = TextPrimary,
                                    style = TextStyle(
                                        shadow = Shadow(
                                            color = ElectricBlue.copy(alpha = 0.6f),
                                            offset = Offset(2f, 2f),
                                            blurRadius = 8f
                                        )
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = buildAnnotatedString {
                                        append("Daar ki Maa ki... ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                                            append("Puja Karo")
                                        }
                                    },
                                    fontSize = 11.sp,
                                    color = GoldGold,
                                    letterSpacing = 1.sp
                                )
                            }
                            IconButton(
                                onClick = { isDrawerOpen = true },
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.White.copy(alpha = 0.05f), shape = CircleShape)
                            ) {
                                InfoIconRedesign(
                                    tint = ElectricBlue,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                PullToRefreshBox(
                    isRefreshing = isConfluenceRefreshing,
                    onRefresh = {
                        scope.launch {
                            isConfluenceRefreshing = true
                            viewModel.resetAllInputs()
                            delay(800)
                            isConfluenceRefreshing = false
                        }
                    },
                    state = confluencePullState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    val confluenceScaleY = 1f + (confluencePullState.distanceFraction * 0.12f).coerceAtMost(0.12f)
                    val confluenceTranslateY = confluencePullState.distanceFraction * 120f

                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                scaleY = confluenceScaleY
                                translationY = confluenceTranslateY
                                transformOrigin = androidx.compose.ui.graphics.TransformOrigin(0.5f, 0f)
                            }
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                // GIVE INPUTS section indicator
                item {
                    Text(
                        text = "GIVE INPUTS",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ElectricBlue,
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                    )
                }

            // HIGHER TIME FRAMES (HTF) SECTION
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ShowChart,
                                contentDescription = "HTF Icon",
                                tint = ElectricBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Higher Time Frames (HTF Trend)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Text(
                            text = "Need 2 consecutive",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TimeframeSelectorCard(
                            label = "1 Week",
                            activeState = tf1w,
                            onStateChanged = { viewModel.updateTf1w(it) },
                            modifier = Modifier.weight(1f)
                        )
                        TimeframeSelectorCard(
                            label = "1 Day",
                            activeState = tf1d,
                            onStateChanged = { viewModel.updateTf1d(it) },
                            modifier = Modifier.weight(1f)
                        )
                        TimeframeSelectorCard(
                            label = "4 Hour",
                            activeState = tf4h,
                            onStateChanged = { viewModel.updateTf4h(it) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // LOWER TIME FRAMES (LTF) SECTION
            item {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "LTF Icon",
                            tint = GoldGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Lower Time Frames (LTF Entries)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            letterSpacing = 0.5.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TimeframeSelectorCard(
                            label = "1 Hour",
                            activeState = tf1h,
                            onStateChanged = { viewModel.updateTf1h(it) },
                            modifier = Modifier.weight(1f)
                        )
                        TimeframeSelectorCard(
                            label = "30 Min",
                            activeState = tf30m,
                            onStateChanged = { viewModel.updateTf30m(it) },
                            modifier = Modifier.weight(1f)
                        )
                        TimeframeSelectorCard(
                            label = "15 Min",
                            activeState = tf15m,
                            onStateChanged = { viewModel.updateTf15m(it) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // AREA OF INTEREST (AOI) STATUS SECTION
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderDb),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Analytics,
                                contentDescription = "AOI Status Icon",
                                tint = ElectricBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "AREA OF INTEREST (AOI)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                letterSpacing = 0.5.sp
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        val isAtAoi = aoiTouches >= 3
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (isAtAoi) BullishGreen.copy(alpha = 0.12f) else Color.Transparent
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                width = 1.dp,
                                color = if (isAtAoi) BullishGreen else BorderDb
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .bounceClick {
                                    viewModel.updateAoiTouches(if (isAtAoi) 1 else 3)
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "AT AOI / REJECTED",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isAtAoi) TextPrimary else TextSecondary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = if (isAtAoi) 
                                            "Validated AOI zone (confirmed rejection at support/resistance)"
                                            else "Toggle if price action confirms rejection at a valid Area of Interest (3+ touches)",
                                        fontSize = 11.sp,
                                        color = if (isAtAoi) BullishGreen else TextMuted,
                                        lineHeight = 15.sp
                                    )
                                }
                                
                                Switch(
                                    checked = isAtAoi,
                                    onCheckedChange = { checked ->
                                        viewModel.updateAoiTouches(if (checked) 3 else 1)
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = BullishGreen,
                                        uncheckedThumbColor = TextMuted,
                                        uncheckedTrackColor = Color.Transparent,
                                        uncheckedBorderColor = BorderDb
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // ENTRY CONFIRMATIONS SECTION
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderDb),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShowChart,
                                contentDescription = "Confirmations Icon",
                                tint = GoldGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ENTRY CONFIRMATIONS",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                letterSpacing = 0.5.sp
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Category 1: Market Structure Dropdown
                        MultiSelectDropdown(
                            categoryTitle = "Market Structure",
                            options = listOf("BOS", "CHoCH", "BOS + Retest", "Break + Retest"),
                            selectedItems = selectedMarketStructure,
                            onToggleItem = { viewModel.toggleMarketStructure(it) },
                            isExpanded = marketStructureExpanded,
                            onExpandedChange = { marketStructureExpanded = it }
                        )
                        
                        Spacer(modifier = Modifier.height(14.dp))
                        
                        // Category 2: Candlestick Dropdown
                        MultiSelectDropdown(
                            categoryTitle = "Candlestick",
                            options = listOf(
                                "Engulfing", 
                                "Pin Bar", 
                                "Rejection Candle", 
                                "Inside Bar", 
                                "Hammer / Hanging Man", 
                                "Doji / Morning Star", 
                                "Harami Pattern", 
                                "Marubozu"
                            ),
                            selectedItems = selectedCandlesticks,
                            onToggleItem = { viewModel.toggleCandlestick(it) },
                            isExpanded = candlesticksExpanded,
                            onExpandedChange = { candlesticksExpanded = it }
                        )
                        
                        Spacer(modifier = Modifier.height(14.dp))
                        
                        // Category 3: Patterns Dropdown
                        MultiSelectDropdown(
                            categoryTitle = "Patterns",
                            options = listOf(
                                "Double Top", 
                                "Double Bottom", 
                                "Head & Shoulders", 
                                "Inverse Head & Shoulders", 
                                "Flag (Bull/Bear)", 
                                "Triangle (Sym/Asc/Desc)", 
                                "Wedge (Falling/Rising)", 
                                "Cup & Handle", 
                                "Quasimodo (QM)"
                            ),
                            selectedItems = selectedPatterns,
                            onToggleItem = { viewModel.togglePattern(it) },
                            isExpanded = patternsExpanded,
                            onExpandedChange = { patternsExpanded = it }
                        )
                    }
                }
            }

            // NEWS FILTER SECTION
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderDb),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "News Warning Icon",
                                tint = BearishRed,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "NEWS FILTER (Choose One)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                letterSpacing = 0.5.sp
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val newsOptions = listOf(
                                "NO_IMPACT" to ("No Impact" to Color.White),
                                "LOW" to ("Low Impact" to Color(0xFFFFEB3B)),
                                "MEDIUM" to ("Medium Impact" to Color(0xFFFF9800)),
                                "HIGH" to ("High Impact" to Color(0xFFE53935))
                            )
                            
                            newsOptions.forEach { (value, pair) ->
                                val (label, color) = pair
                                val isSelected = newsImpact == value
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) color.copy(alpha = 0.15f) else Color.Transparent
                                    ),
                                    border = androidx.compose.foundation.BorderStroke(
                                        width = 1.dp,
                                        color = if (isSelected) color else BorderDb
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .bounceClick { viewModel.updateNewsImpact(value) }
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 12.dp, horizontal = 2.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .background(color, shape = CircleShape)
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = label.substringBefore(" "),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) TextPrimary else TextSecondary,
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = label.substringAfter(" "),
                                            fontSize = 8.sp,
                                            color = if (isSelected) color else TextMuted,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // CALCULATE BUTTON
            item {
                Spacer(modifier = Modifier.height(12.dp))
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                val btnScale by animateFloatAsState(
                    targetValue = if (isPressed) 0.95f else 1f,
                    animationSpec = spring(stiffness = Spring.StiffnessLow),
                    label = "btn_press"
                )
                Button(
                    onClick = { showResultsPage = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ElectricBlue
                    ),
                    shape = RoundedCornerShape(12.dp),
                    interactionSource = interactionSource,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .graphicsLayer {
                            scaleX = btnScale
                            scaleY = btnScale
                        }
                        .testTag("calculate_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = "Calculate Confluence",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "CALCULATE STRIXA CONFLUENCE",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Designed & Built with ❤️ by ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = TextPrimary)) {
                                append("SRIJAN")
                            }
                        },
                        fontSize = 11.sp,
                        color = TextMuted,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
    }
}

    KeepAliveContainer(visible = selectedTab == "RISK_LAB") {
        RiskLabScreen(
            onOpenMenu = { isDrawerOpen = true },
            onScreenChanged = { isRiskLabCalculatorOpen = it }
        )
    }

    FloatingBottomNavigation(
        selectedTab = selectedTab,
        onTabSelected = { selectedTab = it },
        visible = if (selectedTab == "CONFLUENCE") isBottomBarVisible else !isRiskLabCalculatorOpen,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .navigationBarsPadding()
            .padding(bottom = 20.dp)
    )

    RightSidePanel(
        isOpen = isDrawerOpen,
        onClose = { isDrawerOpen = false }
    )

    AnimatedVisibility(
        visible = showBackExitToast,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 }),
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 100.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CardSurfaceDb.copy(alpha = 0.95f)),
            border = BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.6f)),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .padding(horizontal = 32.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Exit Warning",
                    tint = ElectricBlue,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Hit Back Once Again To Close The App",
                    color = TextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
}
}

@Composable
fun TimeframeSelectorCard(
    label: String,
    activeState: String, // "BULLISH", "BEARISH", "NEUTRAL"
    onStateChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = when (activeState) {
                "BULLISH" -> BullishGreen.copy(alpha = 0.08f)
                "BEARISH" -> BearishRed.copy(alpha = 0.08f)
                else -> CardSurfaceDb
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = when (activeState) {
                "BULLISH" -> BullishGreen.copy(alpha = 0.35f)
                "BEARISH" -> BearishRed.copy(alpha = 0.35f)
                else -> BorderDb
            }
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Large center indicator representing the state
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        color = when (activeState) {
                            "BULLISH" -> BullishGreen.copy(alpha = 0.12f)
                            "BEARISH" -> BearishRed.copy(alpha = 0.12f)
                            else -> BorderDb.copy(alpha = 0.4f)
                        },
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = when (activeState) {
                        "BULLISH" -> Icons.Default.ArrowUpward
                        "BEARISH" -> Icons.Default.ArrowDownward
                        else -> Icons.Default.TrendingFlat
                    },
                    contentDescription = activeState,
                    tint = when (activeState) {
                        "BULLISH" -> BullishGreen
                        "BEARISH" -> BearishRed
                        else -> TextSecondary
                    },
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // tactile 1-click tiny dot selection row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Bullish Dot Toggle
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(
                            if (activeState == "BULLISH") BullishGreen else Color.Transparent
                        )
                        .border(
                            1.dp,
                            if (activeState == "BULLISH") BullishGreen else BullishGreen.copy(alpha = 0.4f),
                            CircleShape
                        )
                        .bounceClick { onStateChanged("BULLISH") }
                )
                
                // Neutral Dot Toggle
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(
                            if (activeState == "NEUTRAL") TextSecondary else Color.Transparent
                        )
                        .border(
                            1.dp,
                            if (activeState == "NEUTRAL") TextSecondary else TextMuted,
                            CircleShape
                        )
                        .bounceClick { onStateChanged("NEUTRAL") }
                )

                // Bearish Dot Toggle
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(
                            if (activeState == "BEARISH") BearishRed else Color.Transparent
                        )
                        .border(
                            1.dp,
                            if (activeState == "BEARISH") BearishRed else BearishRed.copy(alpha = 0.4f),
                            CircleShape
                        )
                        .bounceClick { onStateChanged("BEARISH") }
                )
            }
        }
    }
}

@Composable
fun LoggedSetupCard(
    setup: ConfluenceSetup,
    onDelete: () -> Unit
) {
    val formattedDate = remember(setup.timestamp) {
        val sdf = SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault())
        sdf.format(Date(setup.timestamp))
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderDb),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Row 1: Symbol, Bias, Delete Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = setup.symbol,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                color = when (setup.htfBias) {
                                    "BULLISH" -> BullishGreen.copy(alpha = 0.15f)
                                    "BEARISH" -> BearishRed.copy(alpha = 0.15f)
                                    else -> TextMuted.copy(alpha = 0.15f)
                                },
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = setup.htfBias,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = when (setup.htfBias) {
                                "BULLISH" -> BullishGreen
                                "BEARISH" -> BearishRed
                                else -> TextSecondary
                            }
                        )
                    }
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Delete setup",
                        tint = TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Row 2: Confluence Rating & Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = setup.confluenceLevel,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when {
                        setup.confluenceLevel.contains("A+") -> GoldGold
                        setup.confluenceLevel.contains("A") -> BullishGreen
                        setup.confluenceLevel.contains("B") -> ElectricBlue
                        else -> TextSecondary
                    }
                )
                Text(
                    text = formattedDate,
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Matrix values layout (tiny visualization of checked states)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(6.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "1W", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                    MiniIndicatorIcon(state = setup.tf1w)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "1D", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                    MiniIndicatorIcon(state = setup.tf1d)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "4H", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                    MiniIndicatorIcon(state = setup.tf4h)
                }
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                        .background(BorderDb)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "1H", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                    MiniIndicatorIcon(state = setup.tf1h)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "30M", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                    MiniIndicatorIcon(state = setup.tf30m)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "15M", fontSize = 9.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                    MiniIndicatorIcon(state = setup.tf15m)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // AOI & News and Entry Confirmations Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // AOI status chip
                val aoiLabel = if (setup.aoiTouches >= 3) "At AOI / Rejected" else "Not At AOI"
                Box(
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.05f), shape = RoundedCornerShape(4.dp))
                        .border(1.dp, BorderDb, shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = aoiLabel,
                        fontSize = 10.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // News Impact Badge
                val newsColor = when (setup.newsImpact) {
                    "LOW" -> Color(0xFFFFEB3B)
                    "MEDIUM" -> Color(0xFFFF9800)
                    "HIGH" -> Color(0xFFE53935)
                    else -> Color.White
                }
                Box(
                    modifier = Modifier
                        .background(newsColor.copy(alpha = 0.12f), shape = RoundedCornerShape(4.dp))
                        .border(1.dp, newsColor.copy(alpha = 0.4f), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "NEWS: ${setup.newsImpact.replace("_", " ")}",
                        fontSize = 10.sp,
                        color = newsColor,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Entry confirmations score
                Text(
                    text = "Confirmations: ${setup.entryConfirmationScore}%",
                    fontSize = 10.sp,
                    color = ElectricBlue,
                    fontWeight = FontWeight.Bold
                )
            }

            // Subtitle with specific confirmations checked
            val allConfs = listOf(setup.marketStructureConfirmations, setup.candlestickConfirmations, setup.patternConfirmations)
                .filter { it.isNotBlank() }
                .joinToString(", ")
            if (allConfs.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Triggers: $allConfs",
                    fontSize = 11.sp,
                    color = TextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (setup.note.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Note: ${setup.note}",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun MiniIndicatorIcon(state: String) {
    Icon(
        imageVector = when (state) {
            "BULLISH" -> Icons.Default.ArrowUpward
            "BEARISH" -> Icons.Default.ArrowDownward
            else -> Icons.Default.TrendingFlat
        },
        contentDescription = state,
        tint = when (state) {
            "BULLISH" -> BullishGreen
            "BEARISH" -> BearishRed
            else -> TextMuted
        },
        modifier = Modifier.size(12.dp)
    )
}

@Composable
fun indicatorBorderBrush(result: LiveConfluenceResult): Brush {
    return if (result.probabilityPercentage >= 90) {
        Brush.linearGradient(listOf(GoldGold, ElectricBlue))
    } else {
        Brush.linearGradient(listOf(BullishGreen, ElectricBlue))
    }
}

@Composable
fun ConfirmationToggleChip(
    label: String,
    isChecked: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isChecked) ElectricBlue.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.2f)
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (isChecked) ElectricBlue else BorderDb
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.bounceClick { onToggle() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(if (isChecked) ElectricBlue else Color.Transparent)
                    .border(1.dp, if (isChecked) ElectricBlue else TextMuted, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Normal,
                color = if (isChecked) TextPrimary else TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Modifier.bounceClick(onClick: () -> Unit = {}): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "bounce"
    )
    return this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = interactionSource,
            indication = androidx.compose.foundation.LocalIndication.current,
            onClick = onClick
        )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.bounceClickWithLong(
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "bounce"
    )
    return this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .combinedClickable(
            interactionSource = interactionSource,
            indication = androidx.compose.foundation.LocalIndication.current,
            onClick = onClick,
            onLongClick = onLongClick
        )
}

@Composable
fun MultiSelectDropdown(
    categoryTitle: String,
    options: List<String>,
    selectedItems: Set<String>,
    onToggleItem: (String) -> Unit,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = categoryTitle,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextSecondary,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.25f), shape = RoundedCornerShape(10.dp))
                    .border(1.dp, BorderDb, shape = RoundedCornerShape(10.dp))
                    .bounceClick { onExpandedChange(!isExpanded) }
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val displayText = if (selectedItems.isEmpty()) {
                    "None Selected"
                } else {
                    selectedItems.joinToString(", ")
                }
                
                Text(
                    text = displayText,
                    fontSize = 13.sp,
                    color = if (selectedItems.isEmpty()) TextMuted else TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown icon",
                    tint = ElectricBlue,
                    modifier = Modifier
                        .size(20.dp)
                        .scale(1f, if (isExpanded) -1f else 1f)
                )
            }
            
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .background(CardSurfaceDb)
                    .border(1.dp, BorderDb, shape = RoundedCornerShape(8.dp))
            ) {
                options.forEach { option ->
                    val isChecked = selectedItems.contains(option)
                    DropdownMenuItem(
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = null,
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = ElectricBlue,
                                        uncheckedColor = TextSecondary,
                                        checkmarkColor = Color.White
                                    ),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = option,
                                    fontSize = 13.sp,
                                    color = TextPrimary
                                )
                            }
                        },
                        onClick = {
                            onToggleItem(option)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculationResultDialog(
    result: LiveConfluenceResult,
    onDismiss: () -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderDb),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "STRIXA REPORT",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ElectricBlue,
                        letterSpacing = 1.5.sp
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close report",
                            tint = TextMuted,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Custom Circular Progress Gauge/Dial for the score
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(130.dp)
                ) {
                    val animatedProbability by animateFloatAsState(
                        targetValue = result.probabilityPercentage.toFloat(),
                        animationSpec = tween(1000, easing = FastOutSlowInEasing),
                        label = "prob_dial_large"
                    )

                    val indicatorColor = when {
                        result.probabilityPercentage >= 90 -> GoldGold
                        result.probabilityPercentage >= 75 -> BullishGreen
                        result.probabilityPercentage >= 65 -> ElectricBlue
                        else -> BearishRed
                    }

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Background Track
                        drawArc(
                            color = BorderDb,
                            startAngle = -220f,
                            sweepAngle = 260f,
                            useCenter = false,
                            style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                        )
                        // Animated Arc
                        drawArc(
                            color = indicatorColor,
                            startAngle = -220f,
                            sweepAngle = (260f * (animatedProbability / 100f)),
                            useCenter = false,
                            style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${result.probabilityPercentage}%",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                        Text(
                            text = "STRIXA",
                            fontSize = 10.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Setup Class Badge
                Box(
                    modifier = Modifier
                        .background(
                            color = when {
                                result.probabilityPercentage >= 90 -> GoldGold.copy(alpha = 0.12f)
                                result.probabilityPercentage >= 75 -> BullishGreen.copy(alpha = 0.12f)
                                result.probabilityPercentage >= 65 -> ElectricBlue.copy(alpha = 0.12f)
                                else -> BearishRed.copy(alpha = 0.12f)
                            },
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = when {
                                result.probabilityPercentage >= 90 -> GoldGold.copy(alpha = 0.4f)
                                result.probabilityPercentage >= 75 -> BullishGreen.copy(alpha = 0.4f)
                                result.probabilityPercentage >= 65 -> ElectricBlue.copy(alpha = 0.4f)
                                else -> BearishRed.copy(alpha = 0.4f)
                            },
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = result.confluenceLevel.uppercase(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = when {
                            result.probabilityPercentage >= 90 -> GoldGold
                            result.probabilityPercentage >= 75 -> BullishGreen
                            result.probabilityPercentage >= 65 -> ElectricBlue
                            else -> BearishRed
                        },
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Market Bias Banner
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = when (result.htfBias) {
                                "BULLISH" -> BullishGreen.copy(alpha = 0.12f)
                                "BEARISH" -> BearishRed.copy(alpha = 0.12f)
                                else -> TextMuted.copy(alpha = 0.12f)
                            },
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = when (result.htfBias) {
                                "BULLISH" -> BullishGreen.copy(alpha = 0.3f)
                                "BEARISH" -> BearishRed.copy(alpha = 0.3f)
                                else -> BorderDb
                            },
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when (result.htfBias) {
                            "BULLISH" -> Icons.Default.TrendingUp
                            "BEARISH" -> Icons.Default.TrendingDown
                            else -> Icons.Default.TrendingFlat
                        },
                        contentDescription = "Bias",
                        tint = when (result.htfBias) {
                            "BULLISH" -> BullishGreen
                            "BEARISH" -> BearishRed
                            else -> TextSecondary
                        },
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "HTF MARKET BIAS",
                            fontSize = 10.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = when (result.htfBias) {
                                "BULLISH" -> "BULLISH BIAS (Buy Confluence)"
                                "BEARISH" -> "BEARISH BIAS (Sell Confluence)"
                                else -> "NEUTRAL BIAS (Stand Aside)"
                            },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = when (result.htfBias) {
                                "BULLISH" -> BullishGreen
                                "BEARISH" -> BearishRed
                                else -> TextPrimary
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Detailed Analysis Explainer (The "Why")
                Text(
                    text = "ANALYSIS EXPLAINER & CHECKLIST",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    modifier = Modifier.align(Alignment.Start),
                    letterSpacing = 1.sp
                )
                
                Spacer(modifier = Modifier.height(6.dp))
                
                Text(
                    text = result.description,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = TextSecondary,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Analysis bullet list container
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    result.details.forEach { detail ->
                        Row(verticalAlignment = Alignment.Top) {
                            Text(
                                text = "•",
                                fontSize = 12.sp,
                                color = ElectricBlue,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = detail,
                                fontSize = 11.sp,
                                color = TextPrimary,
                                lineHeight = 15.sp
                            )
                        }
                    }
                    
                    // Always show Entry confirmations score details
                    Row(verticalAlignment = Alignment.Top) {
                        Text(
                            text = "•",
                            fontSize = 12.sp,
                            color = ElectricBlue,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "🎯 Trigger/Confirmation Patterns Score: ${result.entryConfirmationScore}/100",
                            fontSize = 11.sp,
                            color = TextPrimary,
                            lineHeight = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Close Button
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "DISMISS REPORT",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ConfluenceResultsPage(
    viewModel: ConfluenceViewModel,
    onBack: () -> Unit
) {
    val result by viewModel.liveResult.collectAsState()
    val aiSummaryState by viewModel.aiSummaryState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.generateAiSummary(
            symbol = viewModel.symbol.value,
            htfBias = result.htfBias,
            tf1w = viewModel.tf1w.value,
            tf1d = viewModel.tf1d.value,
            tf4h = viewModel.tf4h.value,
            tf1h = viewModel.tf1h.value,
            tf30m = viewModel.tf30m.value,
            tf15m = viewModel.tf15m.value,
            marketStructure = viewModel.selectedMarketStructure.value.joinToString(", "),
            candlesticks = viewModel.selectedCandlesticks.value.joinToString(", "),
            patterns = viewModel.selectedPatterns.value.joinToString(", "),
            newsImpact = viewModel.newsImpact.value,
            confluenceLevel = result.confluenceLevel,
            probabilityPercentage = result.probabilityPercentage,
            entryScore = result.entryConfirmationScore
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepSpaceDb),
        containerColor = DeepSpaceDb
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with Back Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.05f), shape = CircleShape)
                        .testTag("results_back_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back to Calculator",
                        tint = TextPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "STRIXA REPORT",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = ElectricBlue,
                    letterSpacing = 2.sp
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Gauge Card
            Card(
                colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderDb),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Custom Circular Progress Gauge/Dial for the score
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(160.dp)
                    ) {
                        val animatedProbability by animateFloatAsState(
                            targetValue = result.probabilityPercentage.toFloat(),
                            animationSpec = tween(1000, easing = FastOutSlowInEasing),
                            label = "prob_dial_large_page"
                        )

                        val indicatorColor = when {
                            result.probabilityPercentage >= 90 -> GoldGold
                            result.probabilityPercentage >= 75 -> BullishGreen
                            result.probabilityPercentage >= 65 -> ElectricBlue
                            else -> BearishRed
                        }

                        Canvas(modifier = Modifier.fillMaxSize()) {
                            // Background Track
                            drawArc(
                                color = BorderDb,
                                startAngle = -220f,
                                sweepAngle = 260f,
                                useCenter = false,
                                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                            )
                            // Animated Arc
                            drawArc(
                                color = indicatorColor,
                                startAngle = -220f,
                                sweepAngle = (260f * (animatedProbability / 100f)),
                                useCenter = false,
                                style = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round)
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${result.probabilityPercentage}%",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Black,
                                color = TextPrimary
                            )
                            Text(
                                text = "PROBABILITY",
                                fontSize = 11.sp,
                                color = TextSecondary,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.5.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Setup Class Badge
                    Box(
                        modifier = Modifier
                            .background(
                                color = when {
                                    result.probabilityPercentage >= 90 -> GoldGold.copy(alpha = 0.12f)
                                    result.probabilityPercentage >= 75 -> BullishGreen.copy(alpha = 0.12f)
                                    result.probabilityPercentage >= 65 -> ElectricBlue.copy(alpha = 0.12f)
                                    else -> BearishRed.copy(alpha = 0.12f)
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = when {
                                    result.probabilityPercentage >= 90 -> GoldGold.copy(alpha = 0.4f)
                                    result.probabilityPercentage >= 75 -> BullishGreen.copy(alpha = 0.4f)
                                    result.probabilityPercentage >= 65 -> ElectricBlue.copy(alpha = 0.4f)
                                    else -> BearishRed.copy(alpha = 0.4f)
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = result.confluenceLevel.uppercase(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = when {
                                result.probabilityPercentage >= 90 -> GoldGold
                                result.probabilityPercentage >= 75 -> BullishGreen
                                result.probabilityPercentage >= 65 -> ElectricBlue
                                else -> BearishRed
                            },
                            letterSpacing = 1.5.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Market Bias Banner
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = when (result.htfBias) {
                            "BULLISH" -> BullishGreen.copy(alpha = 0.12f)
                            "BEARISH" -> BearishRed.copy(alpha = 0.12f)
                            else -> TextMuted.copy(alpha = 0.12f)
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = when (result.htfBias) {
                            "BULLISH" -> BullishGreen.copy(alpha = 0.3f)
                            "BEARISH" -> BearishRed.copy(alpha = 0.3f)
                            else -> BorderDb
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (result.htfBias) {
                        "BULLISH" -> Icons.Default.TrendingUp
                        "BEARISH" -> Icons.Default.TrendingDown
                        else -> Icons.Default.TrendingFlat
                    },
                    contentDescription = "Bias",
                    tint = when (result.htfBias) {
                        "BULLISH" -> BullishGreen
                        "BEARISH" -> BearishRed
                        else -> TextSecondary
                    },
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "HTF MARKET BIAS",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = when (result.htfBias) {
                            "BULLISH" -> "BULLISH BIAS (Buy Confluence Setup)"
                            "BEARISH" -> "BEARISH BIAS (Sell Confluence Setup)"
                            else -> "NEUTRAL BIAS (Stand Aside)"
                        },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (result.htfBias) {
                            "BULLISH" -> BullishGreen
                            "BEARISH" -> BearishRed
                            else -> TextPrimary
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // AI Trade Analysis Card
            Card(
                colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderDb),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "AI Analysis",
                                tint = GoldGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "STRIXA AI SUMMARY",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                letterSpacing = 1.sp
                            )
                        }
                        
                        // Regenerate Button
                        IconButton(
                            onClick = {
                                viewModel.generateAiSummary(
                                    symbol = viewModel.symbol.value,
                                    htfBias = result.htfBias,
                                    tf1w = viewModel.tf1w.value,
                                    tf1d = viewModel.tf1d.value,
                                    tf4h = viewModel.tf4h.value,
                                    tf1h = viewModel.tf1h.value,
                                    tf30m = viewModel.tf30m.value,
                                    tf15m = viewModel.tf15m.value,
                                    marketStructure = viewModel.selectedMarketStructure.value.joinToString(", "),
                                    candlesticks = viewModel.selectedCandlesticks.value.joinToString(", "),
                                    patterns = viewModel.selectedPatterns.value.joinToString(", "),
                                    newsImpact = viewModel.newsImpact.value,
                                    confluenceLevel = result.confluenceLevel,
                                    probabilityPercentage = result.probabilityPercentage,
                                    entryScore = result.entryConfirmationScore
                                )
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Regenerate AI analysis",
                                tint = ElectricBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    when (val state = aiSummaryState) {
                        is AiSummaryState.Idle -> {
                            Text(
                                text = "Preparing STRIXA AI analysis...",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                        is AiSummaryState.Loading -> {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    color = ElectricBlue,
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "STRIXA AI is analyzing market structures and pattern confirmations...",
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        is AiSummaryState.Success -> {
                            Text(
                                text = state.summary,
                                fontSize = 13.sp,
                                lineHeight = 19.sp,
                                color = TextPrimary,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        is AiSummaryState.Error -> {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ErrorOutline,
                                    contentDescription = "Error",
                                    tint = BearishRed,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = state.message,
                                    fontSize = 12.sp,
                                    color = BearishRed,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Explainer Checklist Card
            Card(
                colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderDb),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "ANALYSIS EXPLAINER & CHECKLIST",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        letterSpacing = 1.sp
                    )
                    
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Text(
                        text = result.description,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Analysis bullet list container
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        result.details.forEach { detail ->
                            Row(verticalAlignment = Alignment.Top) {
                                Text(
                                    text = "•",
                                    fontSize = 14.sp,
                                    color = ElectricBlue,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = detail,
                                    fontSize = 12.sp,
                                    color = TextPrimary,
                                    lineHeight = 17.sp
                                )
                            }
                        }
                        
                        // Always show Entry confirmations score details
                        Row(verticalAlignment = Alignment.Top) {
                            Text(
                                text = "•",
                                fontSize = 14.sp,
                                color = ElectricBlue,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = "🎯 Trigger/Confirmation Patterns Score: ${result.entryConfirmationScore}/100",
                                fontSize = 12.sp,
                                color = TextPrimary,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Back Button at the bottom
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "BACK TO CALCULATOR",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Build with ❤️ by SRIJAN
            Text(
                text = buildAnnotatedString {
                    append("Designed & Built with ❤️ by ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = TextPrimary)) {
                        append("SRIJAN")
                    }
                },
                fontSize = 11.sp,
                color = TextMuted,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun RightSidePanel(
    isOpen: Boolean,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf("MENU") }

    // Reset screen to MENU when drawer closes
    LaunchedEffect(isOpen) {
        if (!isOpen) {
            currentScreen = "MENU"
        }
    }

    BackHandler(enabled = isOpen) {
        if (currentScreen != "MENU") {
            currentScreen = "MENU"
        } else {
            onClose()
        }
    }

    val panelAlpha = if (currentScreen == "MENU") 0.92f else 1.0f

    AnimatedVisibility(
        visible = isOpen,
        enter = androidx.compose.animation.slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeIn(),
        exit = androidx.compose.animation.slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeOut(),
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(onClick = onClose)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .fillMaxWidth(0.85f)
                    .background(
                        CardSurfaceDb.copy(alpha = panelAlpha),
                        shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp)
                    )
                    .border(
                        1.dp,
                        BorderDb.copy(alpha = panelAlpha),
                        shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp)
                    )
                    .clip(RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp))
                    .clickable(enabled = false) {}
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
            ) {
                // Pin the Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (currentScreen != "MENU") {
                            IconButton(
                                onClick = { currentScreen = "MENU" },
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(36.dp)
                                    .background(Color.White.copy(alpha = 0.05f), shape = CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back to menu",
                                    tint = TextPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        
                        Text(
                            text = when (currentScreen) {
                                "MENU" -> "STRIXA INFO"
                                "HOW_TO_USE" -> "HOW TO USE"
                                "DISCLAIMER" -> "USAGE & DISCLAIMER"
                                "ABOUT_STRIXA_CONFLUENCE" -> "ABOUT STRIXA CONFLUENCE"
                                "ABOUT_RISK_LAB" -> "ABOUT RISK LAB"
                                "FOR_TRADERS" -> "FOR TRADERS"
                                else -> "INFO PANEL"
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ElectricBlue,
                            letterSpacing = 1.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.05f), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close panel",
                            tint = TextPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Scrollable Body Content with Beautiful Animated Transitions
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = {
                            if (targetState != "MENU") {
                                // Transition forward: subpage slides in from the right, menu slides out to the left
                                (slideInHorizontally(
                                    initialOffsetX = { it },
                                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                                ) + fadeIn(animationSpec = tween(300))).togetherWith(
                                    slideOutHorizontally(
                                        targetOffsetX = { -it / 2 },
                                        animationSpec = tween(300, easing = FastOutSlowInEasing)
                                    ) + fadeOut(animationSpec = tween(300))
                                )
                            } else {
                                // Transition backward: menu slides in from the left, subpage slides out to the right
                                (slideInHorizontally(
                                    initialOffsetX = { -it / 2 },
                                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                                ) + fadeIn(animationSpec = tween(300))).togetherWith(
                                    slideOutHorizontally(
                                        targetOffsetX = { it },
                                        animationSpec = tween(300, easing = FastOutSlowInEasing)
                                    ) + fadeOut(animationSpec = tween(300))
                                )
                            }
                        },
                        label = "DrawerScreenTransitions"
                    ) { targetScreen ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            when (targetScreen) {
                                "MENU" -> {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Text(
                                            text = "Select a section below to view detailed guides and calculation principles.",
                                            fontSize = 12.sp,
                                            color = TextSecondary,
                                            modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
                                        )

                                        MenuRow(
                                            icon = "❓",
                                            title = "How to Use",
                                            subtitle = "Learn the core workflow of STRIXA.",
                                            onClick = { currentScreen = "HOW_TO_USE" },
                                            accentColor = ElectricBlue
                                        )
                                        MenuRow(
                                            icon = "⚠️",
                                            title = "Usage & Disclaimer",
                                            subtitle = "Risk warnings and offline behavior.",
                                            onClick = { currentScreen = "DISCLAIMER" },
                                            accentColor = BearishRed
                                        )
                                        MenuRow(
                                            icon = "🧮",
                                            title = "About Strixa Confluence",
                                            subtitle = "Deep-dive technical scoring metrics.",
                                            onClick = { currentScreen = "ABOUT_STRIXA_CONFLUENCE" },
                                            accentColor = GoldGold
                                        )
                                        MenuRow(
                                            icon = "ℹ️",
                                            title = "About Risk Lab",
                                            subtitle = "Principles of safe capital sizing.",
                                            onClick = { currentScreen = "ABOUT_RISK_LAB" },
                                            accentColor = BullishGreen
                                        )
                                        MenuRow(
                                            icon = "🔥",
                                            title = "For Traders",
                                            subtitle = "Psychological models and control keys.",
                                            onClick = { currentScreen = "FOR_TRADERS" },
                                            accentColor = Color(0xFFFF9800)
                                        )
                                    }
                                }
                                "HOW_TO_USE" -> {
                                    HowToUsePage()
                                }
                                "DISCLAIMER" -> {
                                    UsageDisclaimerPage()
                                }
                                "ABOUT_STRIXA_CONFLUENCE" -> {
                                    AboutStrixaConfluencePage()
                                }
                                "ABOUT_RISK_LAB" -> {
                                    AboutRiskLabPage()
                                }
                                "FOR_TRADERS" -> {
                                    ForTradersPage()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MenuRow(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    accentColor: Color = ElectricBlue
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.02f), shape = RoundedCornerShape(12.dp))
            .border(1.dp, BorderDb, shape = RoundedCornerShape(12.dp))
            .bounceClick(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            // Sleek Left Accent Line
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(26.dp)
                    .background(accentColor, shape = RoundedCornerShape(2.dp))
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = icon,
                fontSize = 18.sp
            )
            
            Spacer(modifier = Modifier.width(10.dp))
            
            Column {
                Text(
                    text = title.uppercase(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = TextMuted,
                    lineHeight = 13.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = "›",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextSecondary
        )
    }
}

@Composable
fun AboutStrixaConfluencePage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Discover how Strixa Confluence analyzes complex technical matrices to derive a high-probability consensus score.",
            fontSize = 12.sp,
            color = TextSecondary,
            lineHeight = 16.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        // Section 1: How it Works
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.02f), shape = RoundedCornerShape(12.dp))
                .border(1.dp, BorderDb, shape = RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Text(
                text = "⚡ CONFLUENCE ALIGNMENT CORE",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = ElectricBlue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "The system evaluates multiple independent technical streams (trends, levels, candlesticks, patterns) and weights them proportionally. Setup conviction is graded as follows:",
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            val items = listOf(
                "🟢 High Confluence (75% - 100%): Strong multi-timeframe directional agreement. High-probability entry zones.",
                "🟡 Moderate Confluence (50% - 74%): Partial alignment. Requires strict entry confirmation or smaller position size.",
                "🔴 Low Confluence (< 50%): Indecisive structures, opposing trends, or poor local validation. High risk of fakeouts."
            )
            items.forEach { item ->
                Text(
                    text = item,
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // Section 2: Confluence Metrics
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.02f), shape = RoundedCornerShape(12.dp))
                .border(1.dp, BorderDb, shape = RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Text(
                text = "📊 CRITICAL MATRIX VECTORS",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = GoldGold
            )
            Spacer(modifier = Modifier.height(8.dp))
            val vectors = listOf(
                "• Trend Alignment: Validating synchronized directions across High Timeframes (HTF) and Execution Timeframes (LTF).",
                "• Key Structure: Plotting entries strictly near major Support/Resistance lines, pivots, or psychological zones.",
                "• Candlestick Actions: Looking for immediate momentum triggers (Engulfing patterns, Hammers, Pinbars) at key zones.",
                "• Technical Indicators: Cross-referencing oversold/overbought oscillations and moving average crossovers for extra verification."
            )
            vectors.forEach { vec ->
                Text(
                    text = vec,
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(vertical = 3.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        DesignedAndBuiltChip()
    }
}

@Composable
fun DesignedAndBuiltChip(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(CardSurfaceDb, shape = RoundedCornerShape(10.dp))
            .border(1.dp, BorderDb, shape = RoundedCornerShape(10.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                append("Designed & Built with ❤️ by ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = TextPrimary)) {
                    append("SRIJAN")
                }
            },
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary
        )
        Text(
            text = "Version 2.0.1",
            fontSize = 10.sp,
            color = TextMuted
        )
    }
}

@Composable
fun InfoIconRedesign(
    modifier: Modifier = Modifier,
    tint: Color = ElectricBlue
) {
    Canvas(modifier = modifier.size(24.dp)) {
        val strokeWidthPx = 2.dp.toPx()
        
        // Upper bar (Full width)
        drawLine(
            color = tint,
            start = Offset(4.dp.toPx(), 6.dp.toPx()),
            end = Offset(20.dp.toPx(), 6.dp.toPx()),
            strokeWidth = strokeWidthPx,
            cap = androidx.compose.ui.graphics.StrokeCap.Round
        )
        
        // Middle bar (Slightly smaller, aligned to the right)
        drawLine(
            color = tint,
            start = Offset(9.dp.toPx(), 12.dp.toPx()),
            end = Offset(20.dp.toPx(), 12.dp.toPx()),
            strokeWidth = strokeWidthPx,
            cap = androidx.compose.ui.graphics.StrokeCap.Round
        )
        
        // Lower bar (Even smaller, aligned to the right)
        drawLine(
            color = tint,
            start = Offset(14.dp.toPx(), 18.dp.toPx()),
            end = Offset(20.dp.toPx(), 18.dp.toPx()),
            strokeWidth = strokeWidthPx,
            cap = androidx.compose.ui.graphics.StrokeCap.Round
        )
    }
}

@Composable
fun HowToUsePage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Follow this recommended workflow to leverage the power of STRIXA for consistent trading results:",
            fontSize = 12.sp,
            color = TextSecondary,
            lineHeight = 18.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        val steps = listOf(
            "1. Open Confluence." to "Navigate to the main analyzer screen of the app.",
            "2. Complete all Confluence inputs." to "Enter the trend details, select structures, candlesticks, and chart patterns.",
            "3. Press Analyze." to "Trigger STRIXA to evaluate alignment score and generate a probability.",
            "4. Review the STRIXA analysis." to "Read the synthesized technical brief summary and review TF alignment requirements.",
            "5. Open Risk Lab." to "Switch over to the calculator suite to calculate the math for execution.",
            "6. Use the appropriate calculator." to "Select Position Size, Profit, or Pip Calculator for accurate parameters."
        )

        steps.forEach { (title, desc) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.02f), shape = RoundedCornerShape(10.dp))
                    .border(1.dp, BorderDb, shape = RoundedCornerShape(10.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(ElectricBlue.copy(alpha = 0.1f), shape = CircleShape)
                        .border(1.dp, ElectricBlue.copy(alpha = 0.3f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title.substringBefore("."),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricBlue
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title.substringAfter(". ").trim(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = desc,
                        fontSize = 11.sp,
                        color = TextSecondary,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun UsageDisclaimerPage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BearishRed.copy(alpha = 0.05f), shape = RoundedCornerShape(12.dp))
                .border(1.dp, BearishRed.copy(alpha = 0.25f), shape = RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Warning icon",
                    tint = BearishRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "RISK WARNING & TERMS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BearishRed,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Trading financial markets involves high risk. Read the following usage rules carefully before continuing.",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        val points = listOf(
            "STRIXA is designed to assist with trade planning and risk management.",
            "STRIXA does not provide financial advice or guaranteed trading signals.",
            "All calculations are estimates.",
            "Market conditions, spreads, commissions, swaps, and slippage may affect actual trading results.",
            "Always verify every value before placing a live trade.",
            "Users are fully responsible for their own trading decisions and results."
        )

        points.forEach { point ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "•",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = ElectricBlue,
                    modifier = Modifier.width(16.dp)
                )
                Text(
                    text = point,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun AboutRiskLabPage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Risk Lab is a collection of practical calculators designed to help traders calculate position size, estimate profit, and manage risk before entering a trade.",
            fontSize = 12.sp,
            color = TextSecondary,
            lineHeight = 18.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        Text(
            text = "Risk Lab is intended to improve discipline and consistency as part of the STRIXA workflow.",
            fontSize = 12.sp,
            color = TextSecondary,
            lineHeight = 18.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(ElectricBlue.copy(alpha = 0.05f), shape = RoundedCornerShape(12.dp))
                .border(1.dp, ElectricBlue.copy(alpha = 0.25f), shape = RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Column {
                Text(
                    text = "🏆 DISCIPLINED RISK RULES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ElectricBlue,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Consistent traders prioritize risk protection over potential gains. Use Risk Lab on every single setup to lock in proper position sizing and keep portfolio drawdown minimal.",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        DesignedAndBuiltChip()
    }
}

@Composable
fun ForTradersPage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        
        // Brand Badge
        StrixaLogo(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, ElectricBlue.copy(alpha = 0.3f), shape = RoundedCornerShape(16.dp))
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "FOR TRADERS",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = TextPrimary,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Master Your Mind. Protect Your Capital.",
                fontSize = 11.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderDb))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val rules = listOf(
                "🧠 Emotional Control" to "The market is a mirror of your emotions. Do not let greed drive your entries or fear dictate your exits. Trade the plan, not the feeling.",
                "🧘 Stress Management" to "Overtrading is the main source of trading anxiety. If you feel stressed, step away from the charts. High-quality setups do not require force.",
                "🛡️ Capital Protection" to "Capital preservation is the ultimate edge. Your main job is not to make money, but to keep your losses small so you can trade another day."
            )

            rules.forEach { (title, desc) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.02f), shape = RoundedCornerShape(10.dp))
                        .border(1.dp, BorderDb, shape = RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = desc,
                        fontSize = 11.sp,
                        color = TextSecondary,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        DesignedAndBuiltChip()
    }
}

// Brand-New Robust KeepAliveContainer for State Preservation
@Composable
fun KeepAliveContainer(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
        label = "tab_alpha"
    )
    val animatedScale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.95f,
        animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
        label = "tab_scale"
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .then(
                if (animatedAlpha > 0.01f) {
                    Modifier
                } else {
                    Modifier.absoluteOffset(x = 10000.dp)
                }
            )
            .graphicsLayer {
                alpha = animatedAlpha
                scaleX = animatedScale
                scaleY = animatedScale
            }
    ) {
        content()
    }
}

// Custom HorizontalDivider to avoid Material 3 Divider import problems
@Composable
fun HorizontalDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(BorderDb)
    )
}

// Reusable custom input field styled specifically for Strixa theme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StrixaInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextSecondary, fontSize = 12.sp) },
        placeholder = { Text(placeholder, color = TextMuted, fontSize = 11.sp) },
        leadingIcon = leadingIcon?.let {
            { Text(text = it, fontSize = 15.sp, modifier = Modifier.padding(start = 12.dp)) }
        },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedContainerColor = Color.Black.copy(alpha = 0.2f),
            unfocusedContainerColor = Color.Black.copy(alpha = 0.2f),
            disabledContainerColor = Color.Black.copy(alpha = 0.2f),
            focusedBorderColor = ElectricBlue,
            unfocusedBorderColor = BorderDb,
            cursorColor = ElectricBlue
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth()
    )
}

// Custom Floating Bottom Navigation Bar matching the dark cosmic theme
@Composable
fun FloatingBottomNavigation(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(250, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(250)),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(250, easing = FastOutSlowInEasing)
        ) + fadeOut(animationSpec = tween(250)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .width(280.dp)
                .height(64.dp)
                .background(
                    color = CardSurfaceDb.copy(alpha = 0.94f),
                    shape = RoundedCornerShape(32.dp)
                )
                .border(
                    width = 1.dp,
                    color = BorderDb,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tabs = listOf(
                Triple("CONFLUENCE", "STRIXA CONFLUENCE", "⚡"),
                Triple("RISK_LAB", "STRIXA RISK LAB", "📊")
            )
            tabs.forEach { (tabId, label, emoji) ->
                val isSelected = selectedTab == tabId
                val animatedScale by animateFloatAsState(
                    targetValue = if (isSelected) 1.05f else 0.95f,
                    animationSpec = tween(200),
                    label = "scale"
                )
                val animatedAlpha by animateFloatAsState(
                    targetValue = if (isSelected) 1.0f else 0.6f,
                    animationSpec = tween(200),
                    label = "alpha"
                )

                val tabInteractionSource = remember { MutableInteractionSource() }
                val isPressed by tabInteractionSource.collectIsPressedAsState()
                val pressScale by animateFloatAsState(
                    targetValue = if (isPressed) 0.90f else 1.0f,
                    animationSpec = spring(stiffness = Spring.StiffnessLow),
                    label = "tab_press"
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = tabInteractionSource,
                            indication = null
                        ) { onTabSelected(tabId) }
                        .graphicsLayer {
                            scaleX = animatedScale * pressScale
                            scaleY = animatedScale * pressScale
                            alpha = animatedAlpha
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = emoji,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) ElectricBlue else TextSecondary
                    )
                }
            }
        }
    }
}

// Risk Lab Main Switcher Screen
@Composable
fun RiskLabScreen(
    onOpenMenu: () -> Unit,
    onScreenChanged: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var riskLabScreen by remember { mutableStateOf("HOME") }

    LaunchedEffect(riskLabScreen) {
        onScreenChanged(riskLabScreen != "HOME")
    }

    BackHandler(enabled = riskLabScreen != "HOME") {
        riskLabScreen = "HOME"
    }

    AnimatedContent(
        targetState = riskLabScreen,
        transitionSpec = {
            if (targetState != "HOME") {
                (slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(250)) + fadeIn()).togetherWith(
                    slideOutHorizontally(targetOffsetX = { -it / 2 }, animationSpec = tween(250)) + fadeOut()
                )
            } else {
                (slideInHorizontally(initialOffsetX = { -it / 2 }, animationSpec = tween(250)) + fadeIn()).togetherWith(
                    slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(250)) + fadeOut()
                )
            }
        },
        label = "RiskLabTransitions"
    ) { screenState ->
        when (screenState) {
            "HOME" -> {
                RiskLabHomeScreen(
                    onOpenMenu = onOpenMenu,
                    onNavigateToCalculator = { riskLabScreen = it }
                )
            }
            "POSITION_SIZE" -> {
                PositionSizeCalculatorScreen(
                    onBack = { riskLabScreen = "HOME" }
                )
            }
            "PROFIT" -> {
                ProfitCalculatorScreen(
                    onBack = { riskLabScreen = "HOME" }
                )
            }
            "PROFIT_BY_PIPS" -> {
                ProfitByPipsScreen(
                    onBack = { riskLabScreen = "HOME" }
                )
            }
            "STANDARD_CALC" -> {
                StandardCalculatorScreen(
                    onBack = { riskLabScreen = "HOME" }
                )
            }
            "CURRENCY_CALC" -> {
                CurrencyCalculatorScreen(
                    onBack = { riskLabScreen = "HOME" }
                )
            }
        }
    }
}

// Risk Lab Home Menu
@Composable
fun RiskLabHomeScreen(
    onOpenMenu: () -> Unit,
    onNavigateToCalculator: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepSpaceDb),
        containerColor = DeepSpaceDb,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardSurfaceDb)
                    .border(width = 1.dp, color = BorderDb)
                    .statusBarsPadding()
                    .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "STRIXA RISK LAB",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.5.sp,
                            color = TextPrimary,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = ElectricBlue.copy(alpha = 0.6f),
                                    offset = Offset(2f, 2f),
                                    blurRadius = 8f
                                )
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Risk Se Ishq Karlo",
                            fontSize = 11.sp,
                            color = GoldGold,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    IconButton(
                        onClick = onOpenMenu,
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White.copy(alpha = 0.05f), shape = CircleShape)
                    ) {
                        InfoIconRedesign(
                            tint = ElectricBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "CALCULATOR SUITE",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = ElectricBlue,
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            CalculatorMenuCard(
                title = "1. Position Size Calculator",
                description = "Calculate the precise trade size (lots) matching your custom risk tolerance.",
                emoji = "📊",
                highlightColor = ElectricBlue,
                onClick = { onNavigateToCalculator("POSITION_SIZE") }
            )

            CalculatorMenuCard(
                title = "2. Profit by Pips",
                description = "Compute the financial outcomes of projected pip moves for different lot sizes.",
                emoji = "🎯",
                highlightColor = GoldGold,
                onClick = { onNavigateToCalculator("PROFIT_BY_PIPS") }
            )

            CalculatorMenuCard(
                title = "3. Profit by Risk to Reward (RR)",
                description = "Estimate your exact dollar gains and losses based on Risk-to-Reward ratio.",
                emoji = "💰",
                highlightColor = BullishGreen,
                onClick = { onNavigateToCalculator("PROFIT") }
            )

            CalculatorMenuCard(
                title = "4. Standard Calculator",
                description = "A clean standard mathematical calculator with percentages for quick general math.",
                emoji = "🧮",
                highlightColor = ElectricBlue,
                onClick = { onNavigateToCalculator("STANDARD_CALC") }
            )

            CalculatorMenuCard(
                title = "5. Currency Converter",
                description = "Real-time global currency rates with interactive vice-versa conversion features.",
                emoji = "💵",
                highlightColor = GoldGold,
                onClick = { onNavigateToCalculator("CURRENCY_CALC") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Designed & Built with ❤️ by ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = TextPrimary)) {
                            append("SRIJAN")
                        }
                    },
                    fontSize = 11.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
fun CalculatorMenuCard(
    title: String,
    description: String,
    emoji: String,
    highlightColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick(onClick = onClick)
            .border(width = 1.dp, color = BorderDb, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(highlightColor.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))
                    .border(width = 1.dp, color = highlightColor.copy(alpha = 0.3f), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emoji,
                    fontSize = 26.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 15.sp
                )
            }
            Text(
                text = "›",
                fontSize = 24.sp,
                color = TextSecondary.copy(alpha = 0.6f),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Calculation Data Holder
data class PositionSizeResult(
    val riskAmount: Double,
    val pips: Double,
    val lots: Double,
    val error: String
)

data class ForexPair(
    val symbol: String,       // e.g. "EUR/USD"
    val displayName: String,  // e.g. "EUR/USD - Euro / US Dollar"
    val pipSize: Double,      // e.g. 0.0001
    val baseValue: Double     // standard multiplier
)

data class ProfitResult(
    val riskAmount: Double,
    val potentialProfit: Double,
    val potentialLoss: Double
)

data class ProfitByPipsResult(
    val pipValue: Double,
    val estimatedProfitLoss: Double
)

// Sizing calculation formulas
fun calculatePositionSize(
    balance: Double,
    riskVal: Double,
    isRiskPercent: Boolean,
    entryPrice: Double,
    stopLossPips: Double?,
    stopLossPrice: Double?,
    selectedPair: ForexPair
): PositionSizeResult {
    val riskAmt = if (isRiskPercent) {
        balance * (riskVal / 100.0)
    } else {
        riskVal
    }

    val pipSize = selectedPair.pipSize

    val pips = if (stopLossPips != null) {
        stopLossPips
    } else if (stopLossPrice != null) {
        val distance = Math.abs(entryPrice - stopLossPrice)
        if (distance <= 0.0) {
            return PositionSizeResult(0.0, 0.0, 0.0, "Entry Price and Stop Loss Price must be different.")
        }
        distance / pipSize
    } else {
        return PositionSizeResult(0.0, 0.0, 0.0, "Stop Loss input value not detected.")
    }

    if (pips <= 0.0) {
        return PositionSizeResult(0.0, 0.0, 0.0, "Calculated Stop Loss distance must be greater than zero.")
    }

    // Dynamic pip value per Standard Lot (100,000 units) in USD base currency
    val pipValuePerLot = when (selectedPair.symbol) {
        "EUR/USD", "GBP/USD", "AUD/USD", "NZD/USD" -> {
            10.0 // Standard major quote in USD
        }
        "USD/JPY", "CAD/JPY", "CHF/JPY", "NZD/JPY", "EUR/JPY", "GBP/JPY", "AUD/JPY" -> {
            // 1 pip = 0.01 on 100,000 JPY contract = 1,000 JPY
            // convert to USD: 1,000 / USDJPY_rate
            if (selectedPair.symbol == "USD/JPY" && entryPrice > 0.0) 1000.0 / entryPrice else 6.45
        }
        "USD/CAD", "GBP/CAD", "EUR/CAD", "AUD/CAD" -> {
            // 1 pip = 0.0001 on 100,000 CAD contract = 10 CAD
            // convert to USD: 10 / USDCAD_rate
            if (selectedPair.symbol == "USD/CAD" && entryPrice > 0.0) 10.0 / entryPrice else 7.30
        }
        "USD/CHF", "GBP/CHF", "EUR/CHF" -> {
            // 1 pip = 0.0001 on 100,000 CHF contract = 10 CHF
            // convert to USD: 10 / USDCHF_rate
            if (selectedPair.symbol == "USD/CHF" && entryPrice > 0.0) 10.0 / entryPrice else 11.0
        }
        "EUR/GBP" -> {
            // 10 GBP contract * approx 1.27 GBPUSD conversion rate = 12.70 USD
            12.70
        }
        "GBP/AUD", "EUR/AUD" -> {
            6.70
        }
        "USD/SGD" -> {
            7.40
        }
        "AUD/NZD" -> {
            6.10
        }
        "XAU/USD" -> {
            // Gold contract is 100 oz. 1 pip (0.1) on 100 oz = 10 USD
            10.0
        }
        else -> 10.0
    }

    val lots = riskAmt / (pips * pipValuePerLot)

    if (lots.isNaN() || lots.isInfinite()) {
        return PositionSizeResult(riskAmt, pips, 0.0, "Error in calculations. Check input parameters.")
    }

    return PositionSizeResult(riskAmt, pips, lots, "")
}

fun calculateProfit(
    balance: Double,
    riskPercent: Double,
    rr: Double
): ProfitResult {
    val riskAmount = balance * (riskPercent / 100.0)
    return ProfitResult(
        riskAmount = riskAmount,
        potentialProfit = riskAmount * rr,
        potentialLoss = riskAmount
    )
}

fun calculateProfitByPips(
    instrumentType: String,
    lots: Double,
    pips: Double
): ProfitByPipsResult {
    val pipValuePerLot = when (instrumentType) {
        "CRYPTO_INDICES" -> 1.0
        else -> 10.0
    }
    val pipValue = lots * pipValuePerLot
    return ProfitByPipsResult(
        pipValue = pipValue,
        estimatedProfitLoss = pipValue * pips
    )
}

// Searchable Forex Dropdown Composable
@Composable
fun SearchableForexDropdown(
    selectedPair: ForexPair,
    onPairSelected: (ForexPair) -> Unit,
    pairs: List<ForexPair>,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredPairs = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            pairs
        } else {
            pairs.filter { 
                it.symbol.contains(searchQuery, ignoreCase = true) || 
                it.displayName.contains(searchQuery, ignoreCase = true) 
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "FOREX PAIR",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextSecondary,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                .border(1.dp, BorderDb, shape = RoundedCornerShape(12.dp))
                .bounceClick { isExpanded = !isExpanded }
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "💱", fontSize = 16.sp, modifier = Modifier.padding(end = 12.dp))
                    Column {
                        Text(
                            text = selectedPair.symbol,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = selectedPair.displayName.substringAfter(" - "),
                            fontSize = 10.sp,
                            color = TextSecondary
                        )
                    }
                }
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown icon",
                    tint = ElectricBlue,
                    modifier = Modifier
                        .size(24.dp)
                        .scale(1f, if (isExpanded) -1f else 1f)
                )
            }
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { 
                isExpanded = false 
                searchQuery = ""
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .heightIn(max = 300.dp)
                .background(CardSurfaceDb)
                .border(1.dp, BorderDb, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search pair...", color = TextSecondary.copy(alpha = 0.6f), fontSize = 12.sp) },
                singleLine = true,
                textStyle = TextStyle(color = TextPrimary, fontSize = 13.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = Color.Black.copy(alpha = 0.3f),
                    unfocusedContainerColor = Color.Black.copy(alpha = 0.3f),
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = BorderDb,
                    cursorColor = ElectricBlue
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )

            HorizontalDivider()

            if (filteredPairs.isEmpty()) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "No pairs match your search.",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    },
                    onClick = {}
                )
            } else {
                filteredPairs.forEach { pair ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = pair.symbol,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (pair.symbol == selectedPair.symbol) ElectricBlue else TextPrimary
                                    )
                                    Text(
                                        text = pair.displayName.substringAfter(" - "),
                                        fontSize = 10.sp,
                                        color = TextSecondary
                                    )
                                }
                                if (pair.symbol == selectedPair.symbol) {
                                    Text("✓", color = ElectricBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                            }
                        },
                        onClick = {
                            onPairSelected(pair)
                            isExpanded = false
                            searchQuery = ""
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (pair.symbol == selectedPair.symbol) Color.White.copy(alpha = 0.05f) else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                    )
                }
            }
        }
    }
}

// 1. Position Size Calculator Screen (Strix Position)
@Composable
fun PositionSizeCalculatorScreen(
    onBack: () -> Unit
) {
    val forexPairs = remember {
        listOf(
            // USD Pairs & Major Spot (At the top)
            ForexPair("EUR/USD", "EUR/USD - Euro / US Dollar", 0.0001, 10.0),
            ForexPair("GBP/USD", "GBP/USD - Great Britain Pound / US Dollar", 0.0001, 10.0),
            ForexPair("USD/JPY", "USD/JPY - US Dollar / Japanese Yen", 0.01, 10.0),
            ForexPair("AUD/USD", "AUD/USD - Australian Dollar / US Dollar", 0.0001, 10.0),
            ForexPair("USD/CAD", "USD/CAD - US Dollar / Canadian Dollar", 0.0001, 10.0),
            ForexPair("USD/CHF", "USD/CHF - US Dollar / Swiss Franc", 0.0001, 10.0),
            ForexPair("NZD/USD", "NZD/USD - New Zealand Dollar / US Dollar", 0.0001, 10.0),
            ForexPair("USD/SGD", "USD/SGD - US Dollar / Singapore Dollar", 0.0001, 7.40),
            ForexPair("XAU/USD", "XAU/USD - Gold Spot / US Dollar", 0.1, 10.0),
            ForexPair("USD/HKD", "USD/HKD - US Dollar / Hong Kong Dollar", 0.0001, 1.28),
            ForexPair("USD/MXN", "USD/MXN - US Dollar / Mexican Peso", 0.0001, 0.59),
            ForexPair("USD/TRY", "USD/TRY - US Dollar / Turkish Lira", 0.0001, 0.31),
            ForexPair("USD/ZAR", "USD/ZAR - US Dollar / South African Rand", 0.0001, 0.54),

            // Other Cross Pairs (After USD pairs)
            ForexPair("EUR/GBP", "EUR/GBP - Euro / Great Britain Pound", 0.0001, 12.70),
            ForexPair("EUR/JPY", "EUR/JPY - Euro / Japanese Yen", 0.01, 6.45),
            ForexPair("GBP/JPY", "GBP/JPY - Great Britain Pound / Japanese Yen", 0.01, 6.45),
            ForexPair("AUD/JPY", "AUD/JPY - Australian Dollar / Japanese Yen", 0.01, 6.45),
            ForexPair("EUR/CHF", "EUR/CHF - Euro / Swiss Franc", 0.0001, 11.10),
            ForexPair("GBP/AUD", "GBP/AUD - Great Britain Pound / Australian Dollar", 0.0001, 6.70),
            ForexPair("EUR/AUD", "EUR/AUD - Euro / Australian Dollar", 0.0001, 6.70),
            ForexPair("GBP/CAD", "GBP/CAD - Great Britain Pound / Canadian Dollar", 0.0001, 7.30),
            ForexPair("EUR/CAD", "EUR/CAD - Euro / Canadian Dollar", 0.0001, 7.30),
            ForexPair("AUD/NZD", "AUD/NZD - Australian Dollar / New Zealand Dollar", 0.0001, 6.10),
            ForexPair("AUD/CAD", "AUD/CAD - Australian Dollar / Canadian Dollar", 0.0001, 7.30),
            ForexPair("CAD/JPY", "CAD/JPY - Canadian Dollar / Japanese Yen", 0.01, 6.45),
            ForexPair("CHF/JPY", "CHF/JPY - Swiss Franc / Japanese Yen", 0.01, 6.45),
            ForexPair("NZD/JPY", "NZD/JPY - New Zealand Dollar / Japanese Yen", 0.01, 6.45),
            ForexPair("GBP/CHF", "GBP/CHF - Great Britain Pound / Swiss Franc", 0.0001, 11.10),
            ForexPair("EUR/NZD", "EUR/NZD - Euro / New Zealand Dollar", 0.0001, 6.10),
            ForexPair("GBP/NZD", "GBP/NZD - Great Britain Pound / New Zealand Dollar", 0.0001, 6.10),
            ForexPair("NZD/CAD", "NZD/CAD - New Zealand Dollar / Canadian Dollar", 0.0001, 7.30),
            ForexPair("NZD/CHF", "NZD/CHF - New Zealand Dollar / Swiss Franc", 0.0001, 11.10),
            ForexPair("AUD/CHF", "AUD/CHF - Australian Dollar / Swiss Franc", 0.0001, 11.10),
            ForexPair("CAD/CHF", "CAD/CHF - Canadian Dollar / Swiss Franc", 0.0001, 11.10)
        )
    }

    var selectedPair by remember { mutableStateOf(forexPairs[0]) }
    var balanceInput by remember { mutableStateOf("") }
    var riskInput by remember { mutableStateOf("") }
    var isRiskPercent by remember { mutableStateOf(true) }

    // Dual input support: enter Stop Loss in Pips directly, or calculate via Entry/SL prices
    var isSlInPips by remember { mutableStateOf(true) }
    var slPipsInput by remember { mutableStateOf("") }
    var entryInput by remember { mutableStateOf("") }
    var slPriceInput by remember { mutableStateOf("") }

    var calculatedResult by remember { mutableStateOf<PositionSizeResult?>(null) }
    var validationError by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(selectedPair) {
        calculatedResult = null
        validationError = ""
        entryInput = ""
        slPriceInput = ""
        slPipsInput = ""
    }

    LaunchedEffect(balanceInput, riskInput, slPipsInput, entryInput, slPriceInput, isSlInPips, isRiskPercent) {
        calculatedResult = null
        validationError = ""
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepSpaceDb),
        containerColor = DeepSpaceDb,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardSurfaceDb)
                    .border(width = 1.dp, color = BorderDb)
                    .statusBarsPadding()
                    .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.05f), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = ElectricBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = "POSITION SIZE",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        color = ElectricBlue
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // 1. Forex Pair Selector (Searchable)
            SearchableForexDropdown(
                selectedPair = selectedPair,
                onPairSelected = { pair ->
                    selectedPair = pair
                },
                pairs = forexPairs
            )

            // 2. Account Balance
            StrixaInputField(
                value = balanceInput,
                onValueChange = { balanceInput = it },
                label = "Account Balance ($)",
                placeholder = "e.g., 10000",
                leadingIcon = "💵",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // 3. Risk Configuration
            Column {
                Text(
                    text = "RISK TYPE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                        .border(1.dp, BorderDb, shape = RoundedCornerShape(12.dp))
                        .padding(4.dp)
                ) {
                    listOf(true to "Risk %", false to "Risk Amount ($)").forEach { (isPercent, label) ->
                        val isSelected = isRiskPercent == isPercent
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .background(
                                    color = if (isSelected) ElectricBlue else Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .bounceClick { isRiskPercent = isPercent },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else TextSecondary
                            )
                        }
                    }
                }
            }

            StrixaInputField(
                value = riskInput,
                onValueChange = { riskInput = it },
                label = if (isRiskPercent) "Risk (%)" else "Risk Amount ($)",
                placeholder = if (isRiskPercent) "e.g., 1" else "e.g., 100",
                leadingIcon = "⚠️",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // 4. Stop Loss Mode Toggle
            Column {
                Text(
                    text = "STOP LOSS INPUT MODE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                        .border(1.dp, BorderDb, shape = RoundedCornerShape(12.dp))
                        .padding(4.dp)
                ) {
                    listOf(true to "Stop Loss in Pips", false to "Entry & SL Prices").forEach { (inPips, label) ->
                        val isSelected = isSlInPips == inPips
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .background(
                                    color = if (isSelected) ElectricBlue else Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .bounceClick { isSlInPips = inPips },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else TextSecondary
                            )
                        }
                    }
                }
            }

            // 5. Dynamic Stop Loss Fields
            if (isSlInPips) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StrixaInputField(
                        value = slPipsInput,
                        onValueChange = { slPipsInput = it },
                        label = "Stop Loss (Pips)",
                        placeholder = "e.g., 30",
                        modifier = Modifier.weight(1f),
                        leadingIcon = "🎯",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    StrixaInputField(
                        value = entryInput,
                        onValueChange = { entryInput = it },
                        label = "Entry Price",
                        placeholder = "Optional (e.g. 1.0850)",
                        modifier = Modifier.weight(1f),
                        leadingIcon = "📈",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Text(
                    text = "⚠️ WARNING: Lot size directly impacts your risk exposure.\nAlways verify your calculated position size aligns with your strict risk management limits.",
                    fontSize = 10.sp,
                    color = BearishRed,
                    lineHeight = 14.sp,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StrixaInputField(
                        value = entryInput,
                        onValueChange = { entryInput = it },
                        label = "Entry Price",
                        placeholder = "e.g., 1.0850",
                        modifier = Modifier.weight(1f),
                        leadingIcon = "📈",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    StrixaInputField(
                        value = slPriceInput,
                        onValueChange = { slPriceInput = it },
                        label = "Stop Loss Price",
                        placeholder = "e.g., 1.0800",
                        modifier = Modifier.weight(1f),
                        leadingIcon = "📉",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val psInteractionSource = remember { MutableInteractionSource() }
            val isPsPressed by psInteractionSource.collectIsPressedAsState()
            val psBtnScale by animateFloatAsState(
                targetValue = if (isPsPressed) 0.95f else 1f,
                animationSpec = spring(stiffness = Spring.StiffnessLow),
                label = "btn_press"
            )

            // 6. Calculate Button
            Button(
                onClick = {
                    keyboardController?.hide()
                    val bal = balanceInput.toDoubleOrNull()
                    val risk = riskInput.toDoubleOrNull()
                    val entry = entryInput.toDoubleOrNull() ?: 1.0

                    if (bal == null || risk == null) {
                        validationError = "Please enter valid Account Balance and Risk values."
                        calculatedResult = null
                    } else if (bal <= 0 || risk <= 0) {
                        validationError = "Balance and Risk values must be greater than zero."
                        calculatedResult = null
                    } else {
                        if (isSlInPips) {
                            val pipsVal = slPipsInput.toDoubleOrNull()
                            if (pipsVal == null || pipsVal <= 0) {
                                validationError = "Please enter a valid Stop Loss distance in Pips."
                                calculatedResult = null
                            } else {
                                validationError = ""
                                calculatedResult = calculatePositionSize(
                                    balance = bal,
                                    riskVal = risk,
                                    isRiskPercent = isRiskPercent,
                                    entryPrice = entry,
                                    stopLossPips = pipsVal,
                                    stopLossPrice = null,
                                    selectedPair = selectedPair
                                )
                            }
                        } else {
                            val slPriceVal = slPriceInput.toDoubleOrNull()
                            val entryVal = entryInput.toDoubleOrNull()
                            if (entryVal == null || slPriceVal == null) {
                                validationError = "Please enter valid Entry Price and Stop Loss Price values."
                                calculatedResult = null
                            } else if (entryVal <= 0 || slPriceVal <= 0) {
                                validationError = "Price values must be greater than zero."
                                calculatedResult = null
                            } else if (entryVal == slPriceVal) {
                                validationError = "Entry Price and Stop Loss Price cannot be equal."
                                calculatedResult = null
                            } else {
                                validationError = ""
                                calculatedResult = calculatePositionSize(
                                    balance = bal,
                                    riskVal = risk,
                                    isRiskPercent = isRiskPercent,
                                    entryPrice = entryVal,
                                    stopLossPips = null,
                                    stopLossPrice = slPriceVal,
                                    selectedPair = selectedPair
                                )
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(12.dp),
                interactionSource = psInteractionSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .graphicsLayer {
                        scaleX = psBtnScale
                        scaleY = psBtnScale
                    }
            ) {
                Text(
                    text = "CALCULATE POSITION SIZE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            }

            if (validationError.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BearishRed.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))
                        .border(1.dp, BearishRed.copy(alpha = 0.3f), shape = RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "⚠️", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = validationError, color = BearishRed, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            // 7. Results Display
            calculatedResult?.let { res ->
                if (res.error.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BearishRed.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))
                            .border(1.dp, BearishRed.copy(alpha = 0.3f), shape = RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Text(text = res.error, color = BearishRed, fontSize = 12.sp)
                    }
                } else {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, ElectricBlue.copy(alpha = 0.4f), shape = RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "POSITION SIZE CALCULATION SETUP SUCCESSFUL",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = ElectricBlue,
                                letterSpacing = 1.sp
                            )

                            HorizontalDivider()

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Selected Pair:", fontSize = 12.sp, color = TextSecondary)
                                Text(
                                    text = selectedPair.symbol,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Risk Capital:", fontSize = 12.sp, color = TextSecondary)
                                Text(
                                    text = String.format(Locale.US, "$%.2f", res.riskAmount),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Stop Loss Distance:", fontSize = 12.sp, color = TextSecondary)
                                Text(
                                    text = String.format(Locale.US, "%.1f Pips", res.pips),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldGold
                                )
                            }

                            HorizontalDivider()

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Required Position Size:", fontSize = 13.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
                                    Text(
                                        text = String.format(Locale.US, "%.3f Standard Lots", res.lots),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        color = BullishGreen
                                    )
                                }

                                // Equivalence breakdowns
                                val miniLots = res.lots * 10.0
                                val microLots = res.lots * 100.0

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Mini Lots (0.1 Lot size):", fontSize = 11.sp, color = TextSecondary)
                                    Text(
                                        text = String.format(Locale.US, "%.2f Mini Lots", miniLots),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextPrimary
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Micro Lots (0.01 Lot size):", fontSize = 11.sp, color = TextSecondary)
                                    Text(
                                        text = String.format(Locale.US, "%.1f Micro Lots", microLots),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

// 2. Profit Calculator Screen
@Composable
fun ProfitCalculatorScreen(
    onBack: () -> Unit
) {
    var balanceInput by remember { mutableStateOf("") }
    var riskInput by remember { mutableStateOf("") }
    var rrInput by remember { mutableStateOf("") }

    var calculatedResult by remember { mutableStateOf<ProfitResult?>(null) }
    var validationError by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(balanceInput, riskInput, rrInput) {
        calculatedResult = null
        validationError = ""
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepSpaceDb),
        containerColor = DeepSpaceDb,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardSurfaceDb)
                    .border(width = 1.dp, color = BorderDb)
                    .statusBarsPadding()
                    .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.05f), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = ElectricBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = "PROFIT BY RISK TO REWARD (RR)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        color = ElectricBlue
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            StrixaInputField(
                value = balanceInput,
                onValueChange = { balanceInput = it },
                label = "Account Balance ($)",
                placeholder = "e.g., 10000",
                leadingIcon = "💵",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            StrixaInputField(
                value = riskInput,
                onValueChange = { riskInput = it },
                label = "Risk %",
                placeholder = "e.g., 1.5",
                leadingIcon = "⚠️",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            StrixaInputField(
                value = rrInput,
                onValueChange = { rrInput = it },
                label = "Risk : Reward (RR Ratio)",
                placeholder = "e.g., 3",
                leadingIcon = "🎯",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            val profitInteractionSource = remember { MutableInteractionSource() }
            val isProfitPressed by profitInteractionSource.collectIsPressedAsState()
            val profitBtnScale by animateFloatAsState(
                targetValue = if (isProfitPressed) 0.95f else 1f,
                animationSpec = spring(stiffness = Spring.StiffnessLow),
                label = "btn_press"
            )

            Button(
                onClick = {
                    keyboardController?.hide()
                    val bal = balanceInput.toDoubleOrNull()
                    val risk = riskInput.toDoubleOrNull()
                    val rr = rrInput.toDoubleOrNull()

                    if (bal == null || risk == null || rr == null) {
                        validationError = "Please fill in all fields with valid numbers."
                        calculatedResult = null
                    } else if (bal <= 0 || risk <= 0 || rr <= 0) {
                        validationError = "Values must be greater than zero."
                        calculatedResult = null
                    } else {
                        validationError = ""
                        calculatedResult = calculateProfit(bal, risk, rr)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(12.dp),
                interactionSource = profitInteractionSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .graphicsLayer {
                        scaleX = profitBtnScale
                        scaleY = profitBtnScale
                    }
            ) {
                Text(
                    text = "CALCULATE",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            }

            if (validationError.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BearishRed.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))
                        .border(1.dp, BearishRed.copy(alpha = 0.3f), shape = RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "⚠️", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = validationError, color = BearishRed, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            calculatedResult?.let { res ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, BullishGreen.copy(alpha = 0.4f), shape = RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "RISK & PROJECTION SUCCESSFUL",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BullishGreen,
                            letterSpacing = 1.sp
                        )

                        HorizontalDivider()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Risk Amount:", fontSize = 12.sp, color = TextSecondary)
                            Text(
                                text = String.format(Locale.US, "$%.2f", res.riskAmount),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Potential Loss:", fontSize = 12.sp, color = TextSecondary)
                            Text(
                                text = String.format(Locale.US, "-$%.2f", res.potentialLoss),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = BearishRed
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Potential Profit:", fontSize = 12.sp, color = TextSecondary)
                            Text(
                                text = String.format(Locale.US, "+$%.2f", res.potentialProfit),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = BullishGreen
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

// 3. Profit by Pips Screen
@Composable
fun ProfitByPipsScreen(
    onBack: () -> Unit
) {
    var lotsInput by remember { mutableStateOf("") }
    var pipsInput by remember { mutableStateOf("") }
    var selectedInstrumentType by remember { mutableStateOf("FOREX_STANDARD") }

    var calculatedResult by remember { mutableStateOf<ProfitByPipsResult?>(null) }
    var validationError by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(lotsInput, pipsInput, selectedInstrumentType) {
        calculatedResult = null
        validationError = ""
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepSpaceDb),
        containerColor = DeepSpaceDb,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardSurfaceDb)
                    .border(width = 1.dp, color = BorderDb)
                    .statusBarsPadding()
                    .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.05f), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = ElectricBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = "PROFIT BY PIPS",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        color = ElectricBlue
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Column {
                Text(
                    text = "INSTRUMENT TYPE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                        .border(1.dp, BorderDb, shape = RoundedCornerShape(12.dp))
                        .padding(4.dp)
                ) {
                    listOf("FOREX_STANDARD" to "Forex / Gold", "CRYPTO_INDICES" to "Crypto / Indices").forEach { (instType, label) ->
                        val isSelected = selectedInstrumentType == instType
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .background(
                                    color = if (isSelected) ElectricBlue else Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .bounceClick { selectedInstrumentType = instType },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else TextSecondary
                            )
                        }
                    }
                }
            }

            StrixaInputField(
                value = lotsInput,
                onValueChange = { lotsInput = it },
                label = "Position Size (Lots)",
                placeholder = "e.g., 1.5",
                leadingIcon = "⚖️",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            StrixaInputField(
                value = pipsInput,
                onValueChange = { pipsInput = it },
                label = "Number of Pips (or Points)",
                placeholder = "e.g., 50",
                leadingIcon = "🎯",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            val pipInteractionSource = remember { MutableInteractionSource() }
            val isPipPressed by pipInteractionSource.collectIsPressedAsState()
            val pipBtnScale by animateFloatAsState(
                targetValue = if (isPipPressed) 0.95f else 1f,
                animationSpec = spring(stiffness = Spring.StiffnessLow),
                label = "btn_press"
            )

            Button(
                onClick = {
                    keyboardController?.hide()
                    val lots = lotsInput.toDoubleOrNull()
                    val pips = pipsInput.toDoubleOrNull()

                    if (lots == null || pips == null) {
                        validationError = "Please fill in all fields with valid numbers."
                        calculatedResult = null
                    } else if (lots <= 0) {
                        validationError = "Position size must be greater than zero."
                        calculatedResult = null
                    } else {
                        validationError = ""
                        calculatedResult = calculateProfitByPips(selectedInstrumentType, lots, pips)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(12.dp),
                interactionSource = pipInteractionSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .graphicsLayer {
                        scaleX = pipBtnScale
                        scaleY = pipBtnScale
                    }
            ) {
                Text(
                    text = "CALCULATE",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            }

            if (validationError.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BearishRed.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))
                        .border(1.dp, BearishRed.copy(alpha = 0.3f), shape = RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "⚠️", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = validationError, color = BearishRed, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            calculatedResult?.let { res ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, GoldGold.copy(alpha = 0.4f), shape = RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "PIP OUTCOME SUCCESSFUL",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldGold,
                            letterSpacing = 1.sp
                        )

                        HorizontalDivider()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Pip Value (per pip):", fontSize = 12.sp, color = TextSecondary)
                            Text(
                                text = String.format(Locale.US, "$%.2f", res.pipValue),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Estimated Profit or Loss:", fontSize = 12.sp, color = TextSecondary)
                            val isLoss = res.estimatedProfitLoss < 0
                            Text(
                                text = String.format(Locale.US, "%s$%.2f", if (isLoss) "" else "+", res.estimatedProfitLoss),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = if (isLoss) BearishRed else BullishGreen
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
fun StandardCalculatorScreen(
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("strixa_calc_prefs", Context.MODE_PRIVATE) }

    fun saveHistory(items: List<Pair<String, String>>) {
        val jsonArray = org.json.JSONArray()
        items.take(50).forEach { (formula, result) ->
            val obj = org.json.JSONObject()
            obj.put("formula", formula)
            obj.put("result", result)
            jsonArray.put(obj)
        }
        prefs.edit().putString("calc_history", jsonArray.toString()).apply()
    }

    fun loadHistory(): List<Pair<String, String>> {
        val raw = prefs.getString("calc_history", null) ?: return emptyList()
        val list = mutableListOf<Pair<String, String>>()
        try {
            val jsonArray = org.json.JSONArray(raw)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(Pair(obj.getString("formula"), obj.getString("result")))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    var historyList by remember { mutableStateOf(loadHistory()) }
    var currentInputVal by remember { mutableStateOf(TextFieldValue("")) }
    var showingResult by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("") }
    var upperText by remember { mutableStateOf("") }
    var acClickCount by remember { mutableStateOf(0) }
    var pendingHistoryItem by remember { mutableStateOf<Pair<String, String>?>(null) }

    val historyListState = rememberLazyListState()

    fun formatDouble(v: Double): String {
        if (v.isNaN()) return "Error"
        if (v.isInfinite()) return "Limit Error"
        try {
            var bd = java.math.BigDecimal(v)
            // Round to 12 decimal places to clear up floating point artifacts
            bd = bd.setScale(12, java.math.RoundingMode.HALF_UP)
            val plain = bd.stripTrailingZeros().toPlainString()
            return plain
        } catch (e: Exception) {
            val s = v.toString()
            return if (s.endsWith(".0")) s.substring(0, s.length - 2) else s
        }
    }

    fun tokenize(expr: String): List<String> {
        val tokens = mutableListOf<String>()
        var currentNum = StringBuilder()
        val cleanExpr = expr.replace(" ", "")
        for (i in cleanExpr.indices) {
            val c = cleanExpr[i]
            if (c.isDigit() || c == '.') {
                currentNum.append(c)
            } else if (c == '-' && (i == 0 || cleanExpr[i - 1] in listOf('+', '-', '×', '÷'))) {
                currentNum.append(c)
            } else if (c in listOf('+', '-', '×', '÷')) {
                if (currentNum.isNotEmpty()) {
                    tokens.add(currentNum.toString())
                    currentNum = StringBuilder()
                }
                tokens.add(c.toString())
            }
        }
        if (currentNum.isNotEmpty()) {
            tokens.add(currentNum.toString())
        }
        return tokens
    }

    fun cleanTokens(tokens: List<String>): List<String> {
        if (tokens.isEmpty()) return tokens
        var list = tokens
        while (list.isNotEmpty() && list.last() in listOf("+", "-", "×", "÷")) {
            list = list.dropLast(1)
        }
        while (list.isNotEmpty() && list.first() in listOf("+", "×", "÷")) {
            list = list.drop(1)
        }
        return list
    }

    fun evaluateTokens(tokens: List<String>): Double {
        if (tokens.isEmpty()) return 0.0

        val pass1 = mutableListOf<String>()
        var i = 0
        while (i < tokens.size) {
            val token = tokens[i]
            if (token == "×" || token == "÷") {
                val op = token
                val prevVal = pass1.removeAt(pass1.size - 1).toDoubleOrNull() ?: 0.0
                val nextVal = if (i + 1 < tokens.size) tokens[i + 1].toDoubleOrNull() ?: 0.0 else 0.0
                val res = if (op == "×") prevVal * nextVal else {
                    if (nextVal == 0.0) 0.0 else prevVal / nextVal
                }
                pass1.add(res.toString())
                i += 2
            } else {
                pass1.add(token)
                i++
            }
        }

        if (pass1.isEmpty()) return 0.0
        var result = pass1[0].toDoubleOrNull() ?: 0.0
        var j = 1
        while (j < pass1.size) {
            val op = pass1[j]
            val nextVal = if (j + 1 < pass1.size) pass1[j + 1].toDoubleOrNull() ?: 0.0 else 0.0
            if (op == "+") {
                result += nextVal
            } else if (op == "-") {
                result -= nextVal
            }
            j += 2
        }
        return result
    }

    fun insertText(key: String) {
        val text = currentInputVal.text
        val sel = currentInputVal.selection
        val start = sel.start.coerceIn(0, text.length)
        val end = sel.end.coerceIn(0, text.length)
        val newText = text.substring(0, start) + key + text.substring(end)
        val newCursor = start + key.length
        currentInputVal = TextFieldValue(newText, TextRange(newCursor))
    }

    fun deleteText() {
        val text = currentInputVal.text
        val sel = currentInputVal.selection
        val start = sel.start.coerceIn(0, text.length)
        val end = sel.end.coerceIn(0, text.length)
        if (start != end) {
            val newText = text.substring(0, start) + text.substring(end)
            currentInputVal = TextFieldValue(newText, TextRange(start))
        } else if (start > 0) {
            val newText = text.substring(0, start - 1) + text.substring(start)
            currentInputVal = TextFieldValue(newText, TextRange(start - 1))
        }
    }

    fun applyPercentAtCursor(value: TextFieldValue): TextFieldValue {
        val text = value.text
        val cursor = value.selection.start
        if (text.isEmpty() || cursor <= 0) return value

        var start = cursor - 1
        while (start >= 0 && (text[start].isDigit() || text[start] == '.')) {
            start--
        }
        if (start >= 0 && text[start] == '-') {
            if (start == 0 || text[start - 1] in listOf('+', '-', '×', '÷')) {
                start--
            }
        }
        val numStart = start + 1
        val numEnd = cursor
        if (numStart < numEnd) {
            val numStr = text.substring(numStart, numEnd)
            val numDouble = numStr.toDoubleOrNull()
            if (numDouble != null) {
                val percentStr = formatDouble(numDouble / 100.0)
                val newText = text.substring(0, numStart) + percentStr + text.substring(numEnd)
                val newCursor = numStart + percentStr.length
                return TextFieldValue(newText, TextRange(newCursor))
            }
        }
        return value
    }

    fun commitPendingHistory() {
        val pending = pendingHistoryItem
        if (pending != null) {
            val updated = (listOf(pending) + historyList).take(50)
            historyList = updated
            saveHistory(updated)
            pendingHistoryItem = null
        }
    }

    fun handlePress(key: String) {
        if (key != "AC" && key != "CLEAR_ALL") {
            acClickCount = 0
        }

        // Auto-scroll history to bottom when typing or executing action
        scope.launch {
            if (historyList.isNotEmpty()) {
                try {
                    historyListState.animateScrollToItem(0)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        if (showingResult) {
            commitPendingHistory()
            showingResult = false
            val prevResult = resultText
            resultText = ""
            upperText = ""

            when (key) {
                in "0123456789" -> {
                    currentInputVal = TextFieldValue(key, TextRange(key.length))
                }
                "." -> {
                    currentInputVal = TextFieldValue("0.", TextRange(2))
                }
                in listOf("+", "-", "×", "÷") -> {
                    currentInputVal = TextFieldValue(prevResult + key, TextRange((prevResult + key).length))
                }
                "%" -> {
                    val resDouble = prevResult.toDoubleOrNull() ?: 0.0
                    val percentVal = formatDouble(resDouble / 100.0)
                    currentInputVal = TextFieldValue(percentVal, TextRange(percentVal.length))
                }
                "⌫" -> {
                    currentInputVal = TextFieldValue("", TextRange.Zero)
                }
                "=" -> {
                    showingResult = true
                    resultText = prevResult
                }
                "AC" -> {
                    currentInputVal = TextFieldValue("", TextRange.Zero)
                    acClickCount = 1
                }
            }
        } else {
            when {
                key == "CLEAR_ALL" -> {
                    currentInputVal = TextFieldValue("", TextRange.Zero)
                    resultText = ""
                    upperText = ""
                    showingResult = false
                    acClickCount = 0
                    pendingHistoryItem = null
                }
                key == "AC" -> {
                    val hasActiveInput = currentInputVal.text.isNotEmpty() || showingResult
                    if (hasActiveInput) {
                        currentInputVal = TextFieldValue("", TextRange.Zero)
                        resultText = ""
                        upperText = ""
                        showingResult = false
                        pendingHistoryItem = null
                        acClickCount = 1
                    } else {
                        acClickCount++
                        if (acClickCount >= 2) {
                            historyList = emptyList()
                            saveHistory(emptyList())
                            acClickCount = 0
                        }
                    }
                }
                key == "⌫" -> {
                    deleteText()
                }
                key in "0123456789" -> {
                    if (currentInputVal.text == "0") {
                        currentInputVal = TextFieldValue(key, TextRange(key.length))
                    } else {
                        insertText(key)
                    }
                }
                key == "." -> {
                    val text = currentInputVal.text
                    val cursor = currentInputVal.selection.start
                    var start = cursor - 1
                    while (start >= 0 && text[start].isDigit()) {
                        start--
                    }
                    val hasDotInCurrentNumber = start >= 0 && text[start] == '.'
                    if (!hasDotInCurrentNumber) {
                        if (currentInputVal.text.isEmpty() || (cursor > 0 && currentInputVal.text[cursor - 1] in listOf('+', '-', '×', '÷'))) {
                            insertText("0.")
                        } else {
                            insertText(".")
                        }
                    }
                }
                key == "%" -> {
                    currentInputVal = applyPercentAtCursor(currentInputVal)
                }
                key in listOf("+", "-", "×", "÷") -> {
                    val text = currentInputVal.text
                    val cursor = currentInputVal.selection.start
                    if (cursor > 0 && text[cursor - 1] in listOf('+', '-', '×', '÷')) {
                        val newText = text.substring(0, cursor - 1) + key + text.substring(cursor)
                        currentInputVal = TextFieldValue(newText, TextRange(cursor))
                    } else {
                        insertText(key)
                    }
                }
                key == "=" -> {
                    val exprStr = currentInputVal.text
                    val tokens = cleanTokens(tokenize(exprStr))
                    if (tokens.isNotEmpty()) {
                        val resultVal = evaluateTokens(tokens)
                        val resultStr = formatDouble(resultVal)
                        val sanitizedExpr = tokens.joinToString(" ")
                        upperText = sanitizedExpr
                        resultText = resultStr
                        showingResult = true
                        pendingHistoryItem = Pair(sanitizedExpr, resultStr)
                        currentInputVal = TextFieldValue("", TextRange.Zero)
                    }
                }
            }
        }
    }

    val isScrolledUp = historyListState.firstVisibleItemIndex > 0 || historyListState.firstVisibleItemScrollOffset > 30
    val displayAlpha by animateFloatAsState(
        targetValue = if (isScrolledUp) 0f else 1f,
        animationSpec = tween(300),
        label = "displayAlpha"
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepSpaceDb),
        containerColor = DeepSpaceDb,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardSurfaceDb)
                    .border(width = 1.dp, color = BorderDb)
                    .statusBarsPadding()
                    .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.05f), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = ElectricBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = "STANDARD CALCULATOR",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        color = ElectricBlue
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Display & Scrollable History Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 12.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                // Scrollable History list
                LazyColumn(
                    state = historyListState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    reverseLayout = true,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    if (historyList.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No Calculation History",
                                    fontSize = 13.sp,
                                    color = TextMuted,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    } else {
                        itemsIndexed(historyList) { index, item ->
                            val itemAlpha = (0.5f - (index * 0.05f)).coerceIn(0.15f, 0.5f)
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .alpha(itemAlpha),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = item.first,
                                    fontSize = 13.sp,
                                    color = TextSecondary,
                                    maxLines = 1,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "= ${item.second}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ElectricBlue,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Current Input / Operators Display Area (Vanish when history scrolled up)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer { alpha = displayAlpha }
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    // Show AC status message if we are primed to clear history
                    if (acClickCount > 0 && currentInputVal.text.isEmpty() && !showingResult) {
                        Text(
                            text = "Press AC once more to clear history",
                            fontSize = 11.sp,
                            color = GoldGold.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    val displayResultFontSize = when {
                        resultText.length > 25 -> 18.sp
                        resultText.length > 20 -> 22.sp
                        resultText.length > 15 -> 28.sp
                        resultText.length > 10 -> 36.sp
                        else -> 44.sp
                    }

                    val displayInputFontSize = when {
                        currentInputVal.text.length > 25 -> 18.sp
                        currentInputVal.text.length > 20 -> 22.sp
                        currentInputVal.text.length > 15 -> 28.sp
                        currentInputVal.text.length > 10 -> 36.sp
                        else -> 44.sp
                    }

                    val displayUpperFontSize = when {
                        upperText.length > 30 -> 12.sp
                        upperText.length > 20 -> 14.sp
                        else -> 18.sp
                    }

                    if (showingResult) {
                        Text(
                            text = upperText,
                            fontSize = displayUpperFontSize,
                            fontWeight = FontWeight.Medium,
                            color = TextSecondary,
                            maxLines = 1,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        Text(
                            text = resultText,
                            fontSize = displayResultFontSize,
                            fontWeight = FontWeight.Black,
                            color = ElectricBlue,
                            maxLines = 1,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    } else {
                        CompositionLocalProvider(
                            androidx.compose.foundation.text.selection.LocalTextSelectionColors provides 
                            androidx.compose.foundation.text.selection.TextSelectionColors(
                                handleColor = ElectricBlue,
                                backgroundColor = ElectricBlue.copy(alpha = 0.3f)
                            )
                        ) {
                            BasicTextField(
                                value = currentInputVal,
                                onValueChange = { currentInputVal = it },
                                readOnly = true, // Prevent soft keyboard, handle key clicks manually
                                textStyle = TextStyle(
                                    fontSize = displayInputFontSize,
                                    fontWeight = FontWeight.Black,
                                    color = TextPrimary,
                                    textAlign = TextAlign.End
                                ),
                                cursorBrush = SolidColor(ElectricBlue),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        if (currentInputVal.text.isEmpty()) {
                                            Text(
                                                text = "0",
                                                fontSize = displayInputFontSize,
                                                fontWeight = FontWeight.Black,
                                                color = TextSecondary.copy(alpha = 0.5f)
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )
                        }
                    }
                }
            }

            // Keyboard Area
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val buttonRows = listOf(
                    listOf("AC", "⌫", "%", "÷"),
                    listOf("7", "8", "9", "×"),
                    listOf("4", "5", "6", "-"),
                    listOf("1", "2", "3", "+"),
                    listOf("0", ".", "=")
                )

                buttonRows.forEach { rowKeys ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowKeys.forEach { key ->
                            val isOperator = key in listOf("+", "-", "×", "÷")
                            val isAction = key in listOf("AC", "⌫", "=", "%")
                            val isZero = key == "0"
                            val isEquals = key == "="
                            val isBackspace = key == "⌫"
                            val isAc = key == "AC"

                            val bgColor = when {
                                isEquals -> BullishGreen
                                isAc -> BearishRed.copy(alpha = 0.15f)
                                isAction -> CardSurfaceDb.copy(alpha = 0.8f)
                                isOperator -> ElectricBlue.copy(alpha = 0.15f)
                                else -> CardSurfaceDb
                            }

                            val textColor = when {
                                isEquals -> Color.White
                                isAc -> BearishRed
                                isOperator -> ElectricBlue
                                isAction -> GoldGold
                                else -> TextPrimary
                            }

                            Box(
                                modifier = Modifier
                                    .weight(if (isZero && rowKeys.size == 3) 2f else 1f)
                                    .height(68.dp)
                                    .background(bgColor, shape = RoundedCornerShape(16.dp))
                                    .border(1.dp, BorderDb.copy(alpha = 0.5f), shape = RoundedCornerShape(16.dp))
                                    .let { modifier ->
                                        if (isBackspace) {
                                            modifier.bounceClickWithLong(
                                                onClick = { handlePress("⌫") },
                                                onLongClick = { handlePress("CLEAR_ALL") }
                                            )
                                        } else {
                                            modifier.bounceClick { handlePress(key) }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = key,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyCalculatorScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("exchange_rates_prefs", Context.MODE_PRIVATE) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    var fromCurrency by remember { mutableStateOf("USD") }
    var toCurrency by remember { mutableStateOf("INR") }
    
    // Supported currencies list
    val currencies = remember {
        listOf(
            "USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "INR", "PKR", "SGD",
            "AED", "SAR", "CNY", "HKD", "NZD", "BRL", "RUB", "ZAR", "MXN", "TRY", "BDT"
        )
    }

    val currencyNames = remember {
        mapOf(
            "USD" to ("US Dollar" to "💵"),
            "EUR" to ("Euro" to "💶"),
            "GBP" to ("British Pound" to "💷"),
            "JPY" to ("Japanese Yen" to "💴"),
            "AUD" to ("Australian Dollar" to "🇦🇺"),
            "CAD" to ("Canadian Dollar" to "🇨🇦"),
            "CHF" to ("Swiss Franc" to "🇨🇭"),
            "INR" to ("Indian Rupee" to "🇮🇳"),
            "PKR" to ("Pakistani Rupee" to "🇵🇰"),
            "SGD" to ("Singapore Dollar" to "🇸🇬"),
            "AED" to ("UAE Dirham" to "🇦🇪"),
            "SAR" to ("Saudi Riyal" to "🇸🇦"),
            "CNY" to ("Chinese Yuan" to "🇨🇳"),
            "HKD" to ("Hong Kong Dollar" to "🇭🇰"),
            "NZD" to ("New Zealand Dollar" to "🇳🇿"),
            "BRL" to ("Brazilian Real" to "🇧🇷"),
            "RUB" to ("Russian Ruble" to "🇷🇺"),
            "ZAR" to ("South African Rand" to "🇿🇦"),
            "MXN" to ("Mexican Peso" to "🇲🇽"),
            "TRY" to ("Turkish Lira" to "🇹🇷"),
            "BDT" to ("Bangladeshi Taka" to "🇧🇩")
        )
    }

    // Load initially from SharedPreferences if saved, otherwise getDefaultRates()
    var exchangeRates by remember {
        mutableStateOf(
            run {
                val savedRates = mutableMapOf<String, Double>()
                currencies.forEach { code ->
                    if (sharedPrefs.contains("rate_$code")) {
                        savedRates[code] = sharedPrefs.getFloat("rate_$code", 1f).toDouble()
                    }
                }
                if (savedRates.isNotEmpty()) savedRates else getDefaultRates()
            }
        )
    }

    var isLoading by remember { mutableStateOf(false) }
    var syncMessage by remember { mutableStateOf("Syncing live rates...") }
    var isError by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()
    var showFromDialog by remember { mutableStateOf(false) }
    var showToDialog by remember { mutableStateOf(false) }

    // Start currency values at "0"
    var amountFrom by remember { mutableStateOf("0") }
    var amountTo by remember { mutableStateOf("0") }
    var lastEditedBy by remember { mutableStateOf("FROM") } // "FROM" or "TO"

    var rotationAngle by remember { mutableStateOf(0f) }
    val animatedRotation by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "swap_rotation"
    )

    fun convertCurrency(amountStr: String, fromCode: String, toCode: String): String {
        if (amountStr.isEmpty() || amountStr == "0") return "0"
        val amount = amountStr.toDoubleOrNull() ?: return "0"
        val rateFrom = exchangeRates[fromCode] ?: 1.0
        val rateTo = exchangeRates[toCode] ?: 1.0
        val converted = (amount / rateFrom) * rateTo
        
        try {
            var bd = java.math.BigDecimal(converted)
            bd = bd.setScale(4, java.math.RoundingMode.HALF_UP)
            return bd.stripTrailingZeros().toPlainString()
        } catch (e: Exception) {
            return String.format(Locale.US, "%.4f", converted).trimEnd('0').trimEnd('.')
        }
    }

    // Reusable, thread-safe rate synchronization helper
    fun refreshRates() {
        if (isLoading) return
        scope.launch {
            isLoading = true
            isError = false
            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                try {
                    val url = URL("https://open.er-api.com/v6/latest/USD")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 5000
                    connection.readTimeout = 5000
                    if (connection.responseCode == 200) {
                        val jsonText = connection.inputStream.bufferedReader().use { it.readText() }
                        val jsonObject = JSONObject(jsonText)
                        if (jsonObject.getString("result") == "success") {
                            val ratesObj = jsonObject.getJSONObject("rates")
                            val newRates = mutableMapOf<String, Double>()
                            currencies.forEach { code ->
                                if (ratesObj.has(code)) {
                                    newRates[code] = ratesObj.getDouble(code)
                                }
                            }
                            
                            // Commit live values to persistent local SharedPreferences
                            val editor = sharedPrefs.edit()
                            newRates.forEach { (code, rate) ->
                                editor.putFloat("rate_$code", rate.toFloat())
                            }
                            editor.apply()

                            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                                exchangeRates = newRates
                                isLoading = false
                                isError = false
                                syncMessage = "Live Exchange Rates Active"
                                if (lastEditedBy == "FROM") {
                                    amountTo = convertCurrency(amountFrom, fromCurrency, toCurrency)
                                } else {
                                    amountFrom = convertCurrency(amountTo, toCurrency, fromCurrency)
                                }
                            }
                        } else {
                            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                                isLoading = false
                                isError = true
                                syncMessage = "Offline [Stored Exchange Rates]"
                            }
                        }
                    } else {
                        kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                            isLoading = false
                            isError = true
                            syncMessage = "Offline [Stored Exchange Rates]"
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                        isLoading = false
                        isError = true
                        syncMessage = "Offline [Stored Exchange Rates]"
                    }
                }
            }
        }
    }

    // Trigger initial rate refresh on screen entry
    LaunchedEffect(Unit) {
        refreshRates()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepSpaceDb),
        containerColor = DeepSpaceDb,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardSurfaceDb)
                    .border(width = 1.dp, color = BorderDb)
                    .statusBarsPadding()
                    .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.05f), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = ElectricBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = "CURRENCY CALCULATOR",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        color = ElectricBlue
                    )
                }
            }
        }
    ) { innerPadding ->
        // PullToRefreshBox supports force scroll down gesture to refresh rates
        PullToRefreshBox(
            isRefreshing = isLoading,
            onRefresh = { refreshRates() },
            state = pullToRefreshState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val scaleY = 1f + (pullToRefreshState.distanceFraction * 0.12f).coerceAtMost(0.12f)
            val translateY = pullToRefreshState.distanceFraction * 120f

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        this.scaleY = scaleY
                        this.translationY = translateY
                        this.transformOrigin = androidx.compose.ui.graphics.TransformOrigin(0.5f, 0f)
                    }
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // Real-time status badge with Refresh Action on Right Side
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.02f), shape = RoundedCornerShape(12.dp))
                        .border(1.dp, BorderDb, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(14.dp),
                                color = ElectricBlue,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        } else {
                            // Active Dot: green if online, red if offline
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(
                                        color = if (isError) BearishRed else BullishGreen,
                                        shape = CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        
                        Text(
                            text = if (isError) "Offline [Stored Exchange Rates]" else syncMessage,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isError) BearishRed else if (isLoading) TextSecondary else BullishGreen
                        )
                    }

                    // Manual refresh button
                    IconButton(
                        onClick = { refreshRates() },
                        modifier = Modifier.size(28.dp),
                        enabled = !isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = ElectricBlue,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Real-time Automatic Converter Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
                    border = BorderStroke(1.dp, BorderDb),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // UPPER CURRENCY ROW
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                                .border(1.dp, BorderDb, shape = RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // LEFT SIDE: Currency Dropdown Selector
                            Row(
                                modifier = Modifier
                                    .weight(0.42f)
                                    .clickable { showFromDialog = true },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val details = currencyNames[fromCurrency]
                                Text(
                                    text = details?.second ?: "💵",
                                    fontSize = 24.sp
                                )
                                Column {
                                    Text(
                                        text = fromCurrency,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = details?.first?.take(12) ?: "",
                                        fontSize = 10.sp,
                                        color = TextSecondary,
                                        maxLines = 1
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown",
                                    tint = TextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            // Vertical Separator
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(38.dp)
                                    .background(BorderDb)
                            )
                            Spacer(modifier = Modifier.width(12.dp))

                            // RIGHT SIDE: Input Option (Amount field)
                            BasicTextField(
                                value = amountFrom,
                                onValueChange = { inputVal ->
                                    var clean = inputVal
                                    if (clean.length > 1 && clean.startsWith("0") && !clean.startsWith("0.")) {
                                        clean = clean.substring(1)
                                    }
                                    if (clean.isEmpty()) {
                                        clean = "0"
                                    }
                                    amountFrom = clean
                                    lastEditedBy = "FROM"
                                    amountTo = convertCurrency(clean, fromCurrency, toCurrency)
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                    }
                                ),
                                textStyle = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (lastEditedBy == "FROM") Color.White else BullishGreen,
                                    textAlign = TextAlign.End
                                ),
                                cursorBrush = SolidColor(ElectricBlue),
                                modifier = Modifier.weight(0.58f),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        if (amountFrom.isEmpty()) {
                                            Text(
                                                text = "0",
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = TextSecondary.copy(alpha = 0.5f),
                                                textAlign = TextAlign.End
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )
                        }

                        // SWAP / VICE VERSA BUTTON
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .graphicsLayer { rotationZ = animatedRotation }
                                    .background(ElectricBlue.copy(alpha = 0.15f), shape = CircleShape)
                                    .border(1.dp, ElectricBlue.copy(alpha = 0.4f), shape = CircleShape)
                                    .clickable {
                                        rotationAngle += 180f
                                        val tempCurr = fromCurrency
                                        fromCurrency = toCurrency
                                        toCurrency = tempCurr

                                        // Keep amountFrom unchanged, and recalculate the bottom target amountTo
                                        amountTo = convertCurrency(amountFrom, fromCurrency, toCurrency)
                                        lastEditedBy = "FROM"
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowUpward,
                                        contentDescription = "Up",
                                        tint = ElectricBlue,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ArrowDownward,
                                        contentDescription = "Down",
                                        tint = ElectricBlue,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        // LOWER CURRENCY ROW
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                                .border(1.dp, BorderDb, shape = RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // LEFT SIDE: Currency Dropdown Selector
                            Row(
                                modifier = Modifier
                                    .weight(0.42f)
                                    .clickable { showToDialog = true },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val details = currencyNames[toCurrency]
                                Text(
                                    text = details?.second ?: "💵",
                                    fontSize = 24.sp
                                )
                                Column {
                                    Text(
                                        text = toCurrency,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = details?.first?.take(12) ?: "",
                                        fontSize = 10.sp,
                                        color = TextSecondary,
                                        maxLines = 1
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown",
                                    tint = TextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            // Vertical Separator
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(38.dp)
                                    .background(BorderDb)
                            )
                            Spacer(modifier = Modifier.width(12.dp))

                            // RIGHT SIDE: Input Option (Amount field)
                            BasicTextField(
                                value = amountTo,
                                onValueChange = { inputVal ->
                                    var clean = inputVal
                                    if (clean.length > 1 && clean.startsWith("0") && !clean.startsWith("0.")) {
                                        clean = clean.substring(1)
                                    }
                                    if (clean.isEmpty()) {
                                        clean = "0"
                                    }
                                    amountTo = clean
                                    lastEditedBy = "TO"
                                    amountFrom = convertCurrency(clean, toCurrency, fromCurrency)
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                    }
                                ),
                                textStyle = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (lastEditedBy == "TO") Color.White else BullishGreen,
                                    textAlign = TextAlign.End
                                ),
                                cursorBrush = SolidColor(ElectricBlue),
                                modifier = Modifier.weight(0.58f),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        if (amountTo.isEmpty()) {
                                            Text(
                                                text = "0",
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = TextSecondary.copy(alpha = 0.5f),
                                                textAlign = TextAlign.End
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )
                        }
                    }
                }

                // Real-time Exchange Rate Info Card
                val fromRate = exchangeRates[fromCurrency] ?: 1.0
                val toRate = exchangeRates[toCurrency] ?: 1.0
                val oneFromToTo = toRate / fromRate
                val oneToToFrom = fromRate / toRate
                
                // Compare relative base rates (a smaller USD rate value means stronger purchasing power)
                val isFromStronger = fromRate < toRate

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CardSurfaceDb, shape = RoundedCornerShape(16.dp))
                        .border(1.dp, BorderDb, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "EXCHANGE RATE SUMMARY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricBlue,
                        letterSpacing = 1.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "1 $fromCurrency =",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                        // If fromCurrency is stronger, then toCurrency rate conversion (destination) is weaker -> show in red (BearishRed), else green (BullishGreen)
                        Text(
                            text = String.format(Locale.US, "%.5f %s", oneFromToTo, toCurrency),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (fromRate == toRate) Color.White else if (isFromStronger) BearishRed else BullishGreen
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "1 $toCurrency =",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                        // If fromCurrency is stronger, then fromCurrency rate conversion (destination) is stronger -> show in green (BullishGreen), else red (BearishRed)
                        Text(
                            text = String.format(Locale.US, "%.5f %s", oneToToFrom, fromCurrency),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (fromRate == toRate) Color.White else if (isFromStronger) BullishGreen else BearishRed
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }

    // From Selector Dialog
    if (showFromDialog) {
        CurrencySelectDialog(
            title = "Convert From",
            currencies = currencies,
            currencyNames = currencyNames,
            onDismiss = { showFromDialog = false },
            onSelect = { 
                fromCurrency = it
                if (lastEditedBy == "FROM") {
                    amountTo = convertCurrency(amountFrom, it, toCurrency)
                } else {
                    amountFrom = convertCurrency(amountTo, toCurrency, it)
                }
            }
        )
    }

    // To Selector Dialog
    if (showToDialog) {
        CurrencySelectDialog(
            title = "Convert To",
            currencies = currencies,
            currencyNames = currencyNames,
            onDismiss = { showToDialog = false },
            onSelect = { 
                toCurrency = it
                if (lastEditedBy == "FROM") {
                    amountTo = convertCurrency(amountFrom, fromCurrency, it)
                } else {
                    amountFrom = convertCurrency(amountTo, it, fromCurrency)
                }
            }
        )
    }
}

fun getDefaultRates(): Map<String, Double> {
    return mapOf(
        "USD" to 1.0,
        "EUR" to 0.92,
        "GBP" to 0.79,
        "JPY" to 157.0,
        "AUD" to 1.50,
        "CAD" to 1.37,
        "CHF" to 0.89,
        "INR" to 83.4,
        "PKR" to 278.0,
        "SGD" to 1.35,
        "AED" to 3.67,
        "SAR" to 3.75,
        "CNY" to 7.25,
        "HKD" to 7.81,
        "NZD" to 1.63,
        "BRL" to 5.40,
        "RUB" to 89.0,
        "ZAR" to 18.2,
        "MXN" to 18.0,
        "TRY" to 32.5,
        "BDT" to 117.0
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelectDialog(
    title: String,
    currencies: List<String>,
    currencyNames: Map<String, Pair<String, String>>,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredCurrencies = currencies.filter { code ->
        code.contains(searchQuery, ignoreCase = true) ||
        (currencyNames[code]?.first?.contains(searchQuery, ignoreCase = true) ?: false)
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurfaceDb),
            border = BorderStroke(1.dp, BorderDb),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = ElectricBlue,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Search field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search currency...", color = TextMuted) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = ElectricBlue,
                        unfocusedBorderColor = BorderDb,
                        cursorColor = ElectricBlue
                    ),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredCurrencies) { code ->
                        val nameAndEmoji = currencyNames[code]
                        val name = nameAndEmoji?.first ?: ""
                        val emoji = nameAndEmoji?.second ?: ""

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White.copy(alpha = 0.02f), shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    onSelect(code)
                                    onDismiss()
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = emoji,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = code,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = name,
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

