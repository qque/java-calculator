import math,cmath,statistics,decimal,random,fractions,random,numpy as np,sympy as sp
from sympy.functions.combinatorial.numbers import totient,divisor_sigma,mobius
import mpmath as mp
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
j=complex(imag=1)
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
def AND(*x):return all(x)
def OR(*x):return any(x)
def NOT(x):return not x
def XOR(*args):return any(args)and not all(args)
def NAND(*args):return not all(args)
def mean(*x):return statistics.mean(x)
def stdev(*x):return statistics.stdev(x)
def stdevp(*x):return statistics.pstdev(x)
def variance(*x):return statistics.variance(x)
def skewness(*x):return stdmoment(2,x)
def stdmoment(n,*x):x=np.asarray(x);mean=np.mean(x);std=np.std(x,ddof=0);return np.mean((x-mean)**n)/std**n
def median(*x):return statistics.median(x)
def mode(*x):return statistics.mode(x)
def sort(*x):return sorted(x)
def min(*x):return min(x)
def max(*x):return max(x)
def range(*x):return max(x)-min(x)
def nCr(n,r):return math.comb(n,r)
def nPr(n,r):return math.perm(n,r)
def fact(x):
	if int(x)==x:return math.factorial(x)
	else:return gamma(x+1)
def hyperfact(num):
	val=1
	for i in range(1,num+1):val=val*pow(i,i)
	return val
def multinomial(*ks):
	n=sum(ks);result=math.factorial(n)
	for k in ks:result//=math.factorial(k)
	return result
def rand(a,b):return random.randint(a,b)
def gcd(a,b):return math.gcd(a,b)
def lcm(*xs):
	if len(xs)==0:raise ValueError('lcm requires at least one argument')
	xs=[abs(int(x))for x in xs]
	if any(x==0 for x in xs):return 0
	r=1
	for x in xs:r=abs(r//math.gcd(r,x)*x)
	return r
def isprime(n):return sp.isprime(int(n))
def nthprime(n):
	count=0;num=1
	while count<n:
		num+=1
		if isprime(num):count+=1
	return num
def phi(n):
	n=int(n)
	if n==0:raise ValueError('phi(0) undefined')
	return int(totient(n))
def sigma(n,k=1):
	n=int(n);k=int(k)
	if n==0:raise ValueError('sigma undefined for 0')
	return int(divisor_sigma(n,k))
def mobius(n):return int(mobius(int(n)))
def mod(a,m):return a%m
from mpmath import mp,gamma,loggamma,beta,digamma,erf,erfc,gammainc,besselj,bessely,besseli,besselk,hyper,zeta,lambertw
mp.mp.dps=50
def gamma_fn(z):return gamma(z)
def lgamma_fn(z):return loggamma(z)
def beta_fn(x,y):return beta(x,y)
def digamma_fn(z):return digamma(z)
def erf_fn(z):return erf(z)
def erfc_fn(z):return erfc(z)
def incomplete_gamma_lower(s,x):return gammainc(s,0,x,regularized=False)
def incomplete_gamma_upper(s,x):return gammainc(s,x,mp.inf,regularized=False)
def bessel_j(nu,z):return besselj(nu,z)
def bessel_y(nu,z):return bessely(nu,z)
def bessel_i(nu,z):return besseli(nu,z)
def bessel_k(nu,z):return besselk(nu,z)
def hyper2f1(a,b,c,z):return hyper([a,b],[c],z)
def riemann_zeta(s):return zeta(s)
def lambert_w(z,k=0):return lambertw(z,k)
def real(x):return x.real
def imag(x):return x.imag