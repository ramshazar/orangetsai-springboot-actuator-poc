node {
  checkout scm
  sh "mvn clean verify -DskipTests -Powasp-check"
}
