import org.csanchez.jenkins.plugins.kubernetes.*
import jenkins.model.*

def k8sEnabled = System.getenv('JENKINS_KUBERNETES_ENABLED') ?: ''

if(k8sEnabled == 'true') {
  def j = Jenkins.getInstance()

  def k = new KubernetesCloud(
    'kubernetes', // name
    null, // pod templates
    '', // server udl
    'default', // namespace
    'http://jenkins.default.svc.cluster.local', // jenkins url
    '10', // container cap str
    0, // connect timeout
    0, // read timeout
    5 // retention timeout
  )

  k.setSkipTlsVerify(true)

  j.clouds.replace(k)
  j.save()
}
