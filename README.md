# javar

#### Java Compiler written in Java

## Building

### Dependencies
* JavaCC 7.0.3 for generating parsers files
  * Note: This is only needed if the parser files need to be re-generated
* jdk9

### IDE

**Recommended: IntelliJ IDEA**

Whille using an IDE, you can build and run by simply pressing "Run".

### Build from command line

```bash
javac -d out -cp src/ src/Main.java
```

## Usage

```bash
java -cp out Main --mips [inputfiles]
```

`inputfiles` is a space-separated list of at least one file path. The path can be absolute or relative to the current directory.

### Available Target Architectures

* MIPS

## Current Plans
* Refactoring:
  * Upgrade code to incorporate Java 8 features
    * Implement stream api as much as possible
  * Make all fields private/protected (encapsulation)
  * Make appropiate fields static/final (enforce immutability)
  * Implement visitor pattern for assem stage
  * Merge AST node classes for different stages where applicable
* Phase out JavaCC, adapt current generated parser logic into one that we manage directly
  * NOTE: if this is too difficult, implement a recursive descent parser, but that will take a lot of time

## Future Plans
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

## Known Issues
