package open_link

import android.content.Context
import android.content.Intent
import android.net.Uri

lateinit var activityContext: Context

actual object Linker {
   actual fun openLink(url:String){
       val intent = Intent(Intent.ACTION_VIEW)
       intent.data = Uri.parse(url)
       activityContext.startActivity(intent)
   }
}