# Part1 스프링 개발 환경 
## 01 개발을 위한 준비
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

## 02 스프링의 특징과 의존성 주입

- @RunWith(SpringJUnit4ClassRunner.class) 에러
https://sseoui.tistory.com/77
https://mvnrepository.com/artifact/org.springframework/spring-test/5.0.7.RELEASE

- pom.xml
```
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-test</artifactId>
			<version>${org.springframework-version}</version>
			<!-- <scope>test</scope> -->
		</dependency>
```
- Spring - Log4j cannot be resolved to a type 에러 https://moon1226.tistory.com/93

- pom.xml
```
<artifactId>log4j</artifactId>
...
<!-- <scope>runtime</scope> -->
```
1. 프로젝트 우클릭 > build path > Add Library > JUNIT > JUnit4
2. 프로젝트 우클릭 > Run As > JUnit Test

```
INFO : org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor - JSR-330 'javax.inject.Inject' annotation found and supported for autowiring
INFO : org.zerock.sample.SampleTests - Restaurant(chef=Chef())
INFO : org.zerock.sample.SampleTests - ----------------------
INFO : org.zerock.sample.SampleTests - Chef()
INFO : org.springframework.context.support.GenericApplicationContext - Closing org.springframework.context.support.GenericApplicationContext@b9b00e0: startup date [Fri Feb 24 23:52:59 KST 2023]; root of context hierarchy
```
```
INFO : org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor - JSR-330 'javax.inject.Inject' annotation found and supported for autowiring
INFO : org.zerock.sample.HotelTests - SampleHotel(chef=Chef())
INFO : org.zerock.sample.HotelTests - ---------------
INFO : org.zerock.sample.HotelTests - Chef()
INFO : org.springframework.context.support.GenericApplicationContext - Closing org.springframework.context.support.GenericApplicationContext@506ae4d4: startup date [Sat Feb 25 00:02:14 KST 2023]; root of context hierarchy
```
## 03 스프링과 Oracle Database 연동
- Oracle 18c XE + 'C##'
- sys/oracle
- SQL Developer
- Oracle 서비스 시작

- SQL Developer ORA-28002 7 일안에 비밀번호가 만기될 것입니다. 경고 발생 https://aljjabaegi.tistory.com/301
```
1. 관리자계정(system/oralce)접속
2. select * from dba_profiles where profile='DEFAULT' and resource_name='PASSWORD_LIFE_TIME';
3. alter profile default  limit password_life_time unlimited;
```
