JFLAGS = -g -Xlint:unchecked
JC = javac
JRUN = java
LIB = lib/algs4.jar
CLASSES = \
	src/WordNet.java \
	src/SAP.java \
	src/Outcast.java \

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) -d bin -cp $(LIB):src $*.java

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	$(JRUN) -cp bin:$(LIB) Outcast input/wordnet/synsets.txt input/wordnet/hypernyms.txt input/wordnet/outcast5.txt input/wordnet/outcast8.txt input/wordnet/outcast11.txt
	$(JRUN) -cp bin:$(LIB) SAP
	$(JRUN) -cp bin:$(LIB) WordNet

zip:
	cp src/WordNet.java ./
	cp src/SAP.java ./
	cp src/Outcast.java ./
	zip -r wordnet.zip *.java
	$(RM) *.java

clean:
	$(RM) bin/*.class