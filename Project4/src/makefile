all:
	javac Absyn/*.java Parse/*.java Semant/*.java Symbol/*.java Types/*.java

compiler:
	javacc -OUTPUT_DIRECTORY=Parse/ Parse/MiniJavaParser.jj
	javac Absyn/*.java Parse/*.java Semant/*.java Symbol/*.java Types/*.java

clean:
	rm -f Absyn/*.class &
	rm -f Parse/*.class &
	rm -f Semant/*.class &
	rm -f Symbol/*.class &
	rm -f Types/*.class
