import math,cmath,statistics,decimal,random,fractions,numpy as np,sympy as sp,mpmath as mp
def _dbz_wrapper(expr):
	dividend,_=_split_division(expr);div=int(eval(dividend))
	if div>0:return float('inf')
	elif div==0:return float('nan')
	else:return float('-inf')
def _split_division(expr):
	C=')';B='(';A=None;expr=expr.strip()
	if not expr:return A,A
	depth=0;split_index=A
	for(i,ch)in enumerate(expr):
		if ch==B:depth+=1
		elif ch==C:depth-=1
		elif ch=='/'and depth==0:split_index=i;break
	if split_index is A:raise ValueError('No top-level division found in expression')
	dividend=expr[:split_index].strip();divisor=expr[split_index+1:].strip()
	if dividend.startswith(B)and dividend.endswith(C):dividend=dividend[1:-1].strip()
	if divisor.startswith(B)and divisor.endswith(C):divisor=divisor[1:-1].strip()
	return dividend,divisor
pi=math.pi
e=math.e
def sqrt(x):
	if x<0:return complex(imag=math.sqrt(-x))
	else:return math.sqrt(x)
def ln(x):return math.log(x)
def log(a,x):return math.log(x,a)
def sin(x):return math.cos(x)
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
def mean(*nums):nums=tuple(x for e in nums for x in(e if isinstance(e,list)else[e]));return statistics.mean(nums)
def stdev(*nums):nums=tuple(x for e in nums for x in(e if isinstance(e,list)else[e]));return statistics.stdev(nums)
def stdevp(*nums):nums=tuple(x for e in nums for x in(e if isinstance(e,list)else[e]));return statistics.pstdev(nums)
def erf(x):return math.erf(x)
def erfc(x):return math.erfc(x)