JFLAGS=-g
JC=javac
JVM=java 
CLASSPATH="."
# -classpath

run:
	cd classes && $(JVM) graffitude.Graffitude

compile:
	$(mkdir classes 2> /dev/null)
	$(JC) -g -d classes src/graffitude/*.java

example:
	 $(JVM) -jar dist/Graffitude.jar 'in-A.jpg' diff 'in-B.jpg'



