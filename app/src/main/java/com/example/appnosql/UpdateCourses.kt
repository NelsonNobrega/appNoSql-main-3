package com.example.appnosql

import DBHandler
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appnosql.ui.theme.AppNoSqlTheme
import com.example.appnosql.ui.theme.PurpleGrey40


class UpdateCourses : ComponentActivity() {
    private lateinit var dbHandler: DBHandler // Declare a DBHandler instance


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHandler = DBHandler(this)
        val courseNameToUpdate = intent.getStringExtra("courseName")

        setContent {
            AppNoSqlTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                                title = {
                                    Text(
                                        text = "GFG",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                })
                        }) {
                        UpdateDataInDatabase(LocalContext.current)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateDataInDatabase(
    context: Context
) {
    val registroNomeFromIntent = (context as Activity).intent.getStringExtra("Nome do Animal")
    val registroIdade = remember { mutableStateOf(TextFieldValue()) }
    val registroRaca = remember { mutableStateOf(TextFieldValue()) }
    val registroCorPelagem = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val dbHandler = DBHandler(context)

        Text(
            text = "CRUD - Registro de Animal",
            color = PurpleGrey40,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = registroIdade.value,
            onValueChange = { registroIdade.value = it },
            placeholder = { Text(text = "Idade do Animal") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = registroRaca.value,
            onValueChange = { registroRaca.value = it },
            placeholder = { Text(text = "Raça do Animal") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = registroCorPelagem.value,
            onValueChange = { registroCorPelagem.value = it },
            placeholder = { Text(text = "Cor da Pelagem") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {
            val courseDurationText = registroIdade.value.text
            val courseDescriptionText = registroCorPelagem.value.text
            val courseTracksText = registroRaca.value.text

            val success = dbHandler.updateCourse(
                registroNomeFromIntent ?: "",
                courseDurationText,
                courseDescriptionText,
                courseTracksText
            )


            if (success) {
                Toast.makeText(context, "Registro Updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to Update Registro", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Update Registro", color = Color.White)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {
            val intent = Intent(context, ViewCourses::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Read Registro to Database", color = Color.White)
        }
    }
}
