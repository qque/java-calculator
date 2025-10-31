import math,cmath,statistics,decimal,random,fractions,numpy as np,sympy as sp,mpmath as mp
pi=math.pi
e=math.e
sqrt=math.sqrt
ln=lambda x:math.log(x)
log=lambda a,x:math.log(x,a)
sin=math
def cos(x):return math.cos(x)
def tan(x):return math.tan(x)
def asin(x):return math.asin(x)
def acos(x):return math.acos(x)
def atan(x):return math.atan(x)
def csc(x):return 1/math.sin(x)
def sec(x):return 1/math.cos(x)
def cot(x):return 1/math.tan(x)
def acsc(x):return math.asin(1/x)
def asec(x):return math.acos(1/x)
def acot(x):return pi-math.atan(x)
def sinh(x):return math.sinh(x)
def cosh(x):return math.cosh(x)
def tanh(x):return math.tanh(x)
def asinh(x):return math.asinh(x)
def acosh(x):return math.acosh(x)
def atanh(x):return math.atanh(x)
def dsin(x):x=pi*x/180;return math.sin(x)
def dcos(x):x=pi*x/180;return math.cos(x)
def dtan(x):x=pi*x/180;return math.tan(x)
def dasin(x):return math.asin(x)*180/pi
def dacos(x):return math.acos(x)*180/pi
def datan(x):return math.atan(x)*180/pi
def dsinh(x):x=pi*x/180;return math.sinh(x)
def dcosh(x):x=pi*x/180;return math.cosh(x)
def dtanh(x):x=pi*x/180;return math.tanh(x)
def dasinh(x):return math.asinh(x)*180/pi
def dacosh(x):return math.acosh(x)*180/pi
def datanh(x):return math.atanh(x)*180/pi
def dcsc(x):x=pi*x/180;return 1/math.sin(x)
def dsec(x):x=pi*x/180;return 1/math.cos(x)
def dcot(x):x=pi*x/180;return 1/math.tan(x)
def dacsc(x):return math.asin(1/x)*180/pi
def dasec(x):return math.acos(1/x)*180/pi
def dacot(x):return(pi-math.atan(x))*180/pi
def end():import sys;sys.exit(0)
def gamma(x):return math.gamma(x)
def fact(x):return math.gamma(x+1)
def nPr(n,k):return math.perm(n,k)
def nCr(n,k):return math.comb(n,k)
def mean(*A):A=tuple(B for A in A for B in(A if isinstance(A,list)else[A]));return statistics.mean(A)
def stdev(*A):A=tuple(B for A in A for B in(A if isinstance(A,list)else[A]));return statistics.stdev(A)
def stdevp(*A):A=tuple(B for A in A for B in(A if isinstance(A,list)else[A]));return statistics.pstdev(A)
def erf(x):return math.erf(x)
def erfc(x):return math.erfc(x)