package com.richpath

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.richpath.model.Group

@Composable
fun RichPathCompose(
    modifier: Modifier = Modifier,
    @DrawableRes vectorId: Int,
    scaleType: ScaleType = ImageView.ScaleType.FIT_XY,
    onLoad: (RichPathOperations) -> Unit,
    onPathClick: (group: Group?,path: RichPath) -> Unit = {_,_ ->}
) {

    Box(modifier = Modifier) {
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
        }, modifier = modifier, update = {view ->
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
                    },
                    allGroups = {
                        view.findAllRichGroups()
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
    val allGroups: () -> Array<Group>,
    val findFirstPath: () -> RichPath?,
    val findPathByIndex: (index: Int) -> RichPath?,
    val addPath: (path: android.graphics.Path) -> Unit
)


