import python_minifier

with open('src/main/resources/functions.py','r') as f:
    with open('src/main/resources/minified.py','w') as mf:
        mf.write(python_minifier.minify(f.read()))

print("Minification complete. Output at ../src/main/resources/minified.py")