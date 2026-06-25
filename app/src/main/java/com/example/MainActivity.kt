package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.ui.theme.BullishGreen
import com.example.ui.theme.BearishRed
import com.example.ui.theme.GoldGold
import com.example.ui.theme.DeepSpaceDb
import com.example.ui.theme.CardSurfaceDb
import com.example.ui.theme.BorderDb
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.MyApplicationTheme
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
                    delay(2200) // Beautiful 2.2 second splash as requested
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
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Center visual icon showing Candlesticks + Rings
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scale).alpha(alpha)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(140.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(ElectricBlue.copy(alpha = 0.2f), Color.Transparent)
                        )
                    )
            ) {
                // Draw a beautiful confluence geometry on Canvas
                Canvas(modifier = Modifier.size(100.dp)) {
                    val radius1 = 45f
                    val radius2 = 30f
                    
                    // Golden Circle
                    drawCircle(
                        color = GoldGold,
                        radius = radius1,
                        style = Stroke(width = 6f)
                    )
                    // Inner Teal Circle
                    drawCircle(
                        color = BullishGreen,
                        radius = radius2,
                        style = Stroke(width = 4f)
                    )
                    
                    // Abstract candlestick lines
                    // Bullish candle on left
                    drawLine(
                        color = BullishGreen,
                        start = androidx.compose.ui.geometry.Offset(size.width * 0.4f, size.height * 0.25f),
                        end = androidx.compose.ui.geometry.Offset(size.width * 0.4f, size.height * 0.75f),
                        strokeWidth = 4f
                    )
                    drawRect(
                        color = BullishGreen,
                        topLeft = androidx.compose.ui.geometry.Offset(size.width * 0.33f, size.height * 0.4f),
                        size = androidx.compose.ui.geometry.Size(size.width * 0.14f, size.height * 0.2f)
                    )

                    // Bearish candle on right
                    drawLine(
                        color = BearishRed,
                        start = androidx.compose.ui.geometry.Offset(size.width * 0.6f, size.height * 0.25f),
                        end = androidx.compose.ui.geometry.Offset(size.width * 0.6f, size.height * 0.75f),
                        strokeWidth = 4f
                    )
                    drawRect(
                        color = BearishRed,
                        topLeft = androidx.compose.ui.geometry.Offset(size.width * 0.53f, size.height * 0.45f),
                        size = androidx.compose.ui.geometry.Size(size.width * 0.14f, size.height * 0.2f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "CONFLUENCE",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 6.sp,
                color = TextPrimary,
                fontFamily = FontFamily.SansSerif
            )
            
            Text(
                text = "Trading Alignment Matrix",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = TextSecondary,
                letterSpacing = 2.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Built by Srijan credit exactly as requested
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("built with ❤️ by ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = TextPrimary)) {
                        append("SRIJAN")
                    }
                },
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "v1.0.0 • Offline Terminal",
                fontSize = 11.sp,
                color = TextMuted,
                letterSpacing = 1.sp
            )
        }
    }
}

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

    var marketStructureExpanded by remember { mutableStateOf(false) }
    var candlesticksExpanded by remember { mutableStateOf(false) }
    var patternsExpanded by remember { mutableStateOf(false) }

    if (showResultsPage) {
        ConfluenceResultsPage(
            result = liveResult,
            onBack = { showResultsPage = false }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
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
                                    text = "CONFLUENCE CALCULATOR",
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
                                    text = "Calculate confluence & trade probability",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                            IconButton(
                                onClick = { isDrawerOpen = true },
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.White.copy(alpha = 0.05f), shape = CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu Panel",
                                    tint = ElectricBlue,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
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
                                .clickable {
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
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
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
                            
                            // Confirmation Score Badge
                            Box(
                                modifier = Modifier
                                    .background(ElectricBlue.copy(alpha = 0.15f), shape = RoundedCornerShape(6.dp))
                                    .border(1.dp, ElectricBlue.copy(alpha = 0.5f), shape = RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Score: ${liveResult.entryConfirmationScore}/100",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ElectricBlue
                                )
                            }
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
                            options = listOf("Engulfing", "Pin Bar", "Rejection Candle", "Inside Bar"),
                            selectedItems = selectedCandlesticks,
                            onToggleItem = { viewModel.toggleCandlestick(it) },
                            isExpanded = candlesticksExpanded,
                            onExpandedChange = { candlesticksExpanded = it }
                        )
                        
                        Spacer(modifier = Modifier.height(14.dp))
                        
                        // Category 3: Patterns Dropdown
                        MultiSelectDropdown(
                            categoryTitle = "Patterns",
                            options = listOf("Double Top", "Double Bottom", "Head & Shoulders", "Inverse Head & Shoulders"),
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
                                        .clickable { viewModel.updateNewsImpact(value) }
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
                Button(
                    onClick = { showResultsPage = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ElectricBlue
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
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
                            text = "CALCULATE CONFLUENCE",
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
                            append("Build with ❤️by ")
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
        
        RightSidePanel(
            isOpen = isDrawerOpen,
            onClose = { isDrawerOpen = false }
        )
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
                        .clickable { onStateChanged("BULLISH") }
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
                        .clickable { onStateChanged("NEUTRAL") }
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
                        .clickable { onStateChanged("BEARISH") }
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
        modifier = modifier.clickable { onToggle() }
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
                    .clickable { onExpandedChange(!isExpanded) }
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
                        text = "CONFLUENCE REPORT",
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
                            text = "CONFLUENCE",
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
    result: LiveConfluenceResult,
    onBack: () -> Unit
) {
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
                    text = "CONFLUENCE REPORT",
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
                    append("Build with ❤️by ")
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
    var logicExpanded by remember { mutableStateOf(false) }
    var warningExpanded by remember { mutableStateOf(false) }

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
                    .background(CardSurfaceDb)
                    .border(
                        1.dp,
                        BorderDb,
                        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                    )
                    .clickable(enabled = false) {}
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "INFO PANEL",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ElectricBlue,
                        letterSpacing = 1.5.sp
                    )
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

                Spacer(modifier = Modifier.height(24.dp))

                // Calculator Logic Expandable Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                        .border(
                            1.dp,
                            if (logicExpanded) ElectricBlue.copy(alpha = 0.5f) else BorderDb,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { logicExpanded = !logicExpanded }
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Logic Info",
                                tint = ElectricBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Calculator Logic",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Expand Logic",
                            tint = TextSecondary,
                            modifier = Modifier
                                .size(20.dp)
                                .scale(1f, if (logicExpanded) -1f else 1f)
                        )
                    }

                    if (logicExpanded) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "This Confluence Calculator establishes high probability market trade setups by analyzing multiple factors:\n\n" +
                                    "• Higher Time Frames (HTF):\n" +
                                    "Assess Daily (D), Weekly (W), and 4-Hour (4H) structures to establish trend bias, liquidity, and AOI zones.\n\n" +
                                    "• Lower Time Frames (LTF):\n" +
                                    "Assess 1-Hour (1H), 30-Min (30M), and 15-Min (15M) structures to target precision entry triggers.\n\n" +
                                    "• Entry Confirmations:\n" +
                                    "Includes Market Structure shifts (BOS, CHoCH), Candlestick triggers (Engulfing, Pin Bar), and Chart Patterns (Double Top, Double Bottom) which score from 0 to 100 points.",
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Warning Expandable Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                        .border(
                            1.dp,
                            if (warningExpanded) BearishRed.copy(alpha = 0.5f) else BorderDb,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { warningExpanded = !warningExpanded }
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Usage Warning",
                                tint = BearishRed,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Confluence Usage Warning",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Expand Warning",
                            tint = TextSecondary,
                            modifier = Modifier
                                .size(20.dp)
                                .scale(1f, if (warningExpanded) -1f else 1f)
                        )
                    }

                    if (warningExpanded) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "TRADING RISK WARNING:\n\n" +
                                    "• Financial instruments, foreign exchange (Forex), commodities, and cryptocurrencies carry extreme risks of rapid financial loss.\n\n" +
                                    "• Confluence percentages represent mathematical probabilities based on historical parameters, not a guarantee of future profits.\n\n" +
                                    "• Never risk capital that you cannot afford to lose completely.\n\n" +
                                    "• Use risk management rules such as proper position sizing and stop-loss orders in combination with any confluence setup reports.",
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}

