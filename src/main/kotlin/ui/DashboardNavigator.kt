package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

class DashboardNavigator {

    @Composable
    fun getComponent(
        listItems: List<Pair<ImageVector, String>>,
        onItemClick: (String) -> Unit
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(color = Color.White)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            listItems.forEach { (icon, title) ->
                Row(
                    modifier = Modifier
                        .height(48.dp)
                        .width(48.dp)
                        .clickable { onItemClick(title) }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onItemClick(title) }
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .width(16.dp)
                    )
                }
            }
        }
    }

}