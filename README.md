# Pass 1 of 2-pass-assembler
Programmed entirely in Java. Symbol Table & IC are produced as output.
***
# Dependencies
openjdk8 is preferred but may work on a few newer versions of Java.
Please do not try to run it through command line/terminal, as it may show up errors like `Could not find or load main class` 
Use a proper IDE with JRE. You may use [IntelliJ](https://www.jetbrains.com/idea/) *(That's what I used)* or [Eclipse](https://www.eclipse.org/downloads/packages/release/2020-09/r/eclipse-ide-java-developers) or any other IDE of your choice.
***
# Usage
From the root directory of the project,
`cd src`
There you'll find 3 files:
```
code.txt
grab_file.java
parse.java
```
Among these, `grab_file.java` is the main class file. code.txt is the Assembley Language Program that it takes as input.
Before changing the code.txt file, please run the program with default file once and understand the working. It may help you modify the functions of the program.
`parse.java` is the file which does LC processing, Intermediate Code generation and Symbol Table generation. 
It also writes the Intermediate Code to a file that'll get stored in your project's root directory by the name `intermediatecode.txt`.
This file can be used for pass-2 of the assembler.


