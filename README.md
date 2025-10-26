# calculator

This is a calculator app built in Swing

## Functionality

TODO

## Install

**NOTE:** This project only builds on Windows. If you deal with JEP integration in a different way it should theoretically work cross-platform, but I didn't want to bother with that.

To install:

1. Clone this repository

2. Navigate to the folder `calculator/`

3. Run `./build` (the flag `-q` / `--quiet` can be passed to `./build` to suppress output to the console.)

If you encounter any issues, make sure Maven, JDK 17, and some version of Python >= 3.6 are properly installed and on the PATH. The build script will install necessary python dependencies if they aren't already.

## Usage

Once the project has been built, you can open the calculator by running `./run`

The following flags can be passed to `./run`:
    
 - `-D[setting name]=[setting value]` to set core program behavior before execution
 - `-q` / `--quiet` to suppress output
 - `-b` / `--build` to build before execution
 - `-w` / `--no-console` to stop the console from opening (this will prevent real-time debug information, though the equivalent log files will still be created if DEBUG_LOG is true)
 - `-d` / `--debug-console` to open the larger debug console on initialization instead of the regular calculator
 - `-c` / `--clear-logs` to clear the existing log files

If you are making changes to the project and want to rebuild, I would recommend running `./run -b -q`