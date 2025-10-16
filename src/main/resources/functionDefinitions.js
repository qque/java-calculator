/*
    Defines functions used in the calculator for the ScriptEngine to evaluate properly

    This is used for most buttons, except for exponentiation (which is parsed into Math.pow beforehand)
    and basic arithmetic operators that already get parsed correctly (including parentheses)

    Within ../java/calculator/logic/ButtonLogic.java, this code is comp
*/

// simple copies of Math library functions
function sin(x) { return Math.sin(x); }
function cos(x) { return Math.cos(x); }
function tan(x) { return Math.tan(x); }
function asin(x) { return Math.asin(x); }
function acos(x) { return Math.acos(x); }
function atan(x) { return Math.atan(x); }
function sinh(x) { return Math.sinh(x); }
function cosh(x) { return Math.cosh(x); }
function tanh(x) { return Math.tanh(x); }
function asinh(x) { return Math.asinh(x); }
function acosh(x) { return Math.acosh(x); }
function atanh(x) { return Math.atanh(x); }
function sqrt(x) { return Math.sqrt(x); }
function ln(x) { return Math.log(x); }

// slight variants
function csc(x) { return 1/Math.sin(x); }
function sec(x) { return 1/Math.cos(x); }
function cot(x) { return 1/Math.tan(x); }
function acsc(x) { return Math.asin(1/x); }
function asec(x) { return Math.acos(1/x); }
function acot(x) { return Math.PI - Math.atan(x); }
function log(a,x) { return Math.log(x) / Math.log(a); }

// degree variants for trig functions
function dsin(x) { x = Math.PI * x / 180; return Math.sin(x); }
function dcos(x) { x = Math.PI * x / 180; return Math.cos(x); }
function dtan(x) { x = Math.PI * x / 180; return Math.tan(x); }
function dasin(x) { x = Math.PI * x / 180; return Math.asin(x); }
function dacos(x) { x = Math.PI * x / 180; return Math.acos(x); }
function datan(x) { x = Math.PI * x / 180; return Math.atan(x); }
function dsinh(x) { x = Math.PI * x / 180; return Math.sinh(x); }
function dcosh(x) { x = Math.PI * x / 180; return Math.cosh(x); }
function dtanh(x) { x = Math.PI * x / 180; return Math.tanh(x); }
function dasinh(x) { x = Math.PI * x / 180; return Math.asinh(x); }
function dacosh(x) { x = Math.PI * x / 180; return Math.acosh(x); }
function datanh(x) { x = Math.PI * x / 180; return Math.atanh(x); }
function dcsc(x) { x = Math.PI * x / 180; return 1/Math.sin(x); }
function dsec(x) { x = Math.PI * x / 180; return 1/Math.cos(x); }
function dcot(x) { x = Math.PI * x / 180; return 1/Math.tan(x); }
function dacsc(x) { x = Math.PI * x / 180; return Math.asin(1/x); }
function dasec(x) { x = Math.PI * x / 180; return Math.acos(1/x); }
function dacot(x) { x = Math.PI * x / 180; return Math.PI - Math.atan(x); }


// statistical functions
function mean(...nums) {
    return nums.reduce((a,b) => a + b, 0);
}

function stdev(...nums) {
    var variance = 0;
    var mu = mean(...nums);
    var len = nums.length;
    var im = 0;
    for (var i = 0; i < len; i++) {
        im = nums[i] - mu;
        variance += im * im;
    }
    variance /= len;
    return Math.sqrt(variance);
}

function stdevp(...nums) {
    var variance = 0;
    var mu = mean(...nums);
    var len = nums.length;
    var im = 0;
    for (var i = 0; i < len; i++) {
        im = nums[i] - mu;
        variance += im * im;
    }
    variance /= (len - 1);
    return Math.sqrt(variance);
}

function sort(...nums) {
    return nums.sort((a,b) => a - b);
}

// other functions
function fact(x) {
    if (Number.isInteger(x)) {
        var out = 1;
        for (var i = 2; i <= x; i++) {
            out *= i;
        }
        return out;
    } else { // more accurate variant of stirling's approximation
        var a = Math.sqrt(2 * Math.PI * x) * Math.pow(x / Math.E, x);
        var b = Math.pow(x * Math.sinh(1/x), x/2);
        var c = Math.exp(7/324 * 1/(x*x*x*(35*x*x + 33)))
        return a * b * c;
    }
}

function nPr(n,r) {
    var out = 1;
    for (var i = n - r + 1; i <= n; i++) {
        out *= i;
    }
    return out;
}

function nCr(n,r) {
    var out = 1;
    for (var i = n - r + 1; i <= n; i++) {
        out *= i;
        out /= n + 1 - i;
    }
    return out;
}