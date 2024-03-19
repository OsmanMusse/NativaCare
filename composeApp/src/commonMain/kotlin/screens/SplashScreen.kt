package screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import data.local.MainData
import data.local.Topic
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.compose.painterResource
import domain.ArticleDataSource
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.nativacare.MR


data class SplashScreenViewState(
    val mainData: Map<String, MainData>? = null
){}
class SplashScreenViewModel(
    private val articleDataSource: ArticleDataSource
): ViewModel() {

    private val _state = MutableStateFlow(SplashScreenViewState())
    val state = _state.asStateFlow()

    private val httpClient: HttpClient = HttpClient() {
        install(ContentNegotiation) { json() }
    }
    suspend fun getAppData(){
        val data = httpClient
            .get("https://nativecarebucket.s3.eu-west-2.amazonaws.com/main.json").call.body<Map<String, MainData>>()
        _state.value = _state.value.copy(mainData = data)
        deleteAllArticles()
        insertArticles(data)
    }

    private suspend fun insertArticles(data: Map<String, MainData>) {
        data.values.forEach {
            it.topics.forEach { topic ->
                println("TITLE == ${topic.title} IMAGE URL = ${topic.imageURL} CONTENTS == ${topic.contents}")
                articleDataSource.insertArticles(
                    Topic(topic.title,topic.imageURL, contents = topic.contents)
                )
            }

        }
    }

     private suspend fun deleteAllArticles() {
         articleDataSource.deleteAllArticles()
     }
}

class SplashScreen: Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val sharedViewModel = navigator.rememberNavigatorScreenModel { SharedViewModel() }
        val viewModel = getViewModel(key = "SplashScreen_View_Model", viewModelFactory { SplashScreenViewModel(sharedViewModel.appModule!!.articleDataSource) })
        val state by viewModel.state.collectAsState()

        scope.launch { viewModel.getAppData() }

        Column(
            modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(MR.images.nativa_logo),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.width(300.dp).height(300.dp)
            )
        }

        if(state.mainData != null){
            scope.launch {
                sharedViewModel.mainData = state.mainData!!
                navigator.push(HomeScreen())
            }

        }
    }

}