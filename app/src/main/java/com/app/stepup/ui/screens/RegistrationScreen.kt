package com.app.stepup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.stepup.R
import com.app.stepup.model.data.Gender
import com.app.stepup.model.data.User
import com.app.stepup.navigation.ScreenRoutes
import com.app.stepup.ui.components.Background
import com.app.stepup.ui.components.DefaultButton
import com.app.stepup.ui.components.DropDownMenu
import com.app.stepup.ui.components.PairTitle
import com.app.stepup.ui.components.showToast
import com.app.stepup.ui.theme.TransparentWhite
import com.app.stepup.view_models.RegistrationViewModel

@Composable
fun RegistrationScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    var userName by rememberSaveable { mutableStateOf("") }
    var userAge by rememberSaveable { mutableIntStateOf(0) }
    var userGender by rememberSaveable { mutableStateOf(Gender.OTHER.name) }
    var userHeight by rememberSaveable { mutableStateOf("") }
    var userWeight by rememberSaveable { mutableStateOf("") }
    var stepGoal by rememberSaveable { mutableIntStateOf(0) }

    val agesList = (1..100).toList()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Background()
        Column(
            Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .background(TransparentWhite, RoundedCornerShape(8.dp)),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            TextField(
                value = userName,
                onValueChange = { userName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 18.sp),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.enter_name),
                        color = Color.White
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            Column {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    PairTitle(firstTitle = R.string.gender, secondTitle = R.string.age)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DropDownMenu(items = Gender.entries) {
                        userGender = it.name
                    }
                    DropDownMenu(items = agesList) {
                        userAge = it
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    PairTitle(firstTitle = R.string.weight, secondTitle = R.string.height)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextField(
                        value = userWeight,
                        onValueChange = { userWeight = it },
                        modifier = Modifier
                            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                            .padding(horizontal = 6.dp)
                            .width(100.dp),
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            unfocusedTextColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.mask_of_entered),
                                color = Color.White
                            )
                        }
                    )
                    TextField(
                        value = userHeight,
                        onValueChange = { userHeight = it },
                        modifier = Modifier
                            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                            .padding(horizontal = 6.dp)
                            .width(100.dp),
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            unfocusedTextColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.mask_of_entered),
                                color = Color.White
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            TextField(
                value = if (stepGoal == 0) "" else stepGoal.toString(),
                onValueChange = { stepGoal = if (it == "") 0 else it.toInt() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 18.sp),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.enter_step_goal),
                        color = Color.White
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            DefaultButton(R.string.registration) {
                when {
                    userName.isEmpty() -> showToast(context, R.string.pls_enter_name)
                    userAge == 0 -> showToast(context, R.string.pls_enter_age)
                    userWeight == "" -> showToast(context, R.string.pls_enter_weight)
                    userHeight == "" -> showToast(context, R.string.pls_enter_height)
                    stepGoal == 0 -> showToast(context, R.string.pls_enter_step_goal)
                    else -> {
                        viewModel.setUserData(
                            User(
                                userName,
                                userAge,
                                Gender.valueOf(userGender),
                                userHeight.toDouble(),
                                userWeight.toDouble()
                            ),
                            stepGoal
                        )
                        navController.navigate(ScreenRoutes.HomeScreen.route)
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
