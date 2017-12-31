JFLAGS = -g
JC = javac
JRUN = java
LIB = lib/algs4.jar
CLASSES = \
	src/burrows/MoveToFront.java \
	src/burrows/CircularSuffixArray.java \
	src/burrows/BurrowsWheeler.java

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) -d bin -cp $(LIB):src/burrows $*.java

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	# $(JRUN) -cp bin:$(LIB) MoveToFront - < input/burrows/abra.txt | $(JRUN) -cp bin:$(LIB) edu.princeton.cs.algs4.HexDump 16
	# $(JRUN) -cp bin:$(LIB) MoveToFront - < input/burrows/abra.txt | $(JRUN) -cp bin:$(LIB) MoveToFront +
	# $(JRUN) -cp bin:$(LIB) CircularSuffixArray
	$(JRUN) -cp bin:$(LIB) BurrowsWheeler - < input/burrows/abra.txt | $(JRUN) -cp bin:$(LIB) edu.princeton.cs.algs4.HexDump 16
	$(JRUN) -cp bin:$(LIB) BurrowsWheeler - < input/burrows/abra.txt | $(JRUN) -cp bin:$(LIB) BurrowsWheeler +


zip:
	cp src/burrows/MoveToFront.java ./
	cp src/burrows/CircularSuffixArray.java ./
	cp src/burrows/BurrowsWheeler.java ./
	zip -r burrows.zip *.java
	$(RM) *.java

clean:
	$(RM) bin/*.class