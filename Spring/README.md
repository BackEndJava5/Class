- 이클립스 최신버전(2022-03 (4.23.0)) 이상 에서는 STS(Spring Tool Suite)를 설치해도 Spring 프로젝트가 생성되지 않습니다.
정확히는 **이클립스 2021-03 (4.19.0)** 까지만 Spring Legacy Project를 생성할 수 있습니다.
-  Eclipse IDE for Enterprise Java and Web Developers https://www.eclipse.org/downloads/packages/release/2021-03/r

### 코배스(개정판)- 2022년 6월 이클립스 메이븐환경 소스코드 
- https://cafe.naver.com/gugucoding?iframe_url_utf8=%2FArticleRead.nhn%253Fclubid%3D28363273%2526menuid%3D28%2526boardtype%3DL%2526page%3D1%2526specialmenutype%3D%2526userDisplay%3D15%2526articleid%3D7819

# Part1 스프링 개발 환경 구축( 완료 )
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
- 사용자 생성(C##book_ex/oracle)
```
CREATE USER C##book_ex IDENTIFIED BY oracle
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP;

GRANT CONNECT, DBA TO C##book_ex;

C##book_ex/oracle 접속
```
- 8080 포트 변경
```
SELECT DBMS_XDB.GETHTTPPORT() FROM DUAL;
EXEC DBMS_XDB.SETHTTPPORT(9090);
```
- JDBC 연결
```
INFO : org.zerock.persistence.JDBCTests - oracle.jdbc.driver.T4CConnection@39d76cb5
```
- 커넥션 풀 설정 ( HikariCP )
- https://mvnrepository.com/artifact/com.zaxxer/HikariCP/2.7.4
- pom.xml
```
<!-- https://mvnrepository.com/artifact/com.zaxxer/HikariCP -->
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>2.7.4</version>
</dependency>
```
- https://github.com/brettwooldridge/HikariCP#configuration-knobs-baby
- root-context.xml
```
	<bean id="hikariConfig" class="com.zaxxer.hikari.HikariConfig">
		<property name="driverClassName"
			value="oracle.jdbc.driver.OracleDriver"></property>
		<property name="jdbcUrl"
			value="jdbc:oracle:thin:@localhost:1521:XE"></property>
		<property name="username" value="C##book_ex"></property>
		<property name="password" value="oracle"></property>
	</bean>

	<!-- HikariCP Configuration -->
	<bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource"
		destroy-method="close">
		<constructor-arg ref="hikariConfig"></constructor-arg>
	</bean>
```
```
INFO : org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor - JSR-330 'javax.inject.Inject' annotation found and supported for autowiring
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
WARN : com.zaxxer.hikari.util.DriverDataSource - Registered driver with driverClassName=oracle.jdbc.driver.OracleDriver was not found, trying direct instantiation.
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
INFO : org.zerock.persistence.DataSourceTests - HikariProxyConnection@697145861 wrapping oracle.jdbc.driver.T4CConnection@497570fb
INFO : org.springframework.context.support.GenericApplicationContext - Closing org.springframework.context.support.GenericApplicationContext@7db12bb6: startup date [Sat Feb 25 01:08:31 KST 2023]; root of context hierarchy
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown completed.
```
## 04 MyBatis와 스프링 연동
### 4.1 MyBatis
- https://mybatis.org/spring/ko/factorybean.html
- https://mvnrepository.com
- pom.xml https://mvnrepository.com/artifact/org.mybatis/mybatis/3.4.6
```
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.6</version>
</dependency>

```
- pom.xml https://mvnrepository.com/artifact/org.mybatis/mybatis-spring/1.3.2
```
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>1.3.2</version>
</dependency>
```
- root-context.xml
```
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
	<property name="dataSource" ref="dataSource" />
</bean>
```
- org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'sqlSessionFactory' defined in URL [file:src/main/webapp/WEB-INF/spring/root-context.xml]: Error setting property values; https://kookyungmin.github.io/server/2018/08/13/spring_06/
- root-context.xml
```
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"></property>
</bean>
```
- src/main/resources에 mybatis-config.xml 파일 추가
```
<!-- mybatis-config.xml -->

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
    PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

</configuration>
```
- root-context.xml
```
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"></property>
    <property name="configLocation" value="classpath:/mybatis-config.xml"></property>
</bean>
```
```
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
INFO : org.zerock.persistence.DataSourceTests - HikariProxyConnection@1099694603 wrapping oracle.jdbc.driver.T4CConnection@d0ec63
INFO : org.zerock.persistence.DataSourceTests - org.apache.ibatis.session.defaults.DefaultSqlSession@6e8a9c30
INFO : org.zerock.persistence.DataSourceTests - HikariProxyConnection@1560406561 wrapping oracle.jdbc.driver.T4CConnection@d0ec63
```
### 4.2 스프링과의 연동 처리
#### 4.2.2 Mapper 테스트
- root-context.xml namespaces tab 에 mybatis-spring 없는 경우 pom.xml 에 프레임워크, 라이브러리 추가가 필요, STS restart 해야 보임 https://hillier.tistory.com/26
```
INFO : org.zerock.persistence.TimeMapperTests - com.sun.proxy.$Proxy26
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.apache.ibatis.reflection.Reflector (file:/C:/Users/iamsu/.m2/repository/org/mybatis/mybatis/3.4.6/mybatis-3.4.6.jar) to method java.lang.String.value()
```
https://github.com/mybatis/mybatis-3/issues/1156
https://sillutt.tistory.com/entry/Mybatis-WARNING-An-illegal-reflective-access-operation-has-occurred	


- pom.xml ( mybatis-3.4.6 -> mybatis-3.5.3 으로 수정 )
```
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
			<version>3.5.3</version>
		</dependency>

		<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis-spring</artifactId>
			<version>1.3.2</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-tx</artifactId>
			<version>${org.springframework-version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
			<version>${org.springframework-version}</version>
		</dependency>
```		
- root-context.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mybatis-spring="http://mybatis.org/schema/mybatis-spring"
	xsi:schemaLocation="http://mybatis.org/schema/mybatis-spring http://mybatis.org/schema/mybatis-spring-1.2.xsd
		http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
	
...
<bean id="sqlSessionFactory"
	class="org.mybatis.spring.SqlSessionFactoryBean">
	<property name="dataSource" ref="dataSource"></property>
</bean>

<mybatis-spring:scan base-package="org.zerock.mapper"/>

<context:component-scan base-package="org.zerock.sample"></context:component-scan>
```
Restart STS.exe

```
INFO : org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor - JSR-330 'javax.inject.Inject' annotation found and supported for autowiring
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
WARN : com.zaxxer.hikari.util.DriverDataSource - Registered driver with driverClassName=oracle.jdbc.driver.OracleDriver was not found, trying direct instantiation.
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
INFO : org.zerock.persistence.TimeMapperTests - com.sun.proxy.$Proxy25
INFO : org.zerock.persistence.TimeMapperTests - 2023-02-25 20:00:59
INFO : org.springframework.context.support.GenericApplicationContext - Closing org.springframework.context.support.GenericApplicationContext@1e0b4072: startup date [Sat Feb 25 20:00:58 KST 2023]; root of context hierarchy
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown completed.
```
#### 4.2.3 XML 매퍼와 같이 쓰기 ( getTime2() )
```
INFO : org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor - JSR-330 'javax.inject.Inject' annotation found and supported for autowiring
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
WARN : com.zaxxer.hikari.util.DriverDataSource - Registered driver with driverClassName=oracle.jdbc.driver.OracleDriver was not found, trying direct instantiation.
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
INFO : org.zerock.persistence.TimeMapperTests - getTime2
INFO : org.zerock.persistence.TimeMapperTests - 2023-02-25 20:17:16
INFO : org.zerock.persistence.TimeMapperTests - com.sun.proxy.$Proxy25
INFO : org.zerock.persistence.TimeMapperTests - 2023-02-25 20:17:16
INFO : org.springframework.context.support.GenericApplicationContext - Closing org.springframework.context.support.GenericApplicationContext@1e0b4072: startup date [Sat Feb 25 20:17:14 KST 2023]; root of context hierarchy
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown completed.
```
### 4.3 log4jdbc-log4j2 설정
- https://kimvampa.tistory.com/63
- Maven repository 사이트(https://mvnrepository.com) 에서 log4jdbc-log4j2를 검색, 라이브러리 추가를 위한 Maven 코드 얻기

```
WARN : org.springframework.context.support.GenericApplicationContext - Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'hikariConfig' defined in URL [file:src/main/webapp/WEB-INF/spring/root-context.xml]: Error setting property values; nested exception is org.springframework.beans.PropertyBatchUpdateException; nested PropertyAccessExceptions (1) are:
PropertyAccessException 1: org.springframework.beans.MethodInvocationException: Property 'driverClassName' threw exception; nested exception is java.lang.NoClassDefFoundError: Unable to find Log4j2 as default logging library. Please provide a logging library and configure a valid spyLogDelegator name in the properties file.
```
- pom.xml ( log4j-api & log4j-core 추가 ) https://okky.kr/articles/665522
```
<!-- https://mvnrepository.com/artifact/org.bgee.log4jdbc-log4j2/log4jdbc-log4j2-jdbc4 -->
<dependency>
	<groupId>org.bgee.log4jdbc-log4j2</groupId>
	<artifactId>log4jdbc-log4j2-jdbc4</artifactId>
	<version>1.16</version>
</dependency>

<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-api</artifactId>
	<version>2.0.1</version>
</dependency>

<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-core</artifactId>
	<version>2.0.1</version>
</dependency>
```	
- JDBCTests_DriverSpy.java 추가(log.warn & log.info() 확인)
```
log4j:WARN Continuable parsing error 56 and column 23
log4j:WARN The content of element type "log4j:configuration" must match "(renderer*,appender*,plugin*,(category|logger)*,root?,(categoryFactory|loggerFactory)?)".
WARN : org.zerock.persistence.JDBCTests_DriverSpy - net.sf.log4jdbc.sql.jdbcapi.ConnectionSpy@1654a892
INFO : org.zerock.persistence.JDBCTests_DriverSpy - net.sf.log4jdbc.sql.jdbcapi.ConnectionSpy@1654a892
net.sf.log4jdbc.sql.jdbcapi.ConnectionSpy@1654a892
INFO : org.zerock.persistence.JDBCTests_DriverSpy - select sysdate from dual: 2023-02-25 21:28:01
select sysdate from dual: 2023-02-25 21:28:01
```
# Part2 스프링 MVC 설정( 완료 )
## 05 스프링 MVC의 기본 구조
- Can not find the tag library descriptor for "http://java.sun.com/jsp/jstl/ core" https://freehoon.tistory.com/78
- pom.xml
```
<dependency>
  <groupId>jstl</groupId>
  <artifactId>jstl</artifactId>
  <version>1.2</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>taglibs</groupId>
  <artifactId>standard</artifactId>
  <version>1.1.2</version>
  <scope>compile</scope>
</dependency>
```
추가 해주니 문제가 해결됐다. 추가해 줬던 코드를 다시 지워보았다. 더이상 빨간 줄이 생기지 않는다.

- jex01
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
```
```
Hello world!
The time on the server is 2023? 2? 28? ?? 12? 51? 29? KST.
```
## 06 스프링 MVC의 Controller
### 6.1 @Controller, @RequestMapping(page 128)
```
INFO : org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/],methods=[GET]}" onto public java.lang.String org.zerock.controller.HomeController.home(java.util.Locale,org.springframework.ui.Model)
INFO : org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/sample/*]}" onto public void org.zerock.controller.SampleController.basic()
```
### 6.2  @RequestMapping의 변화 ( basic() + basicGet(), basicGet2() ) 
```
INFO : org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/sample/*]}" onto public void org.zerock.controller.SampleController.basic()
INFO : org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/sample/basicOnlyGet],methods=[GET]}" onto public void org.zerock.controller.SampleController.basicGet2()
INFO : org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/sample/basic],methods=[GET || POST]}" onto public void org.zerock.controller.SampleController.basicGet()
```
### 6.3 Controller의 파라미터 수집
- http://localhost:8080/sample/ex01?name=AAA&age=10
```
메시지 파일 [/WEB-INF/views/ex01.jsp]을(를) 찾을 수 없습니다.
```
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.SampleController - SampleDTO(name=AAA, age=10)
```
### 6.3.1 파라미터의 수집과 변환
- http://localhost:8080/sample/ex02?name=AAA&age=10
```
메시지  파일 [/WEB-INF/views/ex02.jsp]을(를) 찾을 수 없습니다.
```
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.SampleController - SampleDTO(name=AAA, age=10)
INFO : org.zerock.controller.SampleController - name: AAA
INFO : org.zerock.controller.SampleController - age: 10
```
### 6.3.2 리스트, 배열 처리
- http://localhost:8080/sample/ex02List?ids=111&ids=222&ids=333
```
메시지 파일 [/WEB-INF/views/ex02List.jsp]을(를) 찾을 수 없습니다.
```
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.SampleController - ids: [111, 222, 333]	
```
### 6.3.3 객체 리스트
- Bad request http://localhost:8088/sample/ex02Bean?list[0].name=aaa&list[2].name=bbb
- http://localhost:8080/sample/ex02Bean?list%5B0%5D.name=aaa&list%5B2%5D.name=bbb
```
INFO : org.zerock.controller.SampleController - list dtos: SampleDTOList(list=[SampleDTO(name=aaa, age=0), SampleDTO(name=null, age=0), SampleDTO(name=bbb, age=0)])
```
### 6.3.4 @InitBinder
- http://localhost:8080/sample/ex03?title=test&dueDate=2018-01-01	
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.SampleController - todo: TodoDTO(title=test, dueDate=Mon Jan 01 00:00:00 KST 2018)
```
### 6.3.5 @DateTimeFormat
- http://localhost:8080/sample/ex03?title=test&dueDate=2018/01/01
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.SampleController - todo: TodoDTO(title=test, dueDate=Mon Jan 01 00:00:00 KST 2018)
```
## 6.4 Model이라는 데이터 전달자
### 6.4.1 @ModelAttribute 어노테이션
- http://localhost:8080/sample/ex04?name=aaa&age=11&page=9
```
SAMPLEDTO SampleDTO(name=aaa, age=11)
PAGE 9
```
```
INFO : org.zerock.controller.SampleController - dto: SampleDTO(name=aaa, age=11)
INFO : org.zerock.controller.SampleController - page: 9
```
6.5.1 void 타입
- http://localhost:8080/sample/ex05
```
메시지 파일 [/WEB-INF/views/sample/ex05.jsp]을(를) 찾을 수 없습니다.
```
```
INFO : org.zerock.controller.SampleController - /ex05..........
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
```
6.5.3 객체 타입
- http://localhost:8080/sample/ex06
```
{"name":"홍길동","age":10}
```
```
INFO : org.zerock.controller.SampleController - /ex06..........
```
6.5.4 ResponseEntity 타입
- http://localhost:8080/sample/ex07
```
{"name": "홍길동"}
개발자 도구
요청 URL: http://localhost:8088/sample/ex07
요청 메서드: GET
상태 코드: 200 
원격 주소: [::1]:8080
리퍼러 정책: strict-origin-when-cross-origin
```
```
INFO : org.zerock.controller.SampleController - /ex07..........
```
6.5.5 파일 업로드 처리
- http://localhost:8080/sample/exUpload
```
선택된 파일 없음
선택된 파일 없음
선택된 파일 없음
선택된 파일 없음
선택된 파일 없음
```
```
INFO : org.zerock.controller.SampleController - basic...................
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
```
- 파일 선택 > 제출 후 
- http://localhost:8080/sample/exUploadPost
```
메시지 파일 [/WEB-INF/views/sample/exUploadPost.jsp]을(를) 찾을 수 없습니다.
```
```
INFO : org.zerock.controller.SampleController - /exUpload..........
INFO : org.zerock.controller.SampleController - ----------------------------------
INFO : org.zerock.controller.SampleController - name:ex07.PNG
INFO : org.zerock.controller.SampleController - size:19508
INFO : org.zerock.controller.SampleController - ----------------------------------
INFO : org.zerock.controller.SampleController - name:ex07.PNG
INFO : org.zerock.controller.SampleController - size:19508
INFO : org.zerock.controller.SampleController - ----------------------------------
INFO : org.zerock.controller.SampleController - name:ex07.PNG
INFO : org.zerock.controller.SampleController - size:19508
INFO : org.zerock.controller.SampleController - ----------------------------------
INFO : org.zerock.controller.SampleController - name:ex07.PNG
INFO : org.zerock.controller.SampleController - size:19508
INFO : org.zerock.controller.SampleController - ----------------------------------
INFO : org.zerock.controller.SampleController - name:ex07.PNG
INFO : org.zerock.controller.SampleController - size:19508
````
- page 153 Java 설정을 이용하는 경우(jex01 > Run As > Run on Server 클릭시 자동으로 http://localhost:8080/controller/ load 됨)
```
Hello world!
The time on the server is 2023? 2? 28? ?? 1? 7? 42? KST.
```
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
```
- C:\upload\tmp 가 생성됨
	
6.6 Controller의 Exception 
- Java 설정을 이용하는 경우(jex01, page 158) 에도 동일 결과임.
- http://localhost:8088/sample/ex04?name=aaa&age=bbb&page=9
```
org.springframework.validation.BeanPropertyBindingResult: 1 errors Field error in object 'sampleDTO' on field 'age': rejected value [bbb]; codes [typeMismatch.sampleDTO.age,typeMismatch.age,typeMismatch.int,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [sampleDTO.age,age]; arguments []; default message [age]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'int' for property 'age'; nested exception is java.lang.NumberFormatException: For input string: "bbb"]
org.springframework.web.method.annotation.ModelAttributeMethodProcessor.resolveArgument(ModelAttributeMethodProcessor.java:157)
org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:124)
org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:161)
org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:131)
```
```
[org.springframework.context.support.DefaultMessageSourceResolvable: codes [sampleDTO.age,age]; arguments []; default message [age]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'int' for property 'age'; nested exception is java.lang.NumberFormatException: For input string: "bbb"]
ERROR: org.zerock.exception.CommonExceptionAdvice - {exception=org.springframework.validation.BindException: org.springframework.validation.BeanPropertyBindingResult: 1 errors
Field error in object 'sampleDTO' on field 'age': rejected value [bbb]; codes [typeMismatch.sampleDTO.age,typeMismatch.age,typeMismatch.int,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [sampleDTO.age,age]; arguments []; default message [age]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'int' for property 'age'; nested exception is java.lang.NumberFormatException: For input string: "bbb"]}
```
- page 158 Java 설정을 이용하는 경우(jex01)
- @Log4j에 빨간 줄 https://stage-diary.tistory.com/532
```
					<groupId>com.sun.jmx</groupId>
					<artifactId>jmxri</artifactId>
				</exclusion>
			</exclusions>
			<!-- <scope>runtime</scope> --> 이 부분 
		</dependency>
