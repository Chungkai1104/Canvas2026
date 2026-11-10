package tw.edu.pu.csim.s1141235.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import tw.edu.pu.csim.s1141235.myapplication.ui.theme.Canvas2026Theme
import java.nio.file.Files.size

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Canvas2026Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CanvasScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun CanvasScreen(modifier: Modifier = Modifier) {

    var CanvansWidth by remember { mutableStateOf(0f) }
    var CanvanHeight by remember { mutableStateOf(0f) }

    var minionsX by remember { mutableStateOf(0f) }
    var minionsY by remember { mutableStateOf(0f) }

    var gameOver by remember { mutableStateOf(false) }

    // 載入圖片資源 (小小兵)
    val minionsImage: ImageBitmap =
        ImageBitmap.imageResource(id = R.drawable.minions)

    LaunchedEffect(gameOver) {
        while (!gameOver) {
            delay(1L)
            minionsX += 1
            minionsY += 1

            if (CanvansWidth > 0f && CanvanHeight > 0f) {
                if (minionsX > CanvansWidth || minionsY > CanvanHeight) {
                    minionsX = 0f
                    minionsY = 0f
                }
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = modifier.fillMaxSize()
        ) {
//繪製洋紅色的圓, 半徑 100, 圓心 (100,100)
            drawCircle(
                Color.Magenta,
                100f,
                Offset(100f, 100f)
            )
//繪製藍色空心矩形, 左上角 (300,200), 寬度與高度 200
            drawRect(
                Color.Blue,
                Offset(300f, 200f),
                Size(200f, 200f),
                style = Stroke(width = 5f) // 使用 style 控制外框樣式
            )

            CanvansWidth = size.width
            CanvanHeight = size.height
            drawLine(
                Color.Red,
                Offset(200f, 800f),
                Offset(size.width, size.height),
                strokeWidth = 10f
            )


            // 將畫布起點移至置中位置後畫圖
            translate(minionsX, minionsY) {
                drawImage(minionsImage)
            }
        }
    }
    Column {
        Text("畫布寬度 $CanvansWidth, 長度 $CanvanHeight")

        Button(
            onClick = { gameOver = !gameOver }
        ) {
            if (gameOver) {Text("繼續遊戲") }
            else {Text("停止遊戲")}
        }
        }
    }
