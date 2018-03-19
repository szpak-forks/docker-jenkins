import jenkins.model.*
import hudson.security.*
import hudson.util.Secret
import org.jenkinsci.plugins.saml.*

def idpMetadataUrl = System.getenv('JENKINS_SAML_IDP_METADATA_URL') ?: ''

if(idpMetadataUrl != '') {
  println('Setting up SAML with IdP metadata from ${idpMetadataUrl}...')

  def instance = Jenkins.getInstance()

  def samlRealm = new SamlSecurityRealm(
    new IdpMetadataConfiguration(idpMetadataUrl, 60),
    "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name",
    "http://schemas.xmlsoap.org/claims/Group",
    86400,
    "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name",
    "",
    "",
  null,
    new SamlEncryptionData('/var/jenkins_home/saml.jks', Secret.fromString('password'), Secret.fromString('password'), ''),
    "none",
    "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect"
    )

  instance.setSecurityRealm(samlRealm)
  instance.save()

  def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
  strategy.setAllowAnonymousRead(false)
  instance.setAuthorizationStrategy(strategy)
  instance.save()

  println("SAML setup completed")
}
