
//var adder = function(x, y){ return x + y; }
//
//var a = [1,2,3,4];
//var b = _.reduce(a, adder, 0);
//a.push(5);
//
/////////////////////////////
//
//var db = new RhinoDb();
//
//var c = db.simpleList();
//var d = _.reduce(c, adder, 0);
//
/////////////////////////////
//
//var e = {'xxx':3, 'yyy':4};
//var f = db.simpleMap();
//
//var g = db.nestedList();

//var foo = function() {
//    var 
//    var q = table.readAll("site");
//    for (var i = 0; i < q.length; i++)
//        print(q[i]);
//}

var table = new Table();
var sites = table.readAll("sites");
