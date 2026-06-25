package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.ConfluenceRepository
import com.example.data.ConfluenceSetup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConfluenceViewModel(private val repository: ConfluenceRepository) : ViewModel() {

    private val _symbol = MutableStateFlow("EURUSD")
    val symbol = _symbol.asStateFlow()

    private val _tf1w = MutableStateFlow("NEUTRAL")
    val tf1w = _tf1w.asStateFlow()

    private val _tf1d = MutableStateFlow("NEUTRAL")
    val tf1d = _tf1d.asStateFlow()

    private val _tf4h = MutableStateFlow("NEUTRAL")
    val tf4h = _tf4h.asStateFlow()

    private val _tf1h = MutableStateFlow("NEUTRAL")
    val tf1h = _tf1h.asStateFlow()

    private val _tf30m = MutableStateFlow("NEUTRAL")
    val tf30m = _tf30m.asStateFlow()

    private val _tf15m = MutableStateFlow("NEUTRAL")
    val tf15m = _tf15m.asStateFlow()

    // AOI touches/status (1 = Not At AOI, 3 = At AOI / Rejected)
    private val _aoiTouches = MutableStateFlow(1)
    val aoiTouches = _aoiTouches.asStateFlow()

    // Entry Confirmations Selected Sets
    private val _selectedMarketStructure = MutableStateFlow(setOf<String>())
    val selectedMarketStructure = _selectedMarketStructure.asStateFlow()

    private val _selectedCandlesticks = MutableStateFlow(setOf<String>())
    val selectedCandlesticks = _selectedCandlesticks.asStateFlow()

    private val _selectedPatterns = MutableStateFlow(setOf<String>())
    val selectedPatterns = _selectedPatterns.asStateFlow()

    // News Filter Selection: "NO_IMPACT", "LOW", "MEDIUM", "HIGH"
    private val _newsImpact = MutableStateFlow("NO_IMPACT")
    val newsImpact = _newsImpact.asStateFlow()

    private val _note = MutableStateFlow("")
    val note = _note.asStateFlow()

    val loggedSetups: StateFlow<List<ConfluenceSetup>> = repository.allSetups
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val liveResult: StateFlow<LiveConfluenceResult> = combine(
        _tf1w, _tf1d, _tf4h, _tf1h, _tf30m, _tf15m,
        _aoiTouches, _selectedMarketStructure, _selectedCandlesticks, _selectedPatterns, _newsImpact
    ) { flowsArray ->
        val tf1w = flowsArray[0] as String
        val tf1d = flowsArray[1] as String
        val tf4h = flowsArray[2] as String
        val tf1h = flowsArray[3] as String
        val tf30m = flowsArray[4] as String
        val tf15m = flowsArray[5] as String
        val aoi = flowsArray[6] as Int
        @Suppress("UNCHECKED_CAST")
        val marketStruct = flowsArray[7] as Set<String>
        @Suppress("UNCHECKED_CAST")
        val candles = flowsArray[8] as Set<String>
        @Suppress("UNCHECKED_CAST")
        val patterns = flowsArray[9] as Set<String>
        val news = flowsArray[10] as String

        calculateConfluence(tf1w, tf1d, tf4h, tf1h, tf30m, tf15m, aoi, marketStruct, candles, patterns, news)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LiveConfluenceResult("NEUTRAL", "No HTF Bias", 0, "Select HTF trends.", emptyList(), 0)
    )

    fun updateSymbol(value: String) { _symbol.value = value.uppercase() }
    fun updateTf1w(value: String) { _tf1w.value = value }
    fun updateTf1d(value: String) { _tf1d.value = value }
    fun updateTf4h(value: String) { _tf4h.value = value }
    fun updateTf1h(value: String) { _tf1h.value = value }
    fun updateTf30m(value: String) { _tf30m.value = value }
    fun updateTf15m(value: String) { _tf15m.value = value }
    fun updateNote(value: String) { _note.value = value }

    fun updateAoiTouches(value: Int) { _aoiTouches.value = value }

    fun toggleMarketStructure(item: String) {
        val current = _selectedMarketStructure.value
        _selectedMarketStructure.value = if (current.contains(item)) current - item else current + item
    }

    fun toggleCandlestick(item: String) {
        val current = _selectedCandlesticks.value
        _selectedCandlesticks.value = if (current.contains(item)) current - item else current + item
    }

    fun togglePattern(item: String) {
        val current = _selectedPatterns.value
        _selectedPatterns.value = if (current.contains(item)) current - item else current + item
    }

    fun updateNewsImpact(value: String) { _newsImpact.value = value }

    fun logCurrentSetup() {
        viewModelScope.launch {
            val result = liveResult.value
            val setup = ConfluenceSetup(
                symbol = _symbol.value.ifBlank { "EURUSD" },
                tf1w = _tf1w.value,
                tf1d = _tf1d.value,
                tf4h = _tf4h.value,
                tf1h = _tf1h.value,
                tf30m = _tf30m.value,
                tf15m = _tf15m.value,
                htfBias = result.htfBias,
                confluenceLevel = result.confluenceLevel,
                note = _note.value,
                aoiTouches = _aoiTouches.value,
                marketStructureConfirmations = _selectedMarketStructure.value.joinToString(","),
                candlestickConfirmations = _selectedCandlesticks.value.joinToString(","),
                patternConfirmations = _selectedPatterns.value.joinToString(","),
                newsImpact = _newsImpact.value,
                entryConfirmationScore = result.entryConfirmationScore
            )
            repository.insert(setup)
            // Reset entry confirmations and notes
            _selectedMarketStructure.value = emptySet()
            _selectedCandlesticks.value = emptySet()
            _selectedPatterns.value = emptySet()
            _note.value = ""
        }
    }

    fun deleteSetup(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }

    fun clearAllSetups() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    private fun calculateConfluence(
        tf1w: String, tf1d: String, tf4h: String,
        tf1h: String, tf30m: String, tf15m: String,
        aoi: Int,
        marketStruct: Set<String>,
        candles: Set<String>,
        patterns: Set<String>,
        news: String
    ): LiveConfluenceResult {
        var htfBias = "NEUTRAL"
        var isTripleHtf = false
        var isDoubleHtf = false

        if (tf1w == tf1d && tf1d == tf4h && tf1w != "NEUTRAL") {
            htfBias = tf1w
            isTripleHtf = true
        } else if (tf1d == tf4h && tf1d != "NEUTRAL") {
            htfBias = tf1d
            isDoubleHtf = true
        } else if (tf1w == tf1d && tf1w != "NEUTRAL") {
            htfBias = tf1w
            isDoubleHtf = true
        }

        // Calculate Entry Confirmation Score: Each confirmation out of 12 increases the score
        val totalConfirmationsCount = marketStruct.size + candles.size + patterns.size
        // Maximum = 100, 12 possible options, let's make it proportional:
        val entryConfirmationScore = if (totalConfirmationsCount == 0) {
            0
        } else {
            // Distribute evenly but cap at 100
            minOf(100, (totalConfirmationsCount * 100) / 12)
        }

        val details = mutableListOf<String>()

        if (htfBias == "NEUTRAL") {
            return LiveConfluenceResult(
                htfBias = "NEUTRAL",
                confluenceLevel = "No Clear HTF Bias",
                probabilityPercentage = 15,
                description = "No consecutive Higher Time Frame trends align (requires 1W & 1D, or 1D & 4H to match). Avoid entries.",
                details = listOf("⚠️ No consecutive Higher Time Frames (1W, 1D, 4H) align."),
                entryConfirmationScore = entryConfirmationScore
            )
        }

        details.add("✅ HTF Trend: $htfBias")
        if (isTripleHtf) {
            details.add("🔥 S-Tier Trend: 1W, 1D, and 4H fully aligned.")
        } else if (isDoubleHtf) {
            details.add("⚡ A-Tier Trend: 2 consecutive Higher Time Frames aligned.")
        }

        val ltfs = listOf(tf1h, tf30m, tf15m)
        val alignedLtfCount = ltfs.count { it == htfBias }
        
        details.add("📊 Entry Alignments: $alignedLtfCount of 3 ($tf1h, $tf30m, $tf15m)")

        // Base Probability calculation
        var prob = if (isTripleHtf) {
            when (alignedLtfCount) {
                3 -> 90
                2 -> 80
                1 -> 70
                else -> 60
            }
        } else {
            when (alignedLtfCount) {
                3 -> 75
                2 -> 65
                1 -> 50
                else -> 35
            }
        }

        // Adjust based on AOI status (At AOI / Rejected starts from 3 touches)
        if (aoi >= 3) {
            prob += 15
            details.add("🎯 Price at Area of Interest (AOI) / Rejected zone (+15% boost).")
        } else {
            details.add("⚪ Not currently at Area of Interest (AOI) / Rejected (no score boost).")
        }

        // Adjust based on Entry Confirmations Score
        if (entryConfirmationScore >= 75) {
            prob += 8
            details.add("🎯 Entry Confirmations: Extreme alignment ($entryConfirmationScore%)")
        } else if (entryConfirmationScore >= 45) {
            prob += 4
            details.add("🎯 Entry Confirmations: solid strength ($entryConfirmationScore%)")
        } else if (entryConfirmationScore == 0) {
            prob -= 15
            details.add("⚠️ Entry Confirmations: No confirmation patterns checked.")
        } else {
            details.add("🎯 Entry Confirmations Score: $entryConfirmationScore%")
        }

        // Adjust/Add news warnings - Low impact on probability
        when (news) {
            "LOW" -> {
                prob -= 2
                details.add("⚠️ News Filter: Low impact (minor -2% change).")
            }
            "MEDIUM" -> {
                prob += 2
                details.add("🟠 News Filter: Medium impact (minor +2% change).")
            }
            "HIGH" -> {
                prob += 5
                details.add("🚨 News Filter: High impact (small +5% change).")
            }
            else -> {
                details.add("⚪ News Filter: No impact news today (no change to probability).")
            }
        }

        // Keep probability within bounds [0..100]
        val probabilityPercentage = prob.coerceIn(5, 100)

        val confluenceLevel: String
        val description: String

        if (probabilityPercentage >= 90) {
            confluenceLevel = "A+ Setup (Highest Probability)"
            description = "Outstanding setup! S-Tier Higher Time Frames, verified Area of Interest touches, pristine lower time frame alignment, and multi-pattern confirmations present extremely minimal entry risk."
        } else if (probabilityPercentage >= 78) {
            confluenceLevel = "A Setup (High Probability)"
            description = "Solid high probability setup. HTF bias supports the move and LTF entries have aligned on major structural patterns. Safe to trade with standard risk parameters."
        } else if (probabilityPercentage >= 65) {
            confluenceLevel = "B+ Setup (Moderate Confluence)"
            description = "Moderate confluence setup. Higher time frames align but you have minor counter-indications (e.g. low touch count, pulling back entries, or mild news risk). Consider sizing down."
        } else if (probabilityPercentage >= 50) {
            confluenceLevel = "B Setup (Average Probability)"
            description = "Average probability. Fits baseline criteria but entry validation remains weak. High chance of choppy drawdowns. Wait for more lower time frame candles to close in direction."
        } else {
            confluenceLevel = "C Setup (No-Trade Zone)"
            description = "Extremely low probability setup. High likelihood of failure. Higher time frames do not align sufficiently, AOI touches are scarce, or high impact news threatens extreme slippage. Stand aside."
        }

        return LiveConfluenceResult(
            htfBias = htfBias,
            confluenceLevel = confluenceLevel,
            probabilityPercentage = probabilityPercentage,
            description = description,
            details = details,
            entryConfirmationScore = entryConfirmationScore
        )
    }
}

data class LiveConfluenceResult(
    val htfBias: String,
    val confluenceLevel: String,
    val probabilityPercentage: Int,
    val description: String,
    val details: List<String>,
    val entryConfirmationScore: Int
)

class ConfluenceViewModelFactory(private val repository: ConfluenceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfluenceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConfluenceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

