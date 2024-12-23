package com.example.pieskoapka

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DogListApp(navController: NavController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val dogs = remember { mutableStateListOf<Dog>() }
    var errorMessage by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf<List<Dog>>(emptyList()) }

    if (dogs.isEmpty()) {
        repeat(1) { dogs.add(Dog("Donald", "Beagle")) }
        repeat(1) { dogs.add(Dog("Azor", "Husky")) }
        repeat(14) { dogs.add(Dog("Pan Punpernikiel", "Jack Russel")) }
    }

    val totalDogs = dogs.size
    val favoriteDogs = dogs.count { it.isFavorite }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.navigate(Routes.ScreenB) }) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black
                )
            }
            Text(
                text = "Doggos",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            IconButton(onClick = { navController.navigate("Profile") }) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.Black
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Poszukaj lub dodaj pieska 🐕") },
                modifier = Modifier
                    .weight(1f)
                    .background(if (errorMessage.isNotEmpty()) Color.Red.copy(alpha = 0.1f) else Color.Transparent)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                searchResult = dogs.filter { it.name.contains(searchQuery.text.trim(), ignoreCase = true) }
            }) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Search,
                    contentDescription = "Search Dog"
                )
            }
            IconButton(
                onClick = {
                    if (dogs.any { it.name.equals(searchQuery.text.trim(), ignoreCase = true) }) {
                        errorMessage = "Piesek jest już na liście"
                    } else {
                        dogs.add(Dog(searchQuery.text.trim(),"Unknown Breed"))
                        searchQuery = TextFieldValue("")
                        errorMessage = ""
                    }
                }) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Add,
                    contentDescription = "Add Dog",
                    tint = Color.Black
                )
            }
        }
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Text(
            text = "🐕: $totalDogs    💜: $favoriteDogs",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(searchResult.ifEmpty { dogs }) { dog ->
                DogListItem(dog, onFavoriteClick = {
                    dog.isFavorite = !dog.isFavorite
                    if (dog.isFavorite) {
                        dogs.remove(dog)
                        dogs.add(0, dog)
                    }
                }, onDeleteClick = {
                    dogs.remove(dog)
                }, onDogClick = {
                    navController.navigate(Routes.ScreenB+"/${dog.name}/${dog.breed}")
                })
            }
        }
    }
}

@Composable
fun DogListItem(dog: Dog, onFavoriteClick: () -> Unit, onDeleteClick: () -> Unit, onDogClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFF5F5F5))
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onDogClick) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFB39DDB)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🐕", fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(dog.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(dog.breed, fontSize = 12.sp, color = Color.Gray)
            }
        }
        Row {
            IconButton(onClick = onFavoriteClick) {
                Text(
                    text = if (dog.isFavorite) "💜" else "🤍",
                    fontSize = 20.sp
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    tint = Color.Red,
                    imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun PreviewDogListApp() {
//    DogListApp(navController: NavController)
//}