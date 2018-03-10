import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import hudson.plugins.sshslaves.*
import groovy.io.FileType

domain = Domain.global()
store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

def dir = new File("/credentials/git-keys")

dir.eachFileRecurse(FileType.FILES) { file ->
  if(file.name.endsWith(".pub"))
    return

  privateKey = new BasicSSHUserPrivateKey(
    CredentialsScope.GLOBAL,
    file.name,
    "git",
    new BasicSSHUserPrivateKey.FileOnMasterPrivateKeySource(file.path),
    "",
    ""
  )

  store.addCredentials(domain, privateKey)
}
