# shade-app-maven-plugin

Maven plugin that configures the maven shade plugin to create a fat jar with main class attribute.

# Usage

Add to pom.xml

```xml
<properties>
  <mainClass>pkg.Main</mainClass>
</properties>

<build>
  <extensions>
    <extension>
      <groupId>so.born</groupId>
      <artifactId>shade-app-maven-plugin</artifactId>
      <version>1.1</version>
    </extension>
  </extensions>
</build>

<pluginRepositories>
  <pluginRepository>
    <snapshots>
      <enabled>false</enabled>
    </snapshots>
    <id>bintray-jwiklund-so.born</id>
    <name>bintray-plugins</name>
    <url>http://dl.bintray.com/jwiklund/so.born</url>
  </pluginRepository>
</pluginRepositories>
```