```
6.6.2 404 에러 
- http://localhost:8088/nopage
```
HTTP 상태 404 – 찾을 수 없음
타입 상태 보고

설명 Origin 서버가 대상 리소스를 위한 현재의 representation을 찾지 못했거나, 그것이 존재하는지를 밝히려 하지 않습니다.
```
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
WARN : org.springframework.web.servlet.PageNotFound - No mapping found for HTTP request with URI [/nopage] in DispatcherServlet with name 'appServlet'
```
- Java 설정을 이용하는 경우(jex01, page 161)
```
No handler found for GET /nopage
org.springframework.web.servlet.DispatcherServlet.noHandlerFound(DispatcherServlet.java:1210)
org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:966)
org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:925)
org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:974)
org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:866)
```
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
WARN : org.springframework.web.servlet.PageNotFound - No mapping found for HTTP request with URI [/nopage] in DispatcherServlet with name 'dispatcher'
ERROR: org.zerock.exception.CommonExceptionAdvice - Exception .......No handler found for GET /nopage
ERROR: org.zerock.exception.CommonExceptionAdvice - {exception=org.springframework.web.servlet.NoHandlerFoundException: No handler found for GET /nopage}
```
# Part3 기본적인 웹 게시물 관리
## 07 스프링 MVC 프로젝트의 기본 
### 7.3.1 pom.xml 의 수정
- JDBCTests 와 DataSourceTests 동작 확인
```
INFO : org.zerock.persistence.JDBCTests - oracle.jdbc.driver.T4CConnection@6e15fe2
```
```
INFO : org.zerock.persistence.DataSourceTests - HikariProxyConnection@1323676377 wrapping net.sf.log4jdbc.sql.jdbcapi.ConnectionSpy@72f8ae0c
INFO : org.zerock.persistence.DataSourceTests - org.apache.ibatis.session.defaults.DefaultSqlSession@7f353d99
INFO : org.zerock.persistence.DataSourceTests - HikariProxyConnection@1179244298 
```
### 7.3.2 테이블 생성과 Dummy(더미) 데이터 생성
```
create sequence seq_board;

