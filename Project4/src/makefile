all:
	javac Absyn/*.java Parse/*.java Semant/*.java Symbol/*.java Types/*.java

compiler:
	javacc -OUTPUT_DIRECTORY=Parse/ Parse/MiniJavaParser.jj
	javac Absyn/*.java Parse/*.java

clean:
	rm Absyn/*.class &
	rm Parse/*.class &
	rm Semant/*.class &
	rm Symbol/*.class &
	rm Types/*.class