package com.example.propfinder.presentation.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import com.example.propfinder.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.propfinder.presentation.main.Template
import com.example.propfinder.presentation.viewmodels.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LoginPage(navController: NavHostController, authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.propfinder),
                contentDescription = "Logo",
                modifier = Modifier.size(128.dp)
            )


            Text(
                text = "Connexion",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("votre.email@example.com") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mot de passe") },
                placeholder = { Text("••••••••") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))


            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        authViewModel.signIn(
                            email,
                            password,
                            onSuccess = {
                                navController.navigate("main")
                            },
                            onFailure = {
                                Toast.makeText(
                                    context,
                                    "Email ou mot de passe incorrect",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    } else {
                        Toast.makeText(
                            context,
                            "L’email et le mot de passe sont obligatoires",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF07B42)),
            ) {
                Text("Se connecter", color = Color(0xFF1E1E1E))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("register") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("signup_button"),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF07B42)),
            ) {
                Text("Créer un compte", color = Color(0xFF1E1E1E))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mot de passe oublié ?",
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun RegisterPage(navController: NavHostController, authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    val context = LocalContext.current

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.propfinder),
                contentDescription = "Logo",
                modifier = Modifier.size(128.dp)
            )

            Text(
                text = "Créer un compte",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("votre.email@example.com") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("email_field"),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Nom") },
                placeholder = { Text("Dupont") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("nom_field"),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("Prénom") },
                placeholder = { Text("Jean") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("prenom_field"),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") },
                placeholder = { Text("50") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("age_field"),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mot de passe") },
                placeholder = { Text("••••••••") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("password_field"),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    when {
                        email.isBlank() || password.isBlank() -> {
                            Toast.makeText(context, "Email et mot de passe obligatoires", Toast.LENGTH_SHORT).show()
                        }
                        !isEmailValid(email) -> {
                            Toast.makeText(context, "Format de l'email invalide", Toast.LENGTH_SHORT).show()
                        }
                        password.length < 6 -> {
                            Toast.makeText(context, "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            authViewModel.signUp(
                                email = email,
                                password = password,
                                age = age,
                                prenom = firstName,
                                nom = lastName,
                                onSuccess = {
                                    navController.navigate("main")
                                }
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .testTag("create_account_button"),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF07B42)),
            ) {
                Text("Créer votre compte", color = Color(0xFF1E1E1E))
            }

            OutlinedButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.padding(5.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFF07B42)
                ),
                border = BorderStroke(2.dp, Color(0xFFF07B42))
            ) {
                Text("Retour")
            }
        }
    }
}
