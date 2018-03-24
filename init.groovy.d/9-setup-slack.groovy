import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.impl.*
import hudson.util.Secret
import java.nio.file.Files
import jenkins.model.Jenkins
import net.sf.json.JSONObject
import org.jenkinsci.plugins.plaincredentials.impl.*

def slackToken = System.getenv('JENKINS_SLACK_TOKEN') ?: ''
def slackCompany = System.getenv('JENKINS_SLACK_COMPANY') ?: ''

if(slackToken != '' && slackCompany != '') {
  // parameters
  def slackCredentialParameters = [
    description: 'Slack token',
    id: 'slack-token',
    secret: slackToken
  ]

  def slackParameters = [
    slackBaseUrl: "https://${slackCompany}.slack.com/services/hooks/jenkins-ci/",
    slackBotUser: 'true',
    slackBuildServerUrl: System.getenv('JENKINS_URL'),
    slackRoom: '#jenkins',
    slackSendAs: 'Jenkins',
    slackTeamDomain: slackCompany,
    slackToken: '',
    slackTokenCredentialId:   'slack-token'
  ]

  // get Jenkins instance
  Jenkins jenkins = Jenkins.getInstance()

  // get credentials domain
  def domain = Domain.global()

  // get credentials store
  def store = jenkins.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

  // get Slack plugin
  def slack = jenkins.getExtensionList(jenkins.plugins.slack.SlackNotifier.DescriptorImpl.class)[0]

  // define secret
  def secretText = new StringCredentialsImpl(
    CredentialsScope.GLOBAL,
    slackCredentialParameters.id,
    slackCredentialParameters.description,
    Secret.fromString(slackCredentialParameters.secret)
  )

  // define form and request
  JSONObject formData = ['slack': ['tokenCredentialId': 'slack-token']] as JSONObject
  def request = [getParameter: { name -> slackParameters[name] }] as org.kohsuke.stapler.StaplerRequest

  // add credential to Jenkins credentials store
  store.addCredentials(domain, secretText)

  // add Slack configuration to Jenkins
  slack.configure(request, formData)

  // save to disk
  slack.save()
  jenkins.save()
}
