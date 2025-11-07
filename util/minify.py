#!/usr/bin/env python3

import re
import sys

try:
    import python_minifier
except ImportError:
    import subprocess
    try:
        subprocess.check_call([sys.executable, "-m", "pip", "install", "python_minifier"])
    except subprocess.CalledProcessError as e:
        print(f"Failed to install python_minifier with error code {e.returncode}, try running `python -m pip install python_minifier`\n")
        sys.exit(1)


with open('src/main/resources/functions.py','r') as f:
    contents = f.read()

    def get_global_funcs():
        PATTERN = r'^def\s+([a-zA-Z_][a-zA-Z0-9_]*)\s*\('
        _fn = re.findall(PATTERN, contents, re.MULTILINE)
        
        global _global_f
        _global_f = _fn
    
    if "-g" in sys.argv[1:] or "--minify-globals" in sys.argv[1:]:
        get_global_funcs()
        _rngl = True
    else:
        _rngl = False
        del get_global_funcs

    with open('src/main/resources/minified.py','w') as mf:
        minified = python_minifier.minify(contents,
                        filename="..\\src\\main\\resources\\functions.py",
                        remove_debug=True,
                        preserve_shebang=False,
                        remove_annotations=True,
                        remove_literal_statements=True,
                        rename_locals=True,
                        rename_globals=
                                _rngl,
                        preserve_globals=
                                _global_f if _rngl else [],
                        constant_folding=True,
                        remove_builtin_exception_brackets=True,
                        remove_explicit_return_none=True,
                        hoist_literals=True)

        mf.write(minified)

if "-q" in sys.argv[1:] or "--quiet" in sys.argv[1:]:
    sys.exit(0)

print("Minification complete. Output at ../src/main/resources/minified.py")