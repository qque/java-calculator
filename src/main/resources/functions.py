# This file defines all functions used internally within the calculator.
#
# In all cases, functions are optimized for preformance. Typically, this means using libraries
# such as numpy, sympy, or mpmath, though some functions are quicker when a cleverly implemented numerical algorithm.

import math
import cmath
import statistics
import decimal
import random
import fractions

# if not installed by user, an error will be caught within the Engine class
import numpy as np
import sympy as sp
import mpmath as mp

def sqrt(x):
    return math.sqrt(x)