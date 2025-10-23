# java-calculator

This is a basic calculator app built in Java Swing. It features...

## Functionality

TODO

## Install

As far as I'm aware, this project only builds correctly on Windows. To install:

1. Clone this repository

2. Navigate to the folder `java-calculator/`

3. Run `./build`

4. Run `./run`

    For run, you can set any setting value with a flag of the form `-D[setting name]=[value]`. This is parsed in 

If you encounter any issues, make sure Maven and JDK 17 are properly installed and in the PATH.

The flag `-q` or `--quiet` can be passed to either to suppress output, and `-r` / `--run` and `-b` / `--build` can be passed to the scripts respectively to execute both at the same time. (Note that passing settings values will not work if you use `./build -r`)
