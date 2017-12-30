JFLAGS = -g
JC = javac
JRUN = java
LIB = lib/algs4.jar
CLASSES = \
	src/baseball/BaseballElimination.java

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) -d bin -cp $(LIB):src/baseball $*.java

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	$(JRUN) -cp bin:$(LIB) BaseballElimination input/baseball/teams4.txt

zip:
	cp src/baseball/BaseballElimination.java ./
	zip -r baseball.zip *.java
	$(RM) *.java

clean:
	$(RM) bin/*.class