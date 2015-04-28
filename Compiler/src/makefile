#
#  MiniJava Makefile
#

JC = javac
JCC = javacc
JFLAGS = -g

$(VERBOSE).SILENT:

#======================================================================

# Parser Files
JJFILES =	Parse/MiniJavaParser.jj		\
			Semant/ReadAbsyn.jj			\
			Translate/ReadTypes.jj		\
			Assem/ReadFrags.jj

# Packages
ABSYN :=		$(wildcard Absyn/*.java)
ASSEM :=		$(wildcard Assem/*.java)
CANON :=		$(wildcard Canon/*.java)
FRAME :=		$(wildcard Frame/*.java)
MIPS :=			$(wildcard Mips/*.java)
PARSE :=		$(wildcard Parse/*.java)
REGALLOC :=		$(wildcard RegAlloc/*.java)
SEMANT :=		$(wildcard Semant/*.java)
SYMBOL :=		$(wildcard Symbol/*.java)
TEMP :=			$(wildcard Temp/*.java)
TRANSLATE :=	$(wildcard Translate/*.java)
TREE :=			$(wildcard Tree/*.java)
TYPES :=		$(wildcard Types/*.java)

# Java Source Files
JAVAFILES =	$(ABSYN)		\
			$(ASSEM)		\
			$(CANON)		\
			$(FRAME)		\
			$(MIPS)			\
			$(PARSE)		\
			$(REGALLOC)		\
			$(SEMANT)		\
			$(SYMBOL)		\
			$(TEMP)			\
			$(TRANSLATE)	\
			$(TREE)			\
			$(TYPES)

# Out Files
CLASSFILES = ${JAVAFILES:%.java=%.class}

#======================================================================

# Target rules

all: $(JC)

$(JC): $(CLASSFILES)

# javac -<flags> <java_file>
%.class: %.java
	@echo -e "\tCompiling" $@
	$(JC) $(JFLAGS) $<

# Generate parser java files
parsers: $(JJFILES)

# Without Phony, MAKE improperly recognizes jj files as up-to-date
.PHONY: $(JJFILES)

# javacc -OUTPUT_DIRECTORY=<directory> <parser_name>
$(JJFILES):
	@echo -e "\tGenerating" $@
	$(JCC) -OUTPUT_DIRECTORY=$(dir $@) $@

clean:
	@echo -e "\tCleaning class files"
	rm -f *.class $(CLASSFILES)
