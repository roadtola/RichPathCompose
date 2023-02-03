package com.roatola.composesample

import android.graphics.Color
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.richpath.RichPath
import com.richpath.RichPathCompose
import com.richpathanimator.AnimationListener
import com.richpathanimator.RichPathAnimator
import com.roatola.composesample.ui.theme.RichpathforkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RichpathforkTheme {
                // A surface container using the 'background' color from the theme
                var text by remember { mutableStateOf("Group: .${"\n"}Path: ") }
                var selectedGroup by remember { mutableStateOf<Array<RichPath>>(arrayOf()) }
//                var selectedGroupColor by remember(selectedGroup) { mutableStateOf<Int>(sele) }
                var allPaths by remember { mutableStateOf<Array<RichPath>>(arrayOf()) }
                var size by remember { mutableStateOf(false) }

                LaunchedEffect(key1 = allPaths) {
                    val skin = allPaths.firstOrNull { it.name == "skin" }!!
                    RichPathAnimator.animate(*allPaths)
                        .animationListener(
                            onStart = {
                                allPaths.forEach { it.fillAlpha = 0f }
                            },
                            onStop = {

                            }
                        )
                        .thenAnimate(skin)
                        .fillAlpha(0.3f)
                        .thenAnimate(skin)
                        .fillAlpha(0.9f)
                        .interpolator(AccelerateInterpolator())
                        .duration(400)
                        .start()
                }

                LaunchedEffect(key1 = selectedGroup) {
                    RichPathAnimator.animate(*allPaths.filter { it.name != "skin" }.toTypedArray())
                        .fillAlpha(0.3f)
                        .duration(100)
                        .thenAnimate(*selectedGroup)
                        .animationListener(
                            onStart = {
                                allPaths.forEach { it.clearFillColor() }
                            },
                            onStop = {
                            }
                        )
                        .fillAlpha(0.9f)
                        .fillColor(Color.BLUE)
                        .duration(500)
                        .start()
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RichPathCompose(
                            modifier = Modifier
                                .height(if(size) 400.dp else 300.dp)
                                .width(if(size) 200.dp else 150.dp),
                            vectorId = R.drawable.ic_front_muscles,
                            scaleType = ImageView.ScaleType.FIT_CENTER,
                            onLoad = {
                                //after vector is loaded
                                allPaths = it.allPaths()
                                it.findGroup
                            },
                            onPathClick = { group, path ->
                                //clicked path and group if exists
                                text = "Group: ${group?.name}.${"\n"}Path: ${path.name}"
                                selectedGroup = group?.paths?.toTypedArray() ?: arrayOf()
                            }
                        )
                        Text(
                            text = text, modifier = Modifier
                                .weight(0.5f)
                                .wrapContentWidth()
                                .clickable {
                                    size = !size
                                }
                        )
                    }
                }
            }
        }
    }
}
