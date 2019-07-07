JFLAGS=-g
JC=javac
JVM=java
CLASSPATH="."
# -classpath

run:
	cd build/classes && $(JVM) graffitude.Graffitude

clean:
	rm build/classes/graffitude/*.class

compile:
	$(mkdir build/classes 2> /dev/null)
	$(JC) -g -d build/classes src/graffitude/*.java

example:
	 $(JVM) -jar dist/Graffitude.jar 'in-A.jpg' diff 'in-B.jpg'



