package com.daoranews

import android.app.Activity
import com.daoranews.ui.theme.DaoraNewsTheme
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daoranews.db.fb.FBDatabase
import com.daoranews.db.fb.toFBUser
import com.daoranews.model.User
import com.daoranews.ui.theme.DataField
import com.daoranews.ui.theme.PasswordField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DaoraNewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterPage(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RegisterPage(modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val activity = context as? Activity

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Crie sua conta", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(24.dp))

        DataField(
            label = "Nome de usuário",
            value = name,
            onValueChange = { name = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DataField(
            label = "E-mail",
            value = email,
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            label = "Senha",
            value = password,
            onValueChange = { password = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            label = "Repita a senha",
            value = repeatPassword,
            onValueChange = { repeatPassword = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        val isButtonEnabled = name.isNotEmpty() && email.isNotEmpty() &&
                password.isNotEmpty() && repeatPassword.isNotEmpty() &&
                password == repeatPassword

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    Firebase.auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                FBDatabase().register(User(name, email).toFBUser())
                                Toast.makeText(activity,
                                    "Registro OK!", Toast.LENGTH_LONG).show()

                            } else {
                                Toast.makeText(activity,
                                    "Registro FALHOU!", Toast.LENGTH_LONG).show()
                            }
                        }
                },
                enabled = isButtonEnabled
            ) {
                Text("Registrar")
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    name = ""
                    email = ""
                    password = ""
                    repeatPassword = ""
                }
            ) {
                Text("Limpar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPagePreview() {
    DaoraNewsTheme {
        RegisterPage()
    }
}