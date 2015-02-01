@echo off
cls

rem set JAVA=C:\jdk1.5.0_22
set JAVA=C:\jdk1.6.0_45

set PARAM=-Xlint:unchecked -deprecation
set TOMCAT=..\..\..\Tomcat\5.0

rem set JAVA=C:\j2sdk1.4.2_19
rem set PARAM=
rem set TOMCAT=C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\common\lib
rem set TOMCAT=C:\Program Files\Apache Software Foundation\Tomcat 5.5\common\lib

@echo on
"%JAVA%\bin\javac" %PARAM% -classpath "%JAVA%\lib\tools.jar;%TOMCAT%\jsp-api.jar;%TOMCAT%\servlet-api.jar;..\..\lib\activation.jar;..\..\lib\mail.jar;..\..\lib\log4j.jar;..\..\lib\jcs.jar" %1*.java |more
@echo off

rem "%JAVA%\bin\javac" %PARAM% -classpath "%JAVA%\lib\tools.jar;%TOMCAT%\jsp-api.jar;%TOMCAT%\servlet-api.jar;..\..\lib\activation.jar;..\..\lib\mail.jar;..\..\lib\log4j.jar;..\..\lib\jcs.jar" %1*.java |more

rem "%JAVA%\bin\javac" -deprecation -classpath "%JAVA%\lib\tools.jar;%TOMCAT%\jsp-api.jar;%TOMCAT%\servlet-api.jar;..\..\lib\activation.jar;..\..\lib\mail.jar;..\..\lib\log4j.jar;..\..\lib\jcs.jar" *.java |more
rem "%JAVA%\bin\javac" -deprecation -classpath "%JAVA%\lib\tools.jar;%TOMCAT%\jsp-api.jar;%TOMCAT%\servlet-api.jar;%TOMCAT%\activation.jar;%TOMCAT%\mail.jar;..\..\lib\log4j.jar;..\..\lib\jcs.jar" *.java |more
