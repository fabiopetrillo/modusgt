@echo off

set TMP_CLASSPATH=%CLASSPATH%

for %%i in (".\lib\*.jar") do call "path.bat" %%i

set MODUS_CLASSPATH=%CLASSPATH%
set CLASSPATH=%TMP_CLASSPATH%

.\jre1.6.0-win32\bin\java -Djava.naming.provider.url=10.110.115.231:1099 -Dip=10.110.115.81 -Drotulo=procempa02 -Djava.naming.factory.initial=org.jnp.interfaces.NamingContextFactory -Djava.naming.factory.url.pkgs=org.jboss.naming:org.jnp.interfaces -cp modus.jar%MODUS_CLASSPATH% br.com.procempa.modus.Modus

