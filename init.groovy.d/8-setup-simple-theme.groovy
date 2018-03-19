import jenkins.*

def themeColor = System.getenv('JENKINS_THEME_COLOR') ?: 'grey'

for (pd in PageDecorator.all()) {
  if (pd instanceof org.codefirst.SimpleThemeDecorator) {
    pd.cssUrl = "https://cdn.rawgit.com/afonsof/jenkins-material-theme/gh-pages/dist/material-${themeColor}.css"
  }
}
