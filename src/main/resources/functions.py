#!/usr/bin/env python3

#
# This file defines all functions used internally within the calculator.
#
# In all cases, functions are optimized for preformance.
# In almost every case, this means using libraries such as numpy, sympy, or mpmath.
#

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

pi = math.pi
e = math.e

def sqrt(x):
    return math.sqrt(x)

def ln(x):
    return math.log(x)

def log(x, a):
    return math.log(x, a)

def abs(x):
    return abs(x)

def sin(x):
    return math.sin(x)

def cos(x):
    return math.cos(x)

def tan(x):
    return math.tan(x)

def asin(x):
    return math.asin(x)

def acos(x):
    return math.acos(x)

def atan(x):
    return math.atan(x)

def csc(x):
    return 1/math.sin(x)

def sec(x):
    return 1/math.cos(x)

def cot(x):
    return 1/math.tan(x)

def acsc(x):
    return math.asin(1/x)

def asec(x):
    return math.acos(1/x)

def acot(x):
    return pi - math.atan(x)

def sinh(x):
    return math.sinh(x)

def cosh(x):
    return math.cosh(x)

def tanh(x):
    return math.tanh(x)

def asinh(x):
    return math.asinh(x)

def acosh(x):
    return math.acosh(x)

def atanh(x):
    return math.atanh(x)

def dsin(x):
    x = pi * x / 180
    return math.sin(x)

def dcos(x):
    x = pi * x / 180
    return math.cos(x)

def dtan(x):
    x = pi * x / 180
    return math.tan(x)

def dasin(x):
    x = pi * x / 180
    return math.asin(x)

def dacos(x):
    x = pi * x / 180
    return math.acos(x)

def datan(x):
    x = pi * x / 180
    return math.atan(x)

def dsinh(x):
    x = pi * x / 180
    return math.sinh(x)

def dcosh(x):
    x = pi * x / 180
    return math.cosh(x)

def dtanh(x):
    x = pi * x / 180
    return math.tanh(x)

def dasinh(x):
    x = pi * x / 180
    return math.asinh(x)

def dacosh(x):
    x = pi * x / 180
    return math.acosh(x)

def datanh(x):
    x = pi * x / 180
    return math.atanh(x)

def dcsc(x):
    x = pi * x / 180
    return 1/math.sin(x)

def dsec(x):
    x = pi * x / 180
    return 1/math.cos(x)

def dcot(x):
    x = pi * x / 180
    return 1/math.tan(x)

def dacsc(x):
    x = pi * x / 180
    return math.asin(1/x)

def dasec(x):
    x = pi * x / 180
    return math.acos(1/x)

def dacot(x):
    x = pi * x / 180
    return pi - math.atan(x)

def end(): import sys; sys.exit(0)
# start of advanced functions

def gamma(x):
    return math.gamma(x)

def fact(x):
    return math.gamma(x+1)

def nPr(n,k):
    return math.perm(n,k)

def nCr(n,k):
    return math.comb(n,k)

def mean(*nums):
    # if lists are passed, flatten them out into nums
    nums = tuple(x for e in nums for x in (e if isinstance(e, list) else [e]))
    return statistics.mean(nums)

def stdev(*nums):
    nums = tuple(x for e in nums for x in (e if isinstance(e, list) else [e]))
    return statistics.stdev(nums)

def stdevp(*nums):
    nums = tuple(x for e in nums for x in (e if isinstance(e, list) else [e]))
    return statistics.pstdev(nums)

def erf(x):
    return math.erf(x)

def erfc(x):
    return math.erfc(x)