package com.ml.shubham0204.gloveandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ml.shubham0204.glove_android.GloVe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val tabs = listOf("Compare", "Generate")
    private var gloveEmbeddings: GloVe.GloVeEmbeddings? = null

    private val timeTaken = mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ActivityUI() }
        CoroutineScope(Dispatchers.IO).launch {
            val t1 = System.currentTimeMillis()
            GloVe.loadEmbeddings {
                timeTaken.value = (System.currentTimeMillis() - t1).toInt()
                gloveEmbeddings = it
            }
        }
    }

    @Preview
    @Composable
    private fun ActivityUI() {
        Surface(modifier = Modifier.background(Color.White).fillMaxSize()) { TabScreen() }
    }

    // Add tabs
    // Source: https://www.freecodecamp.org/news/tabs-in-jetpack-compose
    @Composable
    private fun TabScreen() {
        var tabIndex by remember { mutableStateOf(0) }
        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        icon = {
                            when (index) {
                                0 ->
                                    Icon(
                                        imageVector = Icons.Default.Face,
                                        contentDescription = "Compare Words"
                                    )
                                1 ->
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = "Compare Words"
                                    )
                            }
                        },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }
            when (tabIndex) {
                0 -> CompareScreen()
                1 -> GenerateScreen()
            }
            TimeTakenDisplay()
        }
    }

    @Composable
    private fun ColumnScope.TimeTakenDisplay() {
        val time by remember { timeTaken }
        Text(
            text = "Time taken: $time ms",
            modifier = Modifier.weight(0.1f).fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ColumnScope.CompareScreen() {
        Column(modifier = Modifier.padding(8.dp).weight(1f).fillMaxSize()) {
            var word1 by rememberSaveable { mutableStateOf("") }
            var word2 by rememberSaveable { mutableStateOf("") }
            var result by rememberSaveable { mutableStateOf("") }
            Row {
                TextField(
                    value = word1,
                    onValueChange = { word1 = it },
                    label = { Text(text = "First word") },
                    modifier =
                        Modifier.weight(1f)
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                            .fillMaxWidth()
                )
                TextField(
                    value = word2,
                    onValueChange = { word2 = it },
                    label = { Text(text = "Second word") },
                    modifier =
                        Modifier.weight(1f)
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                            .fillMaxWidth()
                )
            }
            Text(
                text = "Similarity Score",
                color = Color.LightGray,
                modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 32.dp).fillMaxWidth()
            )
            Text(
                text = result,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(32.dp).fillMaxWidth()
            )
            Button(
                onClick = {
                    if (gloveEmbeddings != null) {
                        val t1 = System.currentTimeMillis()
                        val embedding1 = gloveEmbeddings!!.getEmbedding(word1.lowercase())
                        val embedding2 = gloveEmbeddings!!.getEmbedding(word2.lowercase())
                        if (embedding1.isNotEmpty() && embedding2.isNotEmpty()) {
                            result = GloVe.compare(embedding1, embedding2).toString()
                            timeTaken.value = (System.currentTimeMillis() - t1).toInt()
                        }
                    }
                },
                modifier = Modifier.padding(32.dp).fillMaxWidth()
            ) {
                Text(text = "Compare")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ColumnScope.GenerateScreen() {
        Column(modifier = Modifier.padding(8.dp).weight(1f).fillMaxSize()) {
            var word by rememberSaveable { mutableStateOf("") }
            var embedding by rememberSaveable { mutableStateOf("") }
            TextField(
                modifier = Modifier.padding(32.dp).fillMaxWidth(),
                value = word,
                label = { Text("Enter word") },
                onValueChange = { word = it }
            )
            Button(
                modifier = Modifier.padding(32.dp).fillMaxWidth(),
                onClick = {
                    if (gloveEmbeddings != null) {
                        val t1 = System.currentTimeMillis()
                        embedding = gloveEmbeddings!!.getEmbedding(word).contentToString()
                        timeTaken.value = (System.currentTimeMillis() - t1).toInt()
                    }
                }
            ) {
                Text(text = "Generate Embedding")
            }
            Text(text = "Generated Embedding:\n${embedding}")
        }
    }
}
