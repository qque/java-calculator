/*
    Defines functions used in the calculator for the ScriptEngine to evaluate properly

    This is used for most buttons, except for exponentiation (which is parsed into Math.pow beforehand)
    and basic arithmetic operators that already get parsed correctly (including parentheses)

    This code is evaluated by the engine when the application is opened
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
    return nums.reduce((a,b) => a + b, 0) / nums.length;
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


// error function; approximation (from Abramowitz & Stegun 7.1.26), accurate to ~1e-9
function erf(x) {
    const sign = Math.sign(x);
    const ax = Math.abs(x);

    const a1 = 0.254829592;
    const a2 = -0.284496736;
    const a3 = 1.421413741;
    const a4 = -1.453152027;
    const a5 = 1.061405429;
    const p  = 0.3275911;

    const t = 1 / (1 + p * ax);
    const y = 1 - (((((a5*t + a4)*t) + a3)*t + a2)*t + a1)*t * Math.exp(-ax*ax);

    return sign * y;
}


// gamma, factorial, npr, and ncr
function gamma(x) { // lanczos approximation
    const p = [
        676.5203681218851,
        -1259.1392167224028,
        771.32342877765313,
        -176.61502916214059,
        12.507343278686905,
        -0.13857109526572012,
        9.9843695780195716e-6,
        1.5056327351493116e-7
    ];

    if (x < 0.5) {
        // reflection formula
        return Math.PI / (Math.sin(Math.PI * x) * gamma(1 - x));
    } else {
        x -= 1;
        let a = 0.99999999999980993;
        for (let i = 0; i < p.length; i++) {
            a += p[i] / (x + i + 1);
        }
        const t = x + p.length - 0.5;
        return Math.sqrt(2 * Math.PI) * Math.pow(t, x + 0.5) * Math.exp(-t) * a;
    }
}

function fact(x) {
    if (Number.isInteger(x)) {
        var out = 1;
        for (var i = 2; i <= x; i++) {
            out *= i;
        }
        return out;
    } else {
        return gamma(x+1);
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