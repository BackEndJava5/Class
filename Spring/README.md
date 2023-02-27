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
# Part2 스프링 MVC 설정
## 05 스프링 MVC의 기본 구조
