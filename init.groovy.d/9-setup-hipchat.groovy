import jenkins.model.*;
import java.lang.reflect.Field;

def hipchatServer = System.getenv('HIPCHAT_SERVER') ?: ''
def hipchatToken = System.getenv('HIPCHAT_TOKEN') ?: ''
def hipchatSendAs = System.getenv('HIPCHAT_SEND_AS') ?: 'Jenkins'

if ( Jenkins.instance.pluginManager.activePlugins.find { it.shortName == "hipchat" } != null && hipchatToken != '' && hipchatServer != '') {
  println "--> setting hipchat plugin"

  def descriptor = Jenkins.instance.getDescriptorByType(jenkins.plugins.hipchat.HipChatNotifier.DescriptorImpl.class)

  // no setters :-(
  // Groovy can disregard object's pivacy anyway to directly access private
  // fields, but we use a different technique 'reflection' this time
  Field[] fld = descriptor.class.getDeclaredFields();
  for(Field f:fld){
    f.setAccessible(true);
    switch (f.getName()) {
      case "server":
        f.set(descriptor, hipchatServer)
        break

      case "token":
        f.set(descriptor, hipchatToken)
        break

      case "buildServerUrl":
        f.set(descriptor, "/")
        break

      case "sendAs":
        f.set(descriptor, hipchatSendAs)
        break
    }
  }
}
