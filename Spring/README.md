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
### 4.2 스프링과의 연동 
