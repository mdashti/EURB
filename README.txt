########################################################################################
For preparing development environment:
########################################################################################
1- Download and install jdk1.6.0_16
	- set Environment Variable (JAVA_HOME) to jdk extracted path
	- set Environment Variable (CLASSPATH) to .
	- add jdk/bin to Environment Variable (PATH)
2- Download and extract apache-tomcat-6.0.35
	- replace apache-tomcat-6.0.35/conf/context.xml with <EURB>/core/conf
	- change email config in apache-tomcat-6.0.35/conf/context.xml
	- disable session serialization by adding this line to apache-tomcat-6.0.35/conf/context.xml (for more information have a look at http://tomcat.apache.org/tomcat-6.0-doc/config/manager.html#Disable_Session_Persistence):
		<Manager pathname="" />
3- Download and install mysql-5.5.19
	- put database user/pass in core/conf/WEB-INF/applicationContext.xml
4- Import EURB project into eclipse
	- File > Import... > Import > General > Existing projects into workspace
5- Copy core/build.properties.example to core/build.properties
	- then change "appserver.home" value in this file to your TOMCAT_HOME
6- Make sure, Apache Ant is installed as a plugin in your Eclipse
7- Build project using core/build.xml ant-script
	- "build" target to build your project
	- "build-jsp" to copy all JSP files only
	- "clean" to clean the project directory in TOMCAT

########################################################################################
To DEBUG:
########################################################################################
1- add this line at top of TOMCAT_HOME/bin/catalina.bat (after @echo off)
	- set JAVA_OPTS=%JAVA_OPTS% -Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n
2- in Eclipse go to run > Debug Configurations... and then double-click on Remote Java Application
	- then config ip and port of debug server

########################################################################################
Tips:
########################################################################################
1- for I18N and other purposes, we should use resource bundle for every string shown to user
	- core/conf/WEB-INF/messages/messages.properties (for now it is only for persian)
	- we should use http://sourceforge.net/projects/eclipse-rbe/ plugin to write and extend this file
