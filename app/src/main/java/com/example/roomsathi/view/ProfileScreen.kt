package com.example.roomsathi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsathi.ui.theme.*

data class ReviewData(
    val id: Int,
    val userName: String,
    val userImage: Int,
    val rating: Double,
    val comment: String
)

val reviewsList = listOf(
    ReviewData(1, "Kim Borrdy", R.drawable.billieeilish, 4.5, "Amazing! The room is good than the picture. Thanks for amazing experience!"),
    ReviewData(2, "Mirai Kamazuki", R.drawable.billieeilish, 5.0, "The service is on point, and I really like the facilities. Good job!"),
    ReviewData(3, "Jzenklen", R.drawable.billieeilish, 5.0, "The service is on point, and I really like the facilities. Good job!"),
    ReviewData(4, "Rezikan Akay", R.drawable.billieeilish, 5.0, "The service is on point, and I really like the facilities. Good job!"),
    ReviewData(5, "Rezingkaly", R.drawable.billieeilish, 5.0, "The service is on point, and I really like the facilities. Good job!")
)

@Composable
fun GlassSurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val cornerShape = RoundedCornerShape(20.dp)
    Box(
        modifier = modifier
            .clip(cornerShape)
            .border(1.dp, White.copy(alpha = 0.2f), cornerShape)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(White.copy(alpha = 0.1f))
                .blur(25.dp)
        )
        Box(modifier = Modifier.padding(20.dp)) {
            content()
        }
    }
}

class ReviewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReviewsScreen()
        }
    }
}

@Composable
fun ReviewsScreen() {
    Box(modifier = Modifier.fillMaxSize().background(LightBlue)) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                            contentDescription = null,
                            tint = White,
                            modifier = Modifier.size(24.dp)
                        )
                        Text("Reviews", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Icon(
                            painter = painterResource(R.drawable.baseline_settings_24),
                            contentDescription = null,
                            tint = White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    GlassSurface(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("4.4", fontSize = 48.sp, color = White, fontWeight = FontWeight.Bold)
                                Row {
                                    repeat(4) {
                                        Icon(
                                            painter = painterResource(R.drawable.baseline_star_24),
                                            contentDescription = null,
                                            tint = Color(0xFFFFC107),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_star_24),
                                        contentDescription = null,
                                        tint = White.copy(alpha = 0.3f),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Text("Based on 532 review", color = White.copy(alpha = 0.6f), fontSize = 12.sp)
                            }
                            Column(modifier = Modifier.weight(1.2f)) {
                                RatingProgress(5, 0.8f)
                                RatingProgress(4, 0.6f)
                                RatingProgress(3, 0.4f)
                                RatingProgress(2, 0.2f)
                                RatingProgress(1, 0.1f)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Text("Reviews (532)", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(15.dp))
                }
                items(reviewsList) { review ->
                    ReviewCard(review)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun RatingProgress(star: Int, progress: Float) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
        Text(star.toString(), color = White, fontSize = 12.sp, modifier = Modifier.width(12.dp))
        Spacer(modifier = Modifier.width(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.weight(1f).height(6.dp).clip(CircleShape),
            color = Color(0xFFFF1744),
            trackColor = White.copy(alpha = 0.1f)
        )
    }
}

@Composable
fun ReviewCard(review: ReviewData) {
    GlassSurface(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.Top) {
            Image(
                painter = painterResource(review.userImage),
                contentDescription = null,
                modifier = Modifier.size(50.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(review.userName, color = White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_star_24),
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(14.dp)
                        )
                        Text(review.rating.toString(), color = White, fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(review.comment, color = White.copy(alpha = 0.7f), fontSize = 13.sp, lineHeight = 18.sp)
            }
        }
    }
}

@Preview
@Composable
fun PreviewReviews() {
    ReviewsScreen()
}