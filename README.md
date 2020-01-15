# FreeMarker Mojo
This is a simple Maven Plugin (Mojo) that wraps and calls the Apache FreeMarker engine.

## Usage
```xml
<plugin>
    <groupId>com.github.brendonstephens</groupId>
    <artifactId>freemarker-mojo</artifactId>
    <version>0.0.1</version>
    <configuration>
        <srcDir>${project.build.directory}</srcDir>
    </configuration>
    <executions>
        <execution>
            <id>test-freemarker-template</id>
            <phase>package</phase>
            <goals>
                <goal>apply-freemarker-template</goal>
            </goals>
            <configuration>
                <template>templates/example.ftl</template>
                <outputFile>${project.build.directory}/output.json</outputFile>
                <dataModels>
                    <config>templates/config.json</config>
                </dataModels>
            </configuration>
        </execution>
    </executions>
</plugin>

```
