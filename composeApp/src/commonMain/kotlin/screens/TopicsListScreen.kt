package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import data.local.MainData
import data.local.PreferencesKeys.IMAGE_PATH
import dev.icerock.moko.resources.compose.colorResource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.example.nativacare.MR
import verticalScrollWithScrollbar


sealed class Component

data class Title(val text: String) : Component()

data class SubTitle(val text: String): Component()

data class Image(val link: String, val caption: String?) : Component()

data class Paragraph(val text: String,val markups: List<Markup> = emptyList()) : Component()

data class BulletList(val text: String): Component()

data class SubSection(val title:String, val imageURL: String, val content:String): Component()

data class RelatedLink(val text:String, val link: String, val isElementFirst: Boolean): Component()



data class Markup(
    val type: MarkupType,
    val start: Int,
    val end: Int,
    val color : Color
)

enum class MarkupType {
    Italic,
    Bold,
}


data class Article(
    val url: String? = null,
    val components: List<Component>
)


data class TopicsListScreen(
    val isRootScreen: Boolean? = false,
    val mainData: MainData,
    val navTitle: String? = null
) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val sharedViewModel = navigator.rememberNavigatorScreenModel { SharedViewModel() }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScrollWithScrollbar(rememberScrollState()).padding(top = 100.dp)
            ) {

                Column(
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {

                    Text(
                        text = if (isRootScreen == false) mainData.topics?.get(0)!!.title else sharedViewModel.navigationStack.lastItemOrNull!!,
                        color = colorResource(MR.colors.h2_color),
                        fontSize = 22.5.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(end = 14.dp)
                    )


                    if (isRootScreen == false) {
                        Spacer(modifier = Modifier.height(25.dp))
                        val topic = mainData.topics?.get(0)
                        KamelImage(
                            resource = asyncPainterResource(data = "${IMAGE_PATH}/fit-in/200x400/${topic?.imageURL}"),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = null,
                            modifier = Modifier.height(250.dp).background(Color.Red)
                        )


                    }

                    Spacer(modifier = Modifier.height(if (isRootScreen!!) 20.dp else 17.dp))

                    LazyColumn(modifier = Modifier.heightIn(max = 2000.dp)) {
                        val evaluatedList =
                            if (mainData.topics.size == 1) mainData.topics[0]?.subTopics ?: mainData.topics else mainData.topics
                        items(items = evaluatedList) { topic ->

                            Divider(
                                modifier = Modifier.fillMaxWidth(),
                                thickness = 1.4.dp,
                                color = colorResource(MR.colors.primaryColor)
                            )
                            Row(
                                modifier = Modifier.height(130.dp)
                                    .clickable {
                                        sharedViewModel.shouldShowBackBtn = true
                                        if (topic.subTopics != null) {
                                            // Continue to sub screen
                                            val newData = MainData(listOf(topic))
                                            sharedViewModel.navigationStack.push(sharedViewModel.navigationStack.lastItemOrNull!!)
                                            navigator.push(TopicsListScreen(false, newData,topic.title))

                                        } else {
                                            sharedViewModel.navTitle = navTitle ?: sharedViewModel.navigationStack.lastItemOrNull!!
                                            sharedViewModel.navigationStack.push(sharedViewModel.navTitle)
                                            navigator.push(ParserScreen(topic))
                                        }
                                    }
                                    .padding(10.dp),
                            ) {
                                Text(
                                    text = "${topic.title}",
                                    modifier = Modifier.weight(1f)
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                    KamelImage(
                                        resource = asyncPainterResource(data = "${IMAGE_PATH}/fit-in/200x400/${topic.imageURL}",key = topic.imageURL),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize().background(Color.White)
                                            .weight(1f)
                                    )

                            }
                        }
                    }

                }

            }
        }

}