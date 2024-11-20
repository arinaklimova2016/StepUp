package com.app.stepup.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.stepup.R

@Composable
fun <T> DropDownMenu(items: List<T>, initialSelectedIndex: Int = 0, onClick: (item: T) -> Unit) {
    var isDropDownGenderExpanded by rememberSaveable { mutableStateOf(false) }
    var itemPosition by rememberSaveable { mutableIntStateOf(initialSelectedIndex) }
    LaunchedEffect(initialSelectedIndex) {
        itemPosition = initialSelectedIndex
    }
    Box(
        modifier = Modifier
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .padding(6.dp)
            .width(100.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    isDropDownGenderExpanded = true
                }
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = items[itemPosition].toString(),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Image(
                painter = painterResource(id = R.drawable.ic_drop_down),
                contentDescription = R.drawable.ic_drop_down.toString()
            )
        }
        DropdownMenu(
            modifier = Modifier.heightIn(max = LocalConfiguration.current.screenHeightDp.dp / 2),
            expanded = isDropDownGenderExpanded,
            onDismissRequest = {
                isDropDownGenderExpanded = false
            }) {
            items.forEachIndexed { index, value ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = value.toString(),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                    },
                    onClick = {
                        isDropDownGenderExpanded = false
                        itemPosition = index
                        onClick(value)
                    }
                )
            }
        }
    }
}