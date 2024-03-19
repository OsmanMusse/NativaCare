package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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

class PrivacyPolicyScreen: Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxSize().verticalScrollWithScrollbar(rememberScrollState()).padding(top = 100.dp, start = 13.dp, end = 13.dp),
        ){
            Text(
                text = "Privacy policy",
                color = colorResource(MR.colors.h2_color),
                fontSize = 22.5.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 14.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text ="Welcome to our information policy notice, designed to clarify how we handle the information we collect from you or that you provide to us. This encompasses both personally identifiable information and non-identifiable data. In legal terms and within the scope of this notice, “process” refers to actions such as collecting, storing, transferring, or otherwise dealing with information.  We are committed to safeguarding your privacy and confidentiality. Recognising that all our website visitors have the right to know that their personal data will be used only for intended purposes and will not inadvertently be accessible to third parties, we take this responsibility seriously.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("We assure you that we will uphold the confidentiality of all information you provide to us and hope for your reciprocal understanding. Our policy aligns with UAE law, including compliance with the UAE Data protection Regulation. To fulfil our legal obligation, we inform you about your rights and our responsibilities regarding the processing and control of your personal data. For further details, we invite you to review the information available at knownyourprivacyrights.com with the exception outlines below, we do not share, sell, or disclose any information collected through our website to third parties.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("When you register on our website, make a purchase, or agree to our terms and conditions, a contractual relationship is established between you and us. To fulfil our obligations under this contract, we are required to process the information you provide, some of which may be personal.  We use this information:",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            val item1 =
                "To verify your identity for security reasons"
            val item2 =
                "To Sell products to you."
            val item3 = "Provide services to you."
            val item4 = "Offer suggestion and advice on products, services, and how to optimise your experience on our website."

            val list = arrayOf(item1, item2, item3,item4)

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

            Text("Information processed with your consent: In situations where there is no contractual relationship, such as when you browse our website or request more information about our business, products, and services, your consent allows us to process personal information. We aim to obtain explicit consent, and you may withdraw it at any time. If you withdraw consent, you may be unable to use our website or services further.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Information processed for legitimate interests: We may process information when there is a legitimate interest, either to you or us, in doing so. This involves careful consideration of alternative means, potential harm, your expectations, and reasonableness. Examples include record-keeping, responding to unsolicited communication, protecting legal rights, managing risk, and marketing analysis",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Information processed due to legal obligation: Certain information processing is necessary to comply with statutory obligations. This may involve providing information to legal authorities upon their request or authorisation.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Specific uses of information provided: Information related to payment methods is securely handled by a third-party provider, when you contact our support team, we collect and use the data to respond effectively. Complaints are recorded and used to resolve issues, with minimal disclosure to other parties.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Use of information collected through automated system:",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("For the purpose of analytics, we automatically collect data when you use the website through Google Analytics. We do not use data to make automated decision or use profiling.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("The type of data we collected may include the number of users, new users, specific pages visited and length of page visit. The purpose of this is to monitor and evaluate usage of the website to measure its effectiveness and improve its functionality.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("This type of data is fully anonymised and does not constitute personal data.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("For more information on the use of Google Analytics and how it collects and processes data, please see page ‘How Google uses data’ at policies.google.com.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("We use cookies for tracking website usage, displaying messages, recording survey responses, and live chat conversations.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Personal identifiers from browsing activity: Information about your browsing activity is recorded for aggregate analysis of webpage popularity and performance.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Re-Marketing: We use third-party re-marketing services, placing cookies to display our ads on other websites if you have consented to cookies.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Disclosure and sharing of information: Data obtained indirectly from third parties is non-identifiable.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Data processing outside the UAE: Data may be processed outside the UAE with appropriate safeguards",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Access, removal, and verification of information: You can review, update, or request information by signing in or contacting us. Removal requests may limit our service, and identity verification is required.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Others matters: We do not sell to or market to children. Users under 18 need parental consent. encryption ensures secure data transfer between us.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("How to complain: You can contact us with privacy concerns at info@nativacare.com , and disputes can be resolved through mediation or arbitration.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Retention period for personal data: We retain personal information for service provision, legal compliance, and support of claims or defences.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Compliance with the law: Our privacy policy is designed to comply with the laws of all jurisdictions where we operate.",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Review of this privacy: This Privacy Policy may change when necessary to reflect customer feedback, changes in our services, or legal changes, and the terms applicable are those posted on the day you use our website. We reserve the right to add, change, suspend or discontinue the website, or any aspect without notice or liability. Feel free to contact us if you have questions about our Privacy Policy at info@nativacare.com",
                color = colorResource(MR.colors.p_color),
                fontSize = 15.sp,
            )
        }
    }
}