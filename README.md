 calculator

This is a calculator app built in Swing using [Jep](https://ninia.github.io/jep/) (Java Embedded Python) on the backend. It supports...

## Functionality

TODO

## Install

**NOTE:** This project only builds on Windows. If you deal with JEP integration in a different way it should theoretically work cross-platform, but I didn't want to bother with that.

To install:

1. Clone this repository

2. Navigate to the folder `calculator/`

3. Run `./build` 

By default, `./build` will run a Maven install with tests skipped and deal with any missing dependencies along the way. The following flags can be passed to `./build`:

 - `-q` / `--quiet` to suppress output
 - `-c` / `--clean` to clean the build (delete the target/ directory). this can be combined with other flags (e.g. `./build -c -m` to run `mvn clean compile`)
 - `-C` / `--clean-only` to clean without doing anything else. this **cannot** be combined with other flags (besides `-q`)
 - `-m` / `--compile` to compile the build without packaging or installing
 - `-p` / `--package` to package the build without installing
 - `-t` / `--test` to use tests, which are always skipped by default. this can be combined with other flags.

If you encounter any issues, make sure Maven, JDK 17, and some version of Python >= 3.6 are properly installed and on the PATH. The build script will install necessary python dependencies if they aren't already.

## Usage

Once the project has been built, you can open the calculator by running `./run`

The following flags can be passed to `./run`:
    
 - `-D[setting name]=[setting value]` to set core program behavior before execution
 - `-q` / `--quiet` to suppress output
 - `-b` / `--build` to build before execution
 - `-w` / `--no-console` to stop the console from opening (this will also disable debug mode, though log files will still be created if DEBUG_LOG is true)
 - `-c` / `--clear-logs` to clear the existing log files
 - `-d` / `--debug-console` to open the debug console instead of the calculator
 - `-t` / `--test` to run tests instead of opening the calculator
 - `-f [file]` to run a specific test file

If you are making changes and want to quickly rebuild to test them out, I would recommend running `./run -b -q -d`.