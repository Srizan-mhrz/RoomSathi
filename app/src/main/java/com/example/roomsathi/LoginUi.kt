package com.example.roomsathi


import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class LoginUi : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Login()
        }
    }
}
@Composable
fun Login() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE)
    val localEmail : String? = sharedPreferences.getString("email","")
    val localPassword : String? = sharedPreferences.getString("password","")

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {

            Spacer(modifier = Modifier.height(60.dp))

            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home Icon",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = "Room Sathi",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Welcome Back!",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Sign in to continue your search for your perfect room",
                style = TextStyle(
                    color = Color.Gray.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))


            OutlinedTextField(
                value = email,
                onValueChange = {data ->
                    email = data
                },
                label = {
                    Text("Email Address")
                },
                shape = RoundedCornerShape(12.dp),
                keyboardOptions= KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                placeholder = {
                    Text( "you@gmail.com")

                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {data->
                    password=data
                }, label = {
                    Text("Password")
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = {rememberMe = it}

                    )
                    Text("Remember me")
                }
                Text(
                    "Forgot password?", style = TextStyle(
                        color = Color.Black.copy(0.6f),
                        fontWeight = FontWeight.SemiBold

                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { if(localEmail == email && localPassword == password){
                    val intent = Intent(
                        context, ProfileActivity::class.java
                    )

                    context.startActivity(intent)

                }else{
                    Toast.makeText(context,"Invalid login", Toast.LENGTH_SHORT).show()
                } },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(100.dp)
                    .padding(horizontal = 12.dp, vertical = 20.dp)
            ) {
                Text("Sign In")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Greeting() {
    Login()

}
