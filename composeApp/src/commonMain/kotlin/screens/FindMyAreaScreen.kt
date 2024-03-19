package screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import dialogs.AdaptiveAlertDialog
import org.example.nativacare.MR


class FindMyAreaScreen: Screen {

    @OptIn(ExperimentalMaterialApi::class, ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var text by remember { mutableStateOf("") }
        val sharedViewModel = navigator.rememberNavigatorScreenModel { SharedViewModel() }
        var shouldShowAlertDialog = remember { mutableStateOf(false) }
        var shouldShowPostcodeAlertDialog = remember { mutableStateOf(false) }

        if(shouldShowAlertDialog.value){
            AdaptiveAlertDialog(
                title = "Continue with no hospital area selected",
                text = "You can explore the different areas at any time from the home screen. Continue without area selection?",
                confirmText = "Yes",
                dismissText = "No",
                onConfirm = {
                    shouldShowAlertDialog.value = false
                    sharedViewModel.shouldShowBackBtn = true
                    sharedViewModel.hospitalName = null
                    navigator.popUntil { navigator.lastItem is HomeScreen }
                },
                onDismiss = {
                    shouldShowAlertDialog.value = false
                },
                properties = DialogProperties()
            )
        }
        if(shouldShowPostcodeAlertDialog.value){
            AdaptiveAlertDialog(
                title = "Area not found",
                text = "Please ensure you enter a full, valid city or place",
                confirmText = "OK",
                dismissText = "",
                onConfirm = {
                    shouldShowPostcodeAlertDialog.value = false
                },
                onDismiss = {
                    shouldShowPostcodeAlertDialog.value = false
                },
                properties = DialogProperties()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, start = 17.dp, end = 17.dp)
        ) {
            Text(
                text = "Enter Your Area",
                color = colorResource(MR.colors.h2_color),
                fontSize = 22.5.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 15.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ){
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(54.dp)
                            .offset(y = -1.dp)
                            .background(colorResource(MR.colors.btn_green_color),
                                shape = RoundedCornerShape(0.dp, 6.dp, 6.dp, 0.dp))
                            .align(Alignment.CenterEnd)
                            .clickable {
                                  if(text.isEmpty()){
                                      shouldShowPostcodeAlertDialog.value = true
                                  } else {
                                      navigator.push(AreaListScreen(text))
                                  }

                            },
                        ){
                        Icon(
                            painter = painterResource(MR.images.search_icon_ios),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp).align(Alignment.Center)
                        )
                    }

                    val borderColor = colorResource(MR.colors.p_color)
                    Canvas( Modifier.fillMaxWidth().height(53.dp).zIndex(2f)){
                        val canvasWidth = size.width
                        val canvasHeight = size.height

                        drawLine( //top line
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = canvasWidth-100.dp.toPx(), y = 0f),
                            strokeWidth = 4f,
                            color = borderColor
                        )
                        drawLine( //bottom line
                            start = Offset(x = 0f, y = canvasHeight),
                            end = Offset(x = canvasWidth-100.dp.toPx(), y = canvasHeight),
                            strokeWidth = 4f,
                            color = borderColor
                        )
                        drawLine( //left line
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = canvasHeight),
                            strokeWidth = 4f,
                            color = borderColor
                        )

                        drawLine( //right line
                            start = Offset(x = canvasWidth, y = 0f),
                            end = Offset(x = canvasWidth, y = canvasHeight),
                            strokeWidth = 4f,
                            color = Color.Transparent
                        )
                    }
                    BasicTextField(
                        value = text,
                        modifier = Modifier.zIndex(1f).align(Alignment.CenterStart),
                        onValueChange = { text = it },
                        decorationBox = { innerTextField ->
                            TextFieldDefaults.TextFieldDecorationBox(
                                value = text,
                                enabled = true,
                                innerTextField = innerTextField,
                                interactionSource = MutableInteractionSource(),
                                singleLine = true,
                                visualTransformation = VisualTransformation.None,
                                placeholder = {
                                    Text(
                                        text = "Enter your area",
                                        fontSize = 16.sp
                                    )
                                }
                            )
                        },
                    )

                }
            }

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "Find if your hospital unit is included",
                color = colorResource(MR.colors.h2_color),
                fontSize = 17.5.sp,
                fontWeight = FontWeight.Medium,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "This app is growing to include more hopsitals. If your area is already included and you select it, the app will give you information and contacts for your local area.",
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = colorResource(MR.colors.p_color)
            )

            Spacer(modifier = Modifier.height(23.dp))


            Button(
                modifier = Modifier.fillMaxWidth().height(47.dp),
                onClick = {
                   shouldShowAlertDialog.value = true
                },
                contentPadding = PaddingValues(horizontal = 23.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(MR.colors.primaryColor))
            ) {
                Text(
                    text = "Continue with no hospital unit",
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start
                )

                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(MR.images.arrow_right),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = "App privacy and usage",
                color = colorResource(MR.colors.h2_color),
                fontSize = 17.5.sp,
                fontWeight = FontWeight.Medium,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "By proceeding, you agree to our privacy policy and terms of use:",
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = colorResource(MR.colors.p_color)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                modifier = Modifier.fillMaxWidth().height(47.dp),
                onClick = {
                    sharedViewModel.navigationStack.push("Privacy policy")
                    sharedViewModel.shouldShowBackBtn = true
                    navigator.push(PrivacyPolicyScreen())
                },
                contentPadding = PaddingValues(horizontal = 23.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(MR.colors.primaryColor))
            ) {
                Text(
                    text = "Privacy policy",
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    textAlign = TextAlign.Start
                )

                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(MR.images.arrow_right),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth().height(47.dp),
                onClick = {
                    sharedViewModel.navigationStack.push("Terms of use")
                    sharedViewModel.shouldShowBackBtn = true
                    navigator.push(TermsOfUseScreen())
                },
                contentPadding = PaddingValues(horizontal = 23.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(MR.colors.primaryColor))
            ) {
                Text(
                    text = "Terms of use",
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    textAlign = TextAlign.Start
                )

                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(MR.images.arrow_right),
                    contentDescription = null,
                    tint = Color.White
                )
            }


        }
    }
}