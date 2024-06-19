package com.example.smartnotetaker.compoments

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import com.example.domain.models.Note

@Composable
fun GraphView(notes: List<Note>, adjacencyMatrix: Array<BooleanArray>, modifier: Modifier) {
    Canvas(modifier = modifier) {
        drawGraph(notes, adjacencyMatrix)
    }
}

fun DrawScope.drawGraph(notes: List<Note>, adjacencyMatrix: Array<BooleanArray>) {
    val nodeRadius = 20f
    val padding = 50f

    val angleStep = 360f / notes.size

    val nodePositions = notes.mapIndexed { index, _ ->
        val angleInRad = Math.toRadians((angleStep * index).toDouble())
        val x = (size.width / 2 + (size.width / 2 - padding) * Math.cos(angleInRad)).toFloat()
        val y = (size.height / 2 + (size.height / 2 - padding) * Math.sin(angleInRad)).toFloat()
        x to y
    }

    adjacencyMatrix.forEachIndexed { i, row ->
        row.forEachIndexed { j, connected ->
            if (connected) {
                val (startX, startY) = nodePositions[i]
                val (endX, endY) = nodePositions[j]
                drawLine(
                    color = Color.Gray,
                    start = androidx.compose.ui.geometry.Offset(startX, startY),
                    end = androidx.compose.ui.geometry.Offset(endX, endY),
                    strokeWidth = 5f
                )
            }
        }
    }

    nodePositions.forEachIndexed { index, (x, y) ->
        drawCircle(
            color = Color.Blue,
            radius = nodeRadius,
            center = androidx.compose.ui.geometry.Offset(x, y)
        )

        drawContext.canvas.nativeCanvas.drawText(
            notes[index].name,
            x,
            y - nodeRadius - 10,  // Adjust the y position to be above the circle
            android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textAlign = android.graphics.Paint.Align.CENTER
                textSize = 40f
            }
        )
    }
}