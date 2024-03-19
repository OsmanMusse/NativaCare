package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.resources.compose.colorResource
import org.example.nativacare.MR
import verticalScrollWithScrollbar

class TermsOfUseScreen: Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScrollWithScrollbar(rememberScrollState())
                .padding(top = 100.dp, start = 13.dp, end = 13.dp)
        ){
            Text(
                text = "Terms of use",
                color = colorResource(MR.colors.h2_color),
                fontSize = 22.5.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 14.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Copyright: The information provided on this website is the copyright of The Nativacare powered by Digital Gravity and may not be used in whole or part without prior written permission. The moral rights of the authors are reserved",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "Website Content:  The contents of Nativacare are reviewed bi-annually to ensure that information given remains accurate, up to date and relevant for the users. While the Nativacare makes every effort to maintain accurate and up-to-date information on this website, we cannot be held responsible for any reliance placed upon the information contained within it.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "Modification of the service: We reserve the right to modify or withdraw any service without notice to meet the highest possible standards. Your use of this website signifies your agreement to the terms of use.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Modification of the service: We reserve the right to modify or withdraw any service without notice to meet the highest possible standards. Your use of this website signifies your agreement to the terms of use.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

//            Booking Terms and conditions:  Our sessions and workshop, taught by qualified midwives, provide educational advice on pregnancy, childbirth, and baby care. We do not provide medical advice, diagnostic or treatment. Where you have any concern as regards the safety, personal health-related queries, and wellbeing of yourself or your baby, you must seek medical advice, from your Obstetrician, other healthcare professional or nearest hospital.


            Text(
                text = "Booking Terms and conditions:  Our sessions and workshop, taught by qualified midwives, provide educational advice on pregnancy, childbirth, and baby care. We do not provide medical advice, diagnostic or treatment. Where you have any concern as regards the safety, personal health-related queries, and wellbeing of yourself or your baby, you must seek medical advice, from your Obstetrician, other healthcare professional or nearest hospital",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "Accuracy of the information: NativaCare workshop and sessions intended to inform you about a range of topics related to childbirth and motherhood journey. Care has been taken to include information that is line with the guidance, advice and/or quality standards that are approved by a range or organisation such as the RCOG (Royal College of Obstetricians and Gynaecologist), RCM ( Royal College of Midwives), UNICEF (United Nations International Children’s Emergency Fund) and NICE ( National Institute for Health and Care Excellence).",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "Liability: Attendees are advised to take care of their personal property. Nativacare does not accept responsibility for any loss, damage, or injury arising from class attendance.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "Course materials are for general information purposes and not individual medical advice. The Nativacare reserves the right to decline any application to attend its classes.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "Course materials are for general information purposes and not individual medical advice. The Nativacare reserves the right to decline any application to attend its classes.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Payment: Upon receipt of payment, a confirmation email will be sent.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "Cancellation Policy:  Cancellation information must be communicated via email or phone. Refunds are as follows:",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            val item1 =
                "48 hours or more before the course: full refund"
            val item2 =
                "Exceptional circumstances: Full refund."

            val list = arrayOf(item1, item2)


            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                list.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.Top)
                                .offset(y = 7.dp)
                                .padding(start = 16.dp, end = 8.dp)
                                .size(8.dp)
                                .background(
                                    colorResource(MR.colors.primaryColor),
                                    shape = CircleShape
                                ),
                        )

                        Spacer(modifier = Modifier.width(18.dp))

                        Text(
                            text = "$it",
                            color = colorResource(MR.colors.p_color),
                            fontSize = 15.sp,
                            modifier = Modifier.align(Alignment.Top),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "Rescheduling: In the event of rescheduling, participant will be notified in advance subject to availability. No refund will be offered in these circumstances.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )


            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "Workshops cancelation: Under exceptional circumstances, if The Parents Class cancels, alternatives will be offered. If declined, a full refund will be offered.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "Contact Information: for more information please do contact us at info@nativacare.com",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )


        }
    }

}