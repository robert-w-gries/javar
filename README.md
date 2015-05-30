#MiniJava Compiler

##Installation

###Dependencies
* JavaCC for generating updated parsers
* Java 8

###Make
> cd MiniJava-Compiler/src/

> make all

##Usage
> java Main --target targetArch [inputfiles]

> Available target architectures: Mips

##Future Plans
* Implement more exceptions
  * ArithmeticException
  * ClassCastException
  * ClassNotFoundException
  * NegativeArraySizeException
* Expand grammar to match Java syntax
  * Expressions on the left hand side
* Support void methods
* Support threading/concurrency

##Known Issues
