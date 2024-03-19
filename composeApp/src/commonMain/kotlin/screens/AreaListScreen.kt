package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import data.local.PreferencesKeys
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import dialogs.AdaptiveAlertDialog
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.example.nativacare.MR


data class LocationData(
    val title: String? = null,
    val image: String? = null
){

    fun formattedURL(): String {
       return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=${image}&key=AIzaSyCpoDhnofVkF8NMqaII0MB-_Wu8no5Qllw"
   }
}
data class AreaListState(
    val list: List<LocationData>? = null,
    val hospitalName: String? = null
){}
class AreaListScreenViewModel: ViewModel() {

    private val _state = MutableStateFlow(AreaListState())
    val state = _state.asStateFlow()

    private val httpClient: HttpClient = HttpClient(){
        install(ContentNegotiation) {json()}
    }

    fun selectHospital(name:String){
        _state.value = _state.value.copy(hospitalName = name)
    }
     suspend fun getHospitals(city:String){
        val response = httpClient.get("https://maps.googleapis.com/maps/api/place/textsearch/json"){
            url {
                encodedParameters.append("query","maternity hospitals%20in%40$city")
                encodedParameters.append("key","AIzaSyCpoDhnofVkF8NMqaII0MB-_Wu8no5Qllw")
            }
        }

        val data = parseJsonData(response)
        _state.value = _state.value.copy(list = data)
    }

    private suspend fun parseJsonData(response: HttpResponse): ArrayList<LocationData> {
        val stringBody = response.body<String>()
        val json: Map<String, JsonElement> = Json.parseToJsonElement(stringBody).jsonObject
        val resultKey = json["results"] as JsonArray
        val list = arrayListOf(LocationData())
        for(item in resultKey){
            var locationData = LocationData()
            val hospitalName = (item as JsonObject)["name"]?.jsonPrimitive?.content.toString()
            locationData = locationData.copy(title = hospitalName)
            println("Hospital NAME == ${hospitalName}")
            val photos = item["photos"] as? JsonArray
            if (photos != null) {
                for(photo in photos){
                    val photoURL =  (photo as JsonObject)["photo_reference"]?.jsonPrimitive?.content.toString()
                    println("PHOTO URL == ${photoURL}")
                    locationData = locationData.copy(image = photoURL)
                    list.add(locationData)
                }
            }else {
                list.remove(locationData)
            }
        }
        if(list.size > 1) list.removeAt(0)
        return list
    }
}
data class AreaListScreen(val searchQuery: String) : Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val showAlertDialog = remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow
        val sharedViewModel = navigator.rememberNavigatorScreenModel { SharedViewModel() }
        val viewModel = getViewModel("Area_List_Screen_ViewModel", viewModelFactory { AreaListScreenViewModel() })

        LaunchedEffect(Unit){
            scope.launch {
                viewModel.getHospitals(searchQuery)
            }
        }

        val state by viewModel.state.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
        ) {

            if(showAlertDialog.value){
                AdaptiveAlertDialog(
                    title = "Select your hospital",
                    text = "You can always change this selection if you book with a different hospital",
                    confirmText = "Ok",
                    dismissText = "Cancel",
                    onConfirm = {
                        settings.putString(PreferencesKeys.HOSPITAL,state.hospitalName ?: "")
                        sharedViewModel.hospitalName = state.hospitalName
                        sharedViewModel.shouldShowBackBtn = false
                        showAlertDialog.value = false
                        navigator.popUntil { navigator.lastItem is HomeScreen }
                    },
                    onDismiss = {
                        showAlertDialog.value = false
                    },
                    properties = DialogProperties()
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min).background(colorResource(MR.colors.gray_bg)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 13.dp, bottom = 13.dp, start = 25.dp)
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(MR.images.list_view_icon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(30.dp))

                    Text(
                        text = "LIST VIEW",
                        color = Color.White,
                    )
                }

                Divider(
                    modifier = Modifier
                     .padding(vertical = 8.dp)
                     .fillMaxHeight()
                     .offset(x = -3.dp)
                     .width(1.25.dp),
                    color = Color.White
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 13.dp, bottom = 13.dp, start = 22.dp)
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(MR.images.map_icon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )

                    Spacer(modifier = Modifier.width(30.dp))

                    Text(
                        text = "MAP VIEW",
                        color = Color.White,
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.heightIn(2000.dp)
            ) {
                items(state.list ?: emptyList() ){
                    Row(modifier = Modifier.background(Color.White).height(110.dp).clickable {
                        viewModel.selectHospital(it.title!!)
                        showAlertDialog.value = true
                    }) {
                        Column(modifier = Modifier.fillMaxHeight().weight(0.80f)){
                            KamelImage(
                                resource = asyncPainterResource(it.formattedURL()),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                onLoading = { println("IMAGE LOADING.........") },
                                onFailure = { println("IMAGE FAILURE.........") }
                            )
                        }
                        Column(modifier = Modifier.fillMaxHeight().weight(1.20f)){

                            Box(modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 13.dp, top = 10.dp, bottom = 10.dp)) {
                                    Text(
                                        text = it.title!!,
                                        fontSize = 15.sp,
                                        modifier = Modifier.align(Alignment.TopStart)
                                    )

                                   Icon(
                                       painter = painterResource(MR.images.arrow_right),
                                       contentDescription = null,
                                       tint = colorResource(MR.colors.gray_bg),
                                       modifier = Modifier.size(25.dp).align(Alignment.CenterEnd)
                                   )

                                    Text(
                                        text ="2.1 miles (approx)",
                                        color = colorResource(MR.colors.p_color),
                                        fontSize = 13.sp,
                                        modifier = Modifier.align(Alignment.BottomStart)
                                    )

                            }
                        }
                    }
                     Divider(modifier = Modifier.fillMaxWidth(),color = colorResource(MR.colors.primaryColor))
                }
            }
        }

    }
}