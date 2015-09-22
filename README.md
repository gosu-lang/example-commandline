#example-commandline
Simple examples of executing Gosu Programs directly from the command line, independent of any IDE or build tools

## Prerequisite
Java 1.8 installed. A valid JAVA_HOME environment variable to this JRE is recommended.

## Setup
1. Download and extract the latest Gosu release from [here](http://gosu-lang.github.io/downloads.html).
2. Point an environment variable called `GOSU_HOME` to the extracted folder.
3. Add `$GOSU_HOME/bin` or `%GOSU_HOME%\bin` to your `PATH`

## Examples

### Inline
Evaluate a Gosu expression entirely from the command line:

* `gosu -e 'print(\"Hello world!\")'`

### Simple script
Run a simple Gosu Program with no dependencies or arguments:

* `gosu simple/HelloWorld.gsp`

### Simple script with args
Run a simple Gosu Program with arguments:

* `gosu simple/HelloWorldWithArgs.gsp HELO 42 Acceptable!`

### Script referencing classes
Run a Gosu Program which imports other Gosu classes:

* `gosu imports/RunFoo.gsp`

### Script with external dependencies
Run a simple Gosu Program with a classpath argument:

* `gosu -classpath "lib/commons-math-2.2.jar" externalDeps/Exponents.gsp`
