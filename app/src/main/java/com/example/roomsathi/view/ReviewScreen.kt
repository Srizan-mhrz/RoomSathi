package com.example.roomsathi.view

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
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
import com.example.roomsathi.R

@Composable
fun GlassSurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val cornerShape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .clip(cornerShape)
            .border(
                width = 1.dp,
                color = White.copy(alpha = 0.3f),
                shape = cornerShape
            )
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(White.copy(alpha = 0.15f))
                .blur(20.dp)
        )

        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

data class Review(
    val id: Int,
    val userName: String,
    val userImageRes: Int,
    val rating: Double,
    val comment: String
)

data class RatingDistribution(
    val star: Int,
    val percentage: Float
)

val dummyReviews = listOf(
    Review(1, "Kim Borrdy", R.drawable.billieeilish, 4.5, "Amazing! The room is good than the picture. Thanks for amazing experience!"),
    Review(2, "Mirai Kamazuki", R.drawable.billieeilish, 5.0, "The service is on point, and I really like the facilities. Good job!"),
    Review(3, "Jzenklen", R.drawable.billieeilish, 5.0, "The service is on point, and I really like the facilities. Good job!"),
    Review(4, "Rezikan Akay", R.drawable.billieeilish, 5.0, "The service is on point, and I really like the facilities. Good job!"),
    Review(5, "Rezingkaly", R.drawable.billieeilish, 5.0, "The service is on point, and I really like the facilities. Good job!")
)

val dummyDistribution = listOf(
    RatingDistribution(5, 0.7f),
    RatingDistribution(4, 0.2f),
    RatingDistribution(3, 0.05f),
    RatingDistribution(2, 0.03f),
    RatingDistribution(1, 0.02f)
)

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
    val backgroundColor = LightBlue

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        containerColor = Color.Transparent,
        topBar = {
            ReviewsHeader(onBackClick = { })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                GlassSurface(modifier = Modifier.fillMaxWidth()) {
                    RatingSummaryContent(
                        averageRating = 4.4,
                        totalReviews = 532,
                        distribution = dummyDistribution
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Reviews (532)",
                    style = TextStyle(color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            items(dummyReviews) { review ->
                ReviewItemCard(review = review)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsHeader(onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Reviews",
                style = TextStyle(color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_settings_24),
                    contentDescription = "Filter",
                    tint = White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun RatingSummaryContent(
    averageRating: Double,
    totalReviews: Int,
    distribution: List<RatingDistribution>
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(0.4f)
        ) {
            Text(
                text = averageRating.toString(),
                style = TextStyle(color = White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
            )
            StarRatingBar(rating = averageRating)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Based on $totalReviews review",
                style = TextStyle(color = White.copy(alpha = 0.7f), fontSize = 14.sp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(0.6f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            distribution.forEach { dist ->
                RatingBarRow(star = dist.star, percentage = dist.percentage)
            }
        }
    }
}

@Composable
fun StarRatingBar(rating: Double) {
    Row {
        repeat(5) { index ->
            val starColor = if (index < rating.toInt()) Color(0xFFFFC107)
            else if (index < rating && (index + 1) > rating) Color(0xFFFFC107).copy(alpha = 0.5f)
            else White.copy(alpha = 0.3f)
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun RatingBarRow(star: Int, percentage: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = star.toString(),
            style = TextStyle(color = White, fontSize = 14.sp),
            modifier = Modifier.width(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(White.copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFFFC107))
            )
        }
    }
}

@Composable
fun ReviewItemCard(review: Review) {
    GlassSurface(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(verticalAlignment = Alignment.Top) {
                Image(
                    painter = painterResource(review.userImageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = review.userName,
                            style = TextStyle(color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = review.rating.toString(),
                                style = TextStyle(color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = review.comment,
                        style = TextStyle(color = White.copy(alpha = 0.8f), fontSize = 14.sp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewsScreenPreview() {
    ReviewsScreen()
}