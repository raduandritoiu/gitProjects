
    /**
      * all-purpose 'piping' function:
      *
      *     js:   $p(a, b, c); 
      *     axon: a().b().c()
      * 
      * if any of the functions take any params other than the previous result,
      * you have to wrap it in a list:
      *
      *     js:   $p(a, b, [c, 'foo']);
      *     axon: a().b().c('foo')
      */
    function $p() {

        var result = (typeof arguments[0] === 'function') ?
            arguments[0].apply(null, []) :
            arguments[0][0].apply(null, arguments[0].slice(1));

        for (var i = 1; i < arguments.length; i++)
            result = (typeof arguments[i] === 'function') ?
                arguments[i].apply(null, [result]) :
                arguments[i][0].apply(null, [result].concat(arguments[i].slice(1)));

        return result;
    }

    ////////////////////////////////////////////////////////

    function aaa() {
        return 'aaa';
    }

    function bbb(p1) {
        return 'bbb' + p1;
    }

    function ccc(p1, p2) {
        return 'ccc' + p1 + p2;
    }

    ////////////////////////////////////////////////////////

    var x = ccc(bbb(aaa()), 'zzz');

    var y = $p(aaa, bbb, [ccc, 'zzz']);

    console.log(x);
    console.log(y);
    if (x !== y) throw "Oops!";
