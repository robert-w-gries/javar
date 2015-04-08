all:
	javac Absyn/*.java Parse/*.java Semant/*.java Symbol/*.java Types/*.java Translate/*.java

compiler:
	javacc -OUTPUT_DIRECTORY=Parse/ Parse/MiniJavaParser.jj
	javac Absyn/*.java Frame/*.java Mips/*.java Parse/*.java Semant/*.java Symbol/*.java Temp/*.java Translate/*.java Tree/*.java Types/*.java 

clean:
	rm -f Absyn/*.class     &
	rm -f Frame/*.class     &
	rm -f Mips/*.class      &
	rm -f Parse/*.class     &
	rm -f Semant/*.class    &
	rm -f Symbol/*.class    &
	rm -f Temp/*.class      &
	rm -f Translate/*.class &
	rm -f Tree/*.class      &
	rm -f Types/*.class
