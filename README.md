# java-calculator

This is a basic calculator app built in Java Swing. It features...

## Functionality

TODO

## Install

As far as I'm aware, this project only builds correctly on Windows. To install:

1. Clone this repository

2. Navigate to the folder `java-calculator/`

3. Run `./build`

    `-q` or `--quiet` can be passed to `./build` to suppress output to the console.

4. Run `./run`

    The following flags can be passed to `./run`:
    
    - `-D[setting name]=[setting value]` to set settings before the program is opened (this is how all core program behavior, such as debug mode or custom function files must be set)
    - `-q` / `--quiet` to suppress output
    - `-b` / `--build` to build before execution
    - `-w` / `--no-console` to stop the console from opening (this will prevent real-time debug information, though the equivalent log files will still be created if DEBUG_LOG is true)
    - `-d` / `--debug-console` to open the larger debug console instead of the regular calculator

If you encounter any issues, make sure Maven and JDK 17 are properly installed and in the PATH.


