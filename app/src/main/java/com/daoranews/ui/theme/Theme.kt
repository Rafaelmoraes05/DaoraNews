package com.daoranews.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkKindleScheme = darkColorScheme(
    primary = KindleOffWhite,
    onPrimary = KindleDark,
    background = KindleDark,
    surface = KindleDark,
    onBackground = KindleOffWhite,
    onSurface = KindleOffWhite,
    secondary = KindleDarkGray,
    outline = KindleDarkGray
)

// Paleta para o Tema Claro (Modo Padrão) usando as cores Kindle
private val LightKindleScheme = lightColorScheme(
    primary = KindleBlack,
    onPrimary = KindleWhite,
    background = KindleWhite,
    surface = KindleWhite,
    onBackground = KindleBlack,
    onSurface = KindleBlack,
    secondary = KindleGray,
    outline = KindleLightGray
)

@Composable
fun DaoraNewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),

    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkKindleScheme
        else -> LightKindleScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}