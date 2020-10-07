# Pass 2 of 2-pass-assembler
Programmed entirely in Java. Machine Code is produced as output.
***
# Dependencies
openjdk11 is preferred but may work on a few previous versions of Java.<br>
Please do not try to run it through command line/terminal, as it may show up errors like `Could not find or load main class`.<br>
Use a proper IDE with JRE. You may use [IntelliJ](https://www.jetbrains.com/idea/) *(That's what I used)* or [Eclipse](https://www.eclipse.org/downloads/packages/release/2020-09/r/eclipse-ide-java-developers) or any other IDE of your choice.
***
# Usage
Get the path to intermediate code, symbol table, literal table and pool table files generated in first pass.<br>
From the root directory of the project,
`cd src`<br><br>
Open the `main.java` file.<br><br>
`Ctrl+F` or `⌘+F` and search for `BufferedReader` in the code. Change the pre-defined paths to paths associated with your files.<br><br>
Run the Code.<br><br>
You'll get a file with name `machinecode.txt` as generated output in the root directory of your project. 


