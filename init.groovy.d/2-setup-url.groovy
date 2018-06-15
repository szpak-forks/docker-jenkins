def url = System.getenv('JENKINS_URL') ?: ''

if(url != '') {
  location = jenkins.model.JenkinsLocationConfiguration.get()
  location.setUrl(url)
  location.save()
}
