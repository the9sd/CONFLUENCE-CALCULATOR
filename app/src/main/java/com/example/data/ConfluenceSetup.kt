package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "confluence_setups")
data class ConfluenceSetup(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val symbol: String,
    val tf1w: String, // "BULLISH", "BEARISH", "NEUTRAL"
    val tf1d: String,
    val tf4h: String,
    val tf1h: String,
    val tf30m: String,
    val tf15m: String,
    val htfBias: String, // "BULLISH", "BEARISH", "NEUTRAL"
    val confluenceLevel: String, // "A+ Setup (Highest Probability)", "A Setup (High Probability)", etc.
    val note: String,
    val aoiTouches: Int = 1,
    val marketStructureConfirmations: String = "",
    val candlestickConfirmations: String = "",
    val patternConfirmations: String = "",
    val newsImpact: String = "NONE",
    val entryConfirmationScore: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
