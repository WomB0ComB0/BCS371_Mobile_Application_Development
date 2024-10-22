package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DrawerContent(navController: NavHostController, onCloseDrawer: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Menu",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Divider()
        DrawerItem("Pizza Party") {
            navController.navigate(BottomNavigationItems.PizzaScreen.route)
            onCloseDrawer()
        }
        DrawerItem("GPA Calculator") {
            navController.navigate(BottomNavigationItems.GpaAppScreen.route)
            onCloseDrawer()
        }
        DrawerItem("Screen 3") {
            navController.navigate(BottomNavigationItems.Screen3.route)
            onCloseDrawer()
        }
    }
}

@Composable
fun DrawerItem(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = text)
    }
}