create table tbl_board (
    bno number(10, 0),
    title varchar2(200) not null,
    content varchar2(2000) not null,
    writer varchar2(50) not null,
    regdate date default sysdate,
    updatedate date default sysdate
);

alter table tbl_board add constraint pk_board
primary key (bno);

insert into tbl_board (bno, title, content, writer)
values (seq_board.nextval, '테스트 제목', '테스트 내용', 'user00');
```
7.5 Java 설정을 이용하는 경우의 프로젝트 구성(jex02)
- Run on Server 동작 확인

## 08 영속/비즈니스 계층의 CRUD 구현
### 8.1 영속 계층의 구현 준비
```
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
INFO : jdbc.sqlonly - select * from tbl_board where bno > 0 

INFO : jdbc.sqltiming - select * from tbl_board where bno > 0 
 {executed in 147 msec}
INFO : jdbc.resultsettable - 
|----|-------|--------|-------|----------------------|----------------------|
|bno |title  |content |writer |regdate               |updatedate            |
|----|-------|--------|-------|----------------------|----------------------|
|1   |테스트 제목 |테스트 내용  |user00 |2023-02-28 17:40:19.0 |2023-02-28 17:40:19.0 |
|2   |테스트 제목 |테스트 내용  |user00 |2023-02-28 17:42:12.0 |2023-02-28 17:42:12.0 |
|3   |테스트 제목 |테스트 내용  |user00 |2023-02-28 17:42:14.0 |2023-02-28 17:42:14.0 |
|4   |테스트 제목 |테스트 내용  |user00 |2023-02-28 17:42:15.0 |2023-02-28 17:42:15.0 |
|5   |테스트 제목 |테스트 내용  |user00 |2023-02-28 17:42:16.0 |2023-02-28 17:42:16.0 |
|----|-------|--------|-------|----------------------|----------------------|
```
## 8.2 영속 영역의 CRUD 구현

- src\test\resources\log4j.xml 에서 log level 을 info로 변경
```
<logger name="jdbc.audit">
	<level value="info" />
