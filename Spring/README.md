# Spring Class

- STS 4 버전부터는 스프링 레거시 프로젝트 생성이 안 되고 스프링 부트만 생성 가능하기 때문에, 스프링 레거시 프로젝트를 생성하기 위해서는 STS 3 버전을 사용해야 합니다.
- 이클립스 마켓플레이스에서 STS3 플러그인 지원이 안 되는 버전이라면 STS를 별도로 설치해야 합니다. 
  Spring Tools 3 (Standalone Edition) 3.9.14 is not supported anymore and is not compatible with the latest Eclipse 2021-09 release. The entry on the Eclipse marketplace is flagged as available for Eclipse 2020-09 as the latest. We do provide Spring Tools 3 distribution builds for newer Eclipse versions, but since they are not supported anymore for a long time now, those are not well tested. If you urgently need that version, you can get them from here: https://github.com/spring-projects/toolsuite-distribution/wiki/Spring-Tool-Suite-3

- Eclipse version 별 호환 JDK version 정리 https://oingdaddy.tistory.com/269
- page 35 
```
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>2.5.1</version>
                <configuration>
                    <source>11</source>
                    <target>11</target>
                    <compilerArgument>-Xlint:all</compilerArgument>
                    <showWarnings>true</showWarnings>
                    <showDeprecation>true</showDeprecation>
                </configuration>
            </plugin>
```
Project configurator "com.springsource.sts.ide.maven.core.springProjectConfigurator" required by plugin execution "org.apache.maven.plugins:maven-compiler-**plugin:2.5.1:testCompile (execution: default-testCompile, phase: test-compile)" is not available.** To enable full functionality, install the project configurator and run Maven->Update Project Configuration.

1. 프로젝트 우클릭 > Run As > Maven Install
2. 프로젝트 우클릭 > Maven > Update Project 
3. 프로젝트 우클릭 > Run As > Run on Server
