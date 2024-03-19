package open_link

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual object Linker {
   actual fun openLink(url:String){
      val application = UIApplication.sharedApplication
      val nsURl = NSURL(string = url)
      if(!application.canOpenURL(nsURl)){
         println("Unable to open URL == $url")
         return
      }
      application.openURL(nsURl)
   }
}