package screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.nodes.Element
import data.local.MainData
import data.local.Topic
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ParserViewModel: ViewModel() {

    private val _state = MutableStateFlow(ParserViewState())
    val state =  _state.asStateFlow()


     fun parserHTML(content:String){
        val html = content
        val doc: Document = Ksoup.parse(html = html)
        val body = doc.body().children()

        val list = arrayListOf<Component>()
         body.forEach {
             when(it.tagName()){
                  "h1" -> list.add(parseTitle(it))
                  "h2" -> list.add(parseSubTitle(it))
                  "p" -> list.add(parseParagraph(it))
                  "img" -> list.add(parseImage(it))
                  "li" -> list.add(parseList(it))
                  "a" -> list.add(parseRelatedLink(it))
                  "div" ->  list.add(parseSubSection(it.childElementsList()))
             }
        }

        _state.value = _state.value.copy(article = Article(components = list))

    }



    private fun parseSubSection(elements:List<Element>): SubSection{
        var topic = Topic("","")
        var contents = ""
        elements.forEach { element ->
            when(element.tagName()){
                "h5" -> topic = topic.copy(title = element.text())
                "img" -> {
                    topic = topic.copy(imageURL = element.attr("src"))
                }
                else -> contents += element
            }
        }

        println("ELEMENTS == ${topic.imageURL}")
        return SubSection(topic.title,topic.imageURL,contents)
    }
    private fun parseTitle(element: Element): Title{
        return Title(element.getElementsByClass("h1.entry-title")?.text() ?: "NOTHING")
    }

    private fun parseSubTitle(element: Element): SubTitle{
        return SubTitle(element.text())
    }

    private fun parseImage(element: Element): Image {
        println("IMAGE LINK == ${element.getElementsByClass("img-header")?.get(0)?.attr("src")}")
        return Image(link = element.getElementsByClass("img-header")?.get(0)?.attr("src") ?: "NO IMAGE", null)
    }

    private fun parseList(element: Element): BulletList {
        return BulletList(element.text())
    }

    private fun parseParagraph(element: Element): Paragraph {
        val list = arrayListOf<Markup>()
        element.forEach { element ->
            val paragraphStartPoint = element?.attr("data-start")
            val paragraphEndPoint = element?.attr("data-end")
           if(paragraphStartPoint?.isNotEmpty() == true){
               println("START POINT == $paragraphStartPoint END POINT == $paragraphEndPoint")
               list.add(Markup(type = MarkupType.Bold,start = paragraphStartPoint.toInt(), end = paragraphEndPoint!!.toInt(), color = Color.Red))
           }
        }

        return Paragraph(element.text(), listOf(Markup(MarkupType.Bold,start = 0, end = 2, color = Color.Red)))

    }

    private fun parseRelatedLink(element:Element): RelatedLink{
        val retrievedLink = element.attr("href")
        val islinkFirst = element.attr("id") == "true"
        return RelatedLink(text = element.text(), link = retrievedLink, isElementFirst = islinkFirst)
    }
}

data class ParserViewState(
    val article: Article? = null,
    val title: String = "",
    val mainData: MainData? = null
){

}
@Composable
fun getTextAndParagraphStyle(): TextStyle {
    val typography = MaterialTheme.typography
    return typography.bodyLarge.copy(lineHeight = 28.sp)
}

fun paragraphToAnnotatedString(
    paragraph: Paragraph,
    typography: Typography,
): AnnotatedString {
    val styles: List<AnnotatedString.Range<SpanStyle>> = paragraph.markups
        .map { it.toAnnotatedStringItem(typography) }
    return AnnotatedString(text = paragraph.text, spanStyles = styles)
}

fun Markup.toAnnotatedStringItem(
    typography: Typography,
): AnnotatedString.Range<SpanStyle> {
    return when (this.type) {
        MarkupType.Italic -> {
            AnnotatedString.Range(
                typography.bodyLarge.copy(fontStyle = FontStyle.Italic, color = Color.Blue).toSpanStyle(), start,
                end
            )
        }
        MarkupType.Bold -> {
            println("Actual range Start == $start End == $end")
            AnnotatedString.Range(
                typography.bodyLarge.copy(fontStyle = FontStyle.Normal, color = Color.Blue).toSpanStyle(), 30,
                40
            )
        }

    }

}

