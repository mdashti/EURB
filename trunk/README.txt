For preparing development environment:
1- Download and install jdk1.6.0_16
	- set Environment Variable (JAVA_HOME) to jdk extracted path
	- set Environment Variable (CLASSPATH) to .
	- add jdk/bin to Environment Variable (PATH)
2- Download and extract apache-tomcat-6.0.35
	- replace apache-tomcat-6.0.35/conf/context.xml with <EURB>/core/conf
	- change email config in apache-tomcat-6.0.35/conf/context.xml
3- Download and install mysql-5.5.19
	- put database user/pass in apache-tomcat-6.0.35/conf/context.xml
4- Import EURB project into eclipse
	- File > Import... > Import > General > Existing projects into workspace
5- Copy core/build.properties.example to core/build.properties
	- then change "appserver.home" value in this file to your TOMCAT_HOME
6- Make sure, Apache Ant is installed as a plugin in your Eclipse
7- Build project using core/build.xml ant-script
	- "build" target to build your project
	- "build-jsp" to copy all JSP files only
	- "clean" to clean the project directory in TOMCAT