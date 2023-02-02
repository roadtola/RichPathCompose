package com.richpath

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.richpath.model.Group
import com.richpath.model.Vector
import com.richpath.util.XmlParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.nio.file.Path

@Composable
fun RichPathCompose(
    modifier: Modifier = Modifier,
    @DrawableRes vectorId: Int,
    scaleType: ScaleType = ImageView.ScaleType.FIT_XY,
    onLoad: (RichPathOperations) -> Unit,
    onPathClick: (group: Group?,path: RichPath) -> Unit = {_,_ ->}
) {

    Box(modifier = modifier) {
        AndroidView(factory = { ctx ->
            val view = RichPathView(ctx, null).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                this.scaleType = scaleType
                setVectorDrawable(vectorId)
                onPathClickListener = object : RichPath.OnPathClickListener {
                    override fun onClick(richGroup: Group?, richPath: RichPath) {
                        onPathClick(richGroup,richPath)
                    }
                }
            }
            view
        }, modifier = Modifier, update = {view ->
            onLoad(
                RichPathOperations(
                    findPath = {
                        view.findRichPathByName(it)
                    },
                    allPaths = {
                        view.findAllRichPaths()
                    },
                    findFirstPath = {
                        view.findFirstRichPath()
                    },
                    findPathByIndex = {
                        view.findRichPathByIndex(it)
                    },
                    addPath = {
                        view.addPath(it)
                    },
                    findGroup = {
                        view.findRichGroupByName(it)
                    }
                )
            )
        })

    }
}

data class RichPathOperations(
    val findPath: (name: String) -> RichPath?,
    val findGroup: (name: String) -> Group?,
    val allPaths: () -> Array<RichPath>,
    val findFirstPath: () -> RichPath?,
    val findPathByIndex: (index: Int) -> RichPath?,
    val addPath: (path: android.graphics.Path) -> Unit
)


