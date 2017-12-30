JFLAGS = -g
JC = javac
JRUN = java
LIB = lib/algs4.jar
CLASSES = \
	src/boggle/BoggleSolver.java \

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) -d bin -cp $(LIB):src/boggle $*.java

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	$(JRUN) -cp bin:$(LIB) BoggleSolver input/boggle/dictionary-yawl.txt

zip:
	cp src/boggle/BoggleSolver.java ./
	zip -r boggle.zip *.java
	$(RM) *.java

clean:
	$(RM) bin/*.class