</logger>

<logger name="jdbc.resultset">
	<level value="info" />
</logger>

<logger name="jdbc.connection">
	<level value="info" />
</logger>
```

### 8.2.1 create(insert) 처리
```
INFO : jdbc.audit - 1. Connection.prepareStatement(insert into tbl_board (bno,title,content,writer)
		values (seq_board.nextval, ?, ?, ?)) returned net.sf.log4jdbc.sql.jdbcapi.PreparedStatementSpy@1d901f20
INFO : jdbc.audit - 1. PreparedStatement.setString(1, "새로 작성하는 글") returned 
INFO : jdbc.audit - 1. PreparedStatement.setString(2, "새로 작성하는 내용") returned 
INFO : jdbc.audit - 1. PreparedStatement.setString(3, "newbie") returned 
INFO : jdbc.sqlonly - insert into tbl_board (bno,title,content,writer) values (seq_board.nextval, '새로 작성하는 글', '새로 
작성하는 내용', 'newbie') 

INFO : jdbc.sqltiming - insert into tbl_board (bno,title,content,writer) values (seq_board.nextval, '새로 작성하는 글', '새로 
작성하는 내용', 'newbie') 
```
### 8.2.2 read(select) 처리
```
INFO : jdbc.audit - 1. PreparedStatement.close() returned 
INFO : jdbc.audit - 1. Connection.clearWarnings() returned 
INFO : org.zerock.mapper.BoardMapperTests - BoardVO(bno=null, title=새로 작성하는 글, content=새로 작성하는 내용, writer=newbie, regdate=null, updateDate=null)
```
### 8.2.3 delete 처리
```
INFO : jdbc.audit - 1. Connection.prepareStatement(delete tbl_board where bno = ?) returned net.sf.log4jdbc.sql.jdbcapi.PreparedStatementSpy@1015a4b9
INFO : jdbc.audit - 1. PreparedStatement.setLong(1, 3) returned 
INFO : jdbc.sqlonly - delete tbl_board where bno = 3 

INFO : jdbc.sqltiming - delete tbl_board where bno = 3 
 {executed in 7 msec}
INFO : jdbc.audit - 1. PreparedStatement.execute() returned false
INFO : jdbc.audit - 1. PreparedStatement.getUpdateCount() ret
```
### 8.2.4 update 처리
```
INFO : jdbc.sqlonly - update tbl_board set title= '수정된 제목', content='수정된 내용', writer = 'user00', updateDate = sysdate 
where bno = 5 

INFO : jdbc.connection - 10. Connection opened
INFO : jdbc.audit - 10. Connection.new Connection returned 
INFO : jdbc.audit - 10. Connection.setReadOnly(false) returned 
INFO : jdbc.audit - 10. Connection.setAutoCommit(true) returned 
INFO : jdbc.sqltiming - update tbl_board set title= '수정된 제목', content='수정된 내용', writer = 'user00', updateDate = sysdate 
where bno = 5 
 {executed in 5 msec}
INFO : jdbc.audit - 1. PreparedStatement.execute() returned false
INFO : jdbc.audit - 1. PreparedStatement.getUpdateCount() returned 1
INFO : jdbc.audit - 1. PreparedStatement.close() returned 
```
## 09 비즈니스 계층
### 9.2 비즈니스 계층의 구현과 테스트
```
INFO : org.zerock.service.BoardServiceTests - org.zerock.service.BoardServiceImpl@44032fde
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...
```

### 9.2.1 등록 작업의 구현과 테스트
```
INFO : jdbc.sqltiming - insert into tbl_board (bno,title,content, writer) values (66, '새로 작성하는 글', '새로 작성하는 내용', 'newbie') 
 {executed in 15 msec}
INFO : jdbc.audit - 1. PreparedStatement.execute() returned false
INFO : jdbc.audit - 1. PreparedStatement.getUpdateCount() returned 1
INFO : jdbc.audit - 1. PreparedStatement.close() returned 
INFO : jdbc.audit - 1. Connection.clearWarnings() returned 
INFO : org.zerock.service.BoardServiceTests - 생성된 게시물의 번호: 66
```

### 9.2.2 목록(리스트) 작업의 구현과 테스트
```
|----|---------------------|----------------------|-------|----------------------|----------------------|
|bno |title                |content               |writer |regdate               |updatedate            |
|----|---------------------|----------------------|-------|----------------------|----------------------|
|1   |테스트 제목               |테스트 내용                |user00 |2023-02-28 17:40:19.0 |2023-02-28 17:40:19.0 |
|2   |테스트 제목               |테스트 내용                |user00 |2023-02-28 17:42:12.0 |2023-02-28 17:42:12.0 |
|4   |테스트 제목               |테스트 내용                |user00 |2023-02-28 17:42:15.0 |2023-02-28 17:42:15.0 |
|5   |수정된 제목               |수정된 내용                |user00 |2023-02-28 17:42:16.0 |2023-03-03 09:56:05.0 |
|62  |새로 작성하는 글 select key |새로 작성하는 내용 select key |newbie |2023-03-03 09:47:12.0 |2023-03-03 09:47:12.0 |
|63  |새로 작성하는 글            |새로 작성하는 내용            |newbie |2023-03-03 09:47:12.0 |2023-03-03 09:47:12.0 |
|64  |새로 작성하는 글 select key |새로 작성하는 내용 select key |newbie |2023-03-03 09:56:05.0 |2023-03-03 09:56:05.0 |
|65  |새로 작성하는 글            |새로 작성하는 내용            |newbie |2023-03-03 09:56:05.0 |2023-03-03 09:56:05.0 |
|66  |새로 작성하는 글            |새로 작성하는 내용            |newbie |2023-03-03 10:19:46.0 |2023-03-03 10:19:46.0 |
|----|---------------------|----------------------|-------|----------------------|----------------------|
```
### 9.2.3 조회 작업의 구현과 테스트
```
INFO : jdbc.resultsettable - 
|----|-------|--------|-------|----------------------|----------------------|
|bno |title  |content |writer |regdate               |updatedate            |
|----|-------|--------|-------|----------------------|----------------------|
|1   |테스트 제목 |테스트 내용  |user00 |2023-02-28 17:40:19.0 |2023-02-28 17:40:19.0 |
|----|-------|--------|-------|----------------------|----------------------|

INFO : jdbc.resultset - 1. ResultSet.next() returned false
INFO : jdbc.resultset - 1. ResultSet.close() returned void
```
### 9.2.4 삭제/수정 구현과 테스트
```
INFO : jdbc.sqlonly - delete tbl_board where bno = 2 

INFO : jdbc.sqltiming - delete tbl_board where bno = 2 
 {executed in 2 msec}
INFO : org.zerock.service.BoardServiceTests - REMOVE RESULT: true
```
```
INFO : jdbc.sqlonly - update tbl_board set title= '제목 수정합니다.', content='테스트 내용', writer = 'user00', updateDate = 
sysdate where bno = 1 

INFO : jdbc.sqltiming - update tbl_board set title= '제목 수정합니다.', content='테스트 내용', writer = 'user00', updateDate = 
sysdate where bno = 1 
 {executed in 6 msec}
INFO : org.zerock.service.BoardServiceTests - MODIFY RESULT: true
```