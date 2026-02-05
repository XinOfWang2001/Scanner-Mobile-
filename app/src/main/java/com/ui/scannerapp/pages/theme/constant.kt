package com.ui.scannerapp.pages.theme

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

val textPad = Modifier.padding(all = 4.dp)
val spaceHeight = Modifier.height(4.dp)
val mediumWidth = Modifier.width(8.dp)
val spaceHeightSmall = Modifier.padding(horizontal = 1.2.dp, vertical = 2.dp)
val ImageFormat = Modifier.size(40.dp).clip(CircleShape)
val animationContentSize = Modifier.animateContentSize().padding(1.dp)