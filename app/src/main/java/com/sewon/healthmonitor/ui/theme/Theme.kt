package com.sewon.healthmonitor.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sewon.healthmonitor.R

private val DarkColorPalette = darkColorScheme(
    primary = Purple200,
    primaryContainer = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColorScheme(
    primary = Purple500,
    primaryContainer = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DataStoreTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = darkColors()

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}


private val YellowThemeLight = lightColors(
    primary = yellow500,
    primaryVariant = yellow400,
    onPrimary = Color.Black,
    secondary = blue700,
    secondaryVariant = blue800,
    onSecondary = Color.White
)

private val YellowThemeDark = darkColors(
    primary = yellow200,
    secondary = blue200,
    onSecondary = Color.Black,
    surface = yellowDarkPrimary
)

@Composable
fun YellowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        YellowThemeDark
    } else {
        YellowThemeLight
    }
    OwlTheme(darkTheme, colors, content)
}



private val WhiteTheme = lightColors(
    primary = white,
    onPrimary = blue200,
    secondary = yellow200,
    surface = blueDarkPrimary
)


@Composable
fun WhiteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = WhiteTheme
    OwlTheme(darkTheme, colors, content)
}

private val LightElevation = Elevations()

private val DarkElevation = Elevations(card = 1.dp)

private val LightImages = Images(lockupLogo = R.drawable.ic_lockup_blue)

private val DarkImages = Images(lockupLogo = R.drawable.ic_lockup_white)

@Composable
private fun OwlTheme(
    darkTheme: Boolean,
    colors: Colors,
    content: @Composable () -> Unit
) {
    val elevation = if (darkTheme) DarkElevation else LightElevation
    val images = if (darkTheme) DarkImages else LightImages
    CompositionLocalProvider(
        LocalElevations provides elevation,
        LocalImages provides images
    ) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

/**
 * Alternate to [MaterialTheme] allowing us to add our own theme systems (e.g. [Elevations]) or to
 * extend [MaterialTheme]'s types e.g. return our own [Colors] extension
 */
object OwlTheme {

    /**
     * Proxy to [MaterialTheme]
     */
    val colors: Colors
        @Composable
        get() = androidx.compose.material.MaterialTheme.colors

    /**
     * Proxy to [MaterialTheme]
     */
    val typography: Typography
        @Composable
        get() = androidx.compose.material.MaterialTheme.typography

    /**
     * Proxy to [MaterialTheme]
     */
    val shapes: Shapes
        @Composable
        get() = androidx.compose.material.MaterialTheme.shapes

    /**
     * Retrieves the current [Elevations] at the call site's position in the hierarchy.
     */
    val elevations: Elevations
        @Composable
        get() = LocalElevations.current

    /**
     * Retrieves the current [Images] at the call site's position in the hierarchy.
     */
    val images: Images
        @Composable
        get() = LocalImages.current
}
