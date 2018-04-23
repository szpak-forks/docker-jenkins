import jenkins.model.*

def executorsNum = System.getenv('JENKINS_EXECUTORS') ?: "5"

Jenkins.instance.setNumExecutors(executorsNum.toInteger())
