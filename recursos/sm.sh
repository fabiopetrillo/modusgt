#!/bin/sh
#############################
#Shell script para execução 
#do modus.
#############################
cd $(dirname $0)

#Configura o classpath
for jar in `ls ./lib`; do export CLASSPATH=$CLASSPATH:./lib/$jar;done;

#Seta variaveis para a VM do arquivo modus.conf
for v in `cat modus.conf`; do export vars=$vars\ -D$v\ ;done;

#Executa o software
./jre1.6.0-linux/bin/java $vars -Djava.naming.factory.initial=org.jnp.interfaces.NamingContextFactory -Djava.naming.factory.url.pkgs=org.jboss.naming:org.jnp.interfaces -cp modus.jar$CLASSPATH br.com.procempa.modus.stationmonitor.StationMonitor

