def url = System.getenv('JENKINS_URL') ?: ''

if(url != '') {
  location = jenkins.model.JenkinsLocationConfiguration.get()
  location.setUrl()
  println(location.getUrl())
}
