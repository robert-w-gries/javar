#MiniJava Compiler

##Installation

###Dependencies
* JavaCC for generating updated parsers
* Java 8

###Make
> cd MiniJava-Compiler/src/

> make all

##Usage
> --mips [inputfiles]

> Available target architectures: Mips

##Current Plans
* Upgrade code to Java 8
  * Implement stream api as much as possible
* Make all fields private/protected
* Make appropiate fields static/final
* Implement visitor pattern for assem stage

##Future Plans
* Implement more exceptions
  * ArithmeticException
  * ClassCastException
  * ClassNotFoundException
  * NegativeArraySizeException
* Expand grammar
  * Expressions on the left hand side
* Support void methods
* Support threading/concurrency

##Known Issues
