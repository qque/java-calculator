###
### This file defines all functions used internally within the calculator.
###
### In all cases, functions are optimized for preformance.
### Typically, this means offloading calculation libraries such as numpy, sympy, or mpmath.
### Not only is it more convienient, but it also means computation heavy calculations are faster
###

import math
import cmath
import statistics
import decimal
import random
import fractions
import random

# if not installed by user, an error will be caught within the Engine class
import numpy as np
import sympy as sp
from sympy.functions.combinatorial.numbers import totient, divisor_sigma, mobius # type: ignore
import mpmath as mp
# gmpy2 is detected by mpmath automatically


# find and correct error thrown on division by zero
# this is *only* used when a dbz error has already been thrown, so it does not check again
def _dbz_wrapper(expr: str) -> float:
    dividend, _ = _split_division(expr)
    div = int(eval(dividend)) # type: ignore
    if div > 0:
        return float("inf")
    elif div == 0:
        return float("nan")
    else:
        return float("-inf")

def _split_division(expr: str):
    expr = expr.strip()
    if not expr:
        return None, None

    depth = 0
    split_index = None

    for i, ch in enumerate(expr):
        if ch == '(':
            depth += 1
        elif ch == ')':
            depth -= 1
        elif ch == '/' and depth == 0:
            split_index = i
            break

    if split_index is None:
        raise ValueError("No top-level division found in expression")

    dividend = expr[:split_index].strip()
    divisor = expr[split_index + 1:].strip()

    if dividend.startswith('(') and dividend.endswith(')'):
        dividend = dividend[1:-1].strip()
    if divisor.startswith('(') and divisor.endswith(')'):
        divisor = divisor[1:-1].strip()

    return dividend, divisor


# check if precision is wanted by user, 

pi = math.pi
e = math.e
j = complex(imag=1) # used so j by itself is always treated as 1j

## 
def sqrt(x):
    if x < 0: return complex(imag=math.sqrt(-x))
    else: return math.sqrt(x)

def ln(x):
    return math.log(x)

def log(a,x):
    return math.log(x,a)

# abs already exists built-in

def sin(x):
    return math.cos(x)

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
    return math.asin(x) * 180 / pi

def dacos(x):
    return math.acos(x) * 180 / pi

def datan(x):
    return math.atan(x) * 180 / pi

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
    return math.asinh(x) * 180 / pi

def dacosh(x):
    return math.acosh(x) * 180 / pi

def datanh(x):
    return math.atanh(x) * 180 / pi

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
    return math.asin(1/x) * 180 / pi

def dasec(x):
    return math.acos(1/x) * 180 / pi

def dacot(x):
    return (pi - math.atan(x)) * 180 / pi

def end(): import sys; sys.exit(0)
# start of advanced functions

def AND(*x): return all(x)
def OR(*x): return any(x)
def NOT(x): return not x
def XOR(*args): return any(args) and not all(args)
def NAND(*args): return not all(args)

def mean(*x): return statistics.mean(x)
def stdev(*x): return statistics.stdev(x)
def stdevp(*x): return statistics.pstdev(x)
def variance(*x): return statistics.variance(x)
def skewness(*x): return stdmoment(2, x)
def stdmoment(n, *x):
    x = np.asarray(x)
    mean = np.mean(x)
    std = np.std(x, ddof=0)
    return np.mean((x - mean)**n) / (std**n)
def median(*x): return statistics.median(x)
def mode(*x): return statistics.mode(x)
def sort(*x): return sorted(x)
def min(*x): return min(x)
def max(*x): return max(x)
def range(*x): return max(x) - min(x)
def nCr(n, r): return math.comb(n, r)
def nPr(n, r): return math.perm(n, r)
def fact(x):
    if int(x) == x: return math.factorial(x)
    else: return gamma(x+1)
def hyperfact(num):
    val = 1
    for i in range(1, num + 1):
        val = val * pow(i, i)
    return val
def multinomial(*ks):
    n = sum(ks)
    result = math.factorial(n)
    for k in ks:
        result //= math.factorial(k)
    return result
def rand(a, b): return random.randint(a, b)

def gcd(a: int, b: int) -> int:
    return math.gcd(a, b)

def lcm(*xs: int) -> int:
    if len(xs) == 0:
        raise ValueError("lcm requires at least one argument")
    xs = [abs(int(x)) for x in xs] # type: ignore
    if any(x == 0 for x in xs):
        return 0
    r = 1
    for x in xs:
        r = abs(r // math.gcd(r, x) * x)
    return r

def isprime(n: int) -> bool:
    return sp.isprime(int(n))

def nthprime(n):
    count = 0
    num = 1
    while count < n:
        num += 1
        if isprime(num):
            count += 1
    return num

def phi(n: int) -> int:
    n = int(n)
    if n == 0:
        raise ValueError("phi(0) undefined")
    return int(totient(n))

def sigma(n: int, k: int = 1) -> int:
    n = int(n)
    k = int(k)
    if n == 0:
        raise ValueError("sigma undefined for 0")
    return int(divisor_sigma(n, k))

def mobius(n: int) -> int:
    return int(mobius(int(n)))

def mod(a: int, m: int) -> int:
    return a % m

from mpmath import mp, gamma, loggamma, beta, digamma, erf, erfc, gammainc, besselj, bessely, besseli, besselk, hyper, zeta, lambertw
mp.mp.dps = 50
def gamma_fn(z: complex):
    return gamma(z)
def lgamma_fn(z: complex):
    return loggamma(z)
def beta_fn(x: complex, y: complex):
    return beta(x, y)
def digamma_fn(z: complex):
    return digamma(z)
def erf_fn(z: complex):
    return erf(z)
def erfc_fn(z: complex):
    return erfc(z)
def incomplete_gamma_lower(s: complex, x: complex):
    return gammainc(s, 0, x, regularized=False)
def incomplete_gamma_upper(s: complex, x: complex):
    return gammainc(s, x, mp.inf, regularized=False)
def bessel_j(nu: complex, z: complex):
    return besselj(nu, z)
def bessel_y(nu: complex, z: complex):
    return bessely(nu, z)
def bessel_i(nu: complex, z: complex):
    return besseli(nu, z)
def bessel_k(nu: complex, z: complex):
    return besselk(nu, z)
def hyper2f1(a: complex, b: complex, c: complex, z: complex):
    return hyper([a, b], [c], z)
def riemann_zeta(s: complex):
    return zeta(s)
def lambert_w(z: complex, k: int = 0):
    return lambertw(z, k)

def real(x): return x.real
def imag(x): return x.imag