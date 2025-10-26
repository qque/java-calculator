# java-calculator

This is a calculator app built in Swing

## Functionality

TODO

## Install

**NOTE:** As far as I'm aware, this project only builds correctly on Windows. If you dealt with JEP integration in a different way and built it manually, it should theoretically work cross-platform, but I didn't want to have to bother with that.

To install:

1. Clone this repository

2. Navigate to the folder `java-calculator/`

3. Run `./build` (the flag `-q` / `--quiet` can be passed to `./build` to suppress output to the console.)

If you encounter any issues, make sure Maven, JDK 17, and some version of Python >= 3.6 are properly installed and on the PATH. The build script will install necessary python dependencies if not found

## Usage

Once the project has been built, you can open the calculator by running `./run`

The following flags can be passed to `./run`:
    
 - `-D[setting name]=[setting value]` to set core program behavior before execution
 - `-q` / `--quiet` to suppress output
 - `-b` / `--build` to build before execution
 - `-w` / `--no-console` to stop the console from opening (this will prevent real-time debug information, though the equivalent log files will still be created if DEBUG_LOG is true)
 - `-d` / `--debug-console` to open the larger debug console on initialization instead of the regular calculator