package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import data.local.MainData
import data.local.PreferencesKeys
import data.local.Topic
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import domain.ArticleDataSource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.nativacare.MR


data class SearchScreenState(
    val articles: List<Topic>? = null
){}

class SearchScreenViewModel(
    private val query: String,
    private val articleDataSource: ArticleDataSource
): ViewModel() {


    init {
        viewModelScope.launch {
            searchArticle(query)
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("VIEWMODEL CLEARED 1 == ")
    }


    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()

    suspend fun searchArticle(query: String){
        articleDataSource.getSpecificArticles(query).collect { articleList ->
            delay(350)
            _state.value = _state.value.copy(articles = articleList)
        }
    }
}

data class SearchScreen(
    private val query: String,
    private val dataSource: ArticleDataSource
): Screen {

    class HomeScreenModel(
        private val query: String,
        private val articleDataSource: ArticleDataSource
    ): ScreenModel {

        init {
            screenModelScope.launch {
                searchArticle(query)
            }
        }

        override fun onDispose() {
            super.onDispose()
            println("VIEWMODEL CLEARED 2 == ")
        }


        private val _state = MutableStateFlow(SearchScreenState())
        val state = _state.asStateFlow()

        suspend fun searchArticle(query: String){
            articleDataSource.getSpecificArticles(query).collect { articleList ->
                delay(350)
                _state.value = _state.value.copy(articles = articleList)
            }
        }
    }
//
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val sharedViewModel = navigator.rememberNavigatorScreenModel { SharedViewModel() }
        val viewModel = rememberScreenModel { HomeScreenModel(query,dataSource) }
        val state by viewModel.state.collectAsState()

        println("STATE FLOW == ${state}")

        Column(modifier = Modifier.fillMaxWidth().padding(top = 60.dp)){
            if(state.articles == null){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    CircularProgressIndicator(modifier = Modifier, color = colorResource(MR.colors.pink_main_color))
                }
            }

            if (state.articles?.isEmpty() == false){
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "${state.articles?.size} search results",
                        color = colorResource(MR.colors.p_color)
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                LazyColumn(modifier = Modifier.heightIn(max = 2000.dp)) {
                    items(items = state.articles!!) { topic ->

                        if (state.articles!!.first() != topic){
                            Divider(
                                modifier = Modifier.fillMaxWidth(),
                                thickness = 1.4.dp,
                                color = colorResource(MR.colors.primaryColor)
                            )
                        }

                        Row(
                            modifier = Modifier.height(130.dp)
                                .clickable {
                                    sharedViewModel.navigationStack.push(query)
                                    sharedViewModel.shouldShowBackBtn = true
                                    println("Data == ${topic}")
                                    if(topic.contents == ""){
                                        navigator.push(TopicsListScreen(true, mainData = MainData(
                                            arrayListOf(topic)
                                        )))
                                    } else {
                                        navigator.push(ParserScreen(topic))
                                    }

                                }
                                .padding(10.dp),
                        ) {
                            androidx.compose.material.Text(
                                text = "${topic.title}",
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            KamelImage(
                                resource = asyncPainterResource(data = "${PreferencesKeys.IMAGE_PATH}/fit-in/200x400/${topic.imageURL}",key = topic.imageURL),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize().background(Color.White)
                                    .weight(1f)
                            )

                        }
                    }
                }
            }

             else if(state.articles?.isEmpty() == true) {
                Column(
                    modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp),
                ){
                    Text(
                        text = "Sorry, but we have no results for your search",
                        color = colorResource(MR.colors.h2_color),
                        fontSize = 22.5.sp,
                        fontWeight = FontWeight.Medium,
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Please try again using a different search term, or revisit the home page to navigate your way to specific topics.",
                        color = colorResource(MR.colors.p_dark_color),
                        fontSize = 14.85.sp,
                    )

                    Spacer(modifier = Modifier.height(35.dp))

                    Box(modifier = Modifier.fillMaxWidth()){
                        Icon(
                            painter = painterResource(MR.images.searching_magnifying_glass),
                            contentDescription = null,
                            tint = colorResource(MR.colors.pink_main_color),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                }
            }
        }

    }

}