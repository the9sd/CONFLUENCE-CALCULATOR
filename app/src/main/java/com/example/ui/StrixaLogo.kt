package com.example.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.R

/**
 * A reusable logo component for STRIXA AI.
 * Displays the branding logo asset. Updates automatically if the underlying resource is updated.
 */
@Composable
fun StrixaLogo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_strixa_logo),
        contentDescription = "STRIXA AI Logo",
        modifier = modifier
    )
}
