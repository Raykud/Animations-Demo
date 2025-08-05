package com.tool.motion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tool.motion.ui.theme.AnimationsDemoTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimationsDemoTheme {
                var currentAnimationToShow by remember { mutableStateOf<String?>(null) }
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                if (currentAnimationToShow == null) "Motion Demo"
                                else "Example: $currentAnimationToShow"
                            )
                        },
                        navigationIcon = {
                            // Show back button if we are viewing an example
                            if (currentAnimationToShow != null) {
                                IconButton(onClick = { currentAnimationToShow = null }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back to list"
                                    )
                                }
                            }
                        }
                    )
                }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        if (currentAnimationToShow == null) {
                            // Show the list of animations
                            AnimationListScreenContent(
                                onItemClick = { animationName: String ->
                                    currentAnimationToShow = animationName
                                  },
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            // Show the selected animation example
                            AnimationExamplesScreen(
                                animationType = currentAnimationToShow,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimationListScreenContent(onItemClick: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
            text = "List of composable animations",
            fontWeight = FontWeight.Bold
        )
        AnimationListsItems(
            onItemClick = onItemClick // Pass the callback directly
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationListsItems(onItemClick: (String) -> Unit) {
    val animationList = listOf("Appear and Disappear", "Change Colour", "Size", "Position", "Padding", "Elevation", "Animate Text Colour", "Smooth Animate Text")
    LazyColumn {
        items(animationList.size) { index ->
            val itemText = animationList[index]
            ListItem(
                headlineContent = { Text(itemText) },
                modifier = Modifier
                    .fillMaxWidth() // So that the click works on the entire row
                    .clickable {
                        onItemClick(itemText) // When clicked, currentAnimationToShow is updated
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp) // Add some vertical padding
            )
        }
    }
}


@Composable
fun AnimationExamplesScreen(animationType: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // To center the examples
    ) {
        if (animationType != null) {
            Text(
                text = "Animation Example:",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Use 'when' to decide which animation example to show
            when (animationType) {
                "Appear and Disappear" -> AnimatedVisibilityCookbook()
                "Appear and Disappear (Alpha)" -> AnimatedVisibilityCookbook_ModifierAlpha()
                "Change Colour" -> AnimateBackgroundColor()
                "Size" -> AnimateSizeChange()
                "Position" -> AnimateOffset()
                "Padding" -> AnimatePadding()
                "Elevation" -> AnimateElevation()
                "Animate Text Colour" -> AnimateTextColor()
                "Smooth Animate Text" -> SmoothAnimateText()
                else -> Text("Unknown animation type: $animationType")
            }
        } else {
            Text("No animation type provided.")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreviewShowingList() {
    AnimationsDemoTheme {
        var currentAnimationToShow by remember { mutableStateOf<String?>(null) }
        Scaffold(
            topBar = { TopAppBar(title = { Text(if (currentAnimationToShow == null) "Animations Demo" else "Example: $currentAnimationToShow") }) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                if (currentAnimationToShow == null) {
                    AnimationListScreenContent(onItemClick = { currentAnimationToShow = it })
                } else {
                    AnimationExamplesScreen(animationType = currentAnimationToShow)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreviewShowingExample() {
    AnimationsDemoTheme {
        var currentAnimationToShow by remember { mutableStateOf<String?>("Size") } // Example: Size
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(if (currentAnimationToShow == null) "Motion Demo" else "Example: $currentAnimationToShow") },
                    navigationIcon = {
                        if (currentAnimationToShow != null) {
                            IconButton(onClick = { currentAnimationToShow = null }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                if (currentAnimationToShow == null) {
                    AnimationListScreenContent(onItemClick = { currentAnimationToShow = it })
                } else {
                    AnimationExamplesScreen(animationType = currentAnimationToShow)
                }
            }
        }
    }
}