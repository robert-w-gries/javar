#MiniJava Compiler

##Installation

###Dependencies
* JavaCC for generating parsers (.java files are included, so you only need this if you are updating the parser)
* Java 8

###Make
> cd MiniJava-Compiler/src/
> make all

###Alternative Installation (without Make)
> cd MiniJava-Compiler
> javac -d out -cp src Main.java

.class files will be located under MiniJava-Compiler/out/

##Usage
> cd MiniJava-Compiler
> java -cp out Main --mips [inputfiles]

`inputfiles` is a space-separated list of at least one file path. The path can be absolute or relative to the current directory.

Available target architectures:
* MIPS
* More to come... (x86, LLVM, JVM bytecode)

##Current Plans
* Refactoring:
  * Upgrade code to incorporate Java 8 features
    * Implement stream api as much as possible
  * Make all fields private/protected (encapsulation)
  * Make appropiate fields static/final (enforce immutability)
  * Implement visitor pattern for assem stage
  * Merge AST node classes for different stages where applicable
* Phase out JavaCC, adapt current generated parser logic into one that we manage directly
  * NOTE: if this is too difficult, implement a recursive descent parser, but that will take a lot of time

##Future Plans
* Implement interpreter until we have a solid architecture implementation (hard to run MIPS on a non-MIPS machine)
* Implement modules off of ECMAScript 6 standard (this will be the first deviation from actual Java)
  * Change CLI to only allow one file as input
* Implement static methods
* Support void methods
* Implement dynamically determined entry point (look for a static main method in the specified class)
* Write to a file instead of stdout
* Implement more exceptions
  * ArithmeticException
  * ClassCastException
  * ClassNotFoundException
  * NegativeArraySizeException
* Expand grammar
  * Expressions on the left hand side
* Support threading/concurrency

##Known Issues
