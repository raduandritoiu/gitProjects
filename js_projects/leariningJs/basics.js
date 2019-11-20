/**
 * Created by Radu on 1/28/2015.
 */

// global working variables

var x = 42;
var y = 32;
var z;
var d = {'aaa':12, bbb:89.3};


// create the obj classes
var createPersType = function (n_fname, n_sname, n_age, n_idx) {
    return {
        fname   : n_fname,
        sname   : n_sname,
        age     : n_age,
        idx     : n_idx,
        mthd    : "created",
        prototype : createPersType.prototype,

        fullName : function() {
            return n_fname + " " + n_sname;
        },
        fullInfo : function() {
            return this.fname + " " + this.sname + " (" + this.mthd + " - " + this.idx + ") - of age " + this.age;
        }
    }
}

var addPersType = function (pers, n_fname, n_sname, n_age, n_idx) {
    pers.fname  = n_fname;
    pers.sname  = n_sname;
    pers.age    = n_age;
    pers.idx    = n_idx;
    pers.mthd   = "added";
    pers.prototype = addPersType.prototype;

    pers.fullName = function() {
        return this.fname + " " + this.sname;
    }
    pers.fullInfo = function() {
        return this.fname + " " + this.sname + " (" + this.mthd + " - " + this.idx + ") - of age " + this.age;
    }
}

var NewPersType = function (n_fname, n_sname, n_age, n_idx) {
    this.fname  = n_fname;
    this.sname  = n_sname;
    this.age    = n_age;
    this.idx    = n_idx;
    this.mthd   = "new";

    this.fullName = function() {
        return this.fname + " " + this.sname;
    }
    this.fullInfo = function() {
        return this.fname + " " + this.sname + " (" + this.mthd + " - " + this.idx + ") - of age " + this.age;
    }
}


// add methods to the classes - before objs
createPersType.prototype.newFunc = function() {
    return "The person (" + this.sname + ", " + this.fname + ", " + this.idx + ") was " + this.mthd;
}
addPersType.prototype.newFunc = function() {
    return "The person (" + this.sname + ", " + this.fname + ", " + this.idx + ") was " + this.mthd;
}
NewPersType.prototype.newFunc = function() {
    return "The person (" + this.sname + ", " + this.fname + ", " + this.idx + ") was " + this.mthd;
}


// create the extended person
var NewStudent = function (n_fname, n_sname, n_age, n_idx, n_nota) {
    this.nota = n_nota;
    NewPersType.call(this, n_fname, n_sname, n_age, n_idx);
}
NewStudent.prototype = Object.create(NewPersType.prototype);
//NewStudent.prototype = {};
//NewStudent.prototype.__proto__ = NewPersType.prototype;
NewStudent.prototype.printNota = function() {
    return "Student (" + this.sname + ", " + this.fname + ", " + this.idx + " - " + this.mthd + ") has grade " + this.nota;
}
NewStudent.prototype.addNota = function(x) {
    this.nota = this.nota + x;
}


// create the objects
var pers_1_1 = createPersType("Alin", "Popescu", 32, 1);
var pers_1_2 = createPersType("Andrei", "Popescu", 77, 2);
//------------
var pers_2_1 = new NewPersType("Gabi", "Ionescu", 54, 101);
var pers_2_2 = new NewPersType("Gigel", "Ionescu", 14, 102);
//------------
var pers_2p_1 = {};
NewPersType.call(pers_2p_1, "Preda", "Ionescu", 27, 201);
var pers_2p_2 = {};
NewPersType.call(pers_2p_2, "Paul", "Ionescu", 23, 202);
//------------
var pers_3_1 = {};
addPersType(pers_3_1, "Vlad", "Sorescu", 51, 1001);
var pers_3_2 = {};
addPersType(pers_3_2, "Viorel", "Sorescu", 48, 1002);
//------------
var stud_1_1 = new NewStudent("Bethoven", "Muz", 29, 2001, 9.21);
var stud_1_2 = {};
NewStudent.call(stud_1_2, "Ravel", "Muz", 28, 2002, 8.97);
stud_1_2.__proto__ = NewStudent.prototype;

// add methods to the classes - after creating objs
createPersType.prototype.anotherFunc = function() {
    return "Added function for person (" + this.sname + ", " + this.fname + ", " + this.idx + ", " +  this.mthd + ")";
}
addPersType.prototype.anotherFunc = function() {
    return "Added function for person (" + this.sname + ", " + this.fname + ", " + this.idx + ", " +  this.mthd + ")";
}
NewPersType.prototype.anotherFunc = function() {
    return "Added function for person (" + this.sname + ", " + this.fname + ", " + this.idx + ", " +  this.mthd + ")";
}



console.log(pers_1_1.fullInfo());
console.log(pers_1_2.fullInfo());
console.log("-");
console.log(pers_2_1.fullInfo());
console.log(pers_2_2.fullInfo());
console.log("-");
console.log(pers_2p_1.fullInfo());
console.log(pers_2p_2.fullInfo());
console.log("-");
console.log(pers_3_1.fullInfo());
console.log(pers_3_2.fullInfo());
console.log("------------------------------");


//console.log(pers_1_1.newFunc()); //-- da undefined
//console.log(pers_1_1.anotherFunc()); //-- da undefined
//console.log(pers_1_2.newFunc()); //-- da undefined
//console.log(pers_1_2.anotherFunc()); //-- da undefined
console.log("-");
console.log(pers_2_1.newFunc());
console.log(pers_2_1.anotherFunc());
console.log(pers_2_2.newFunc());
console.log(pers_2_2.anotherFunc());
console.log("-");
//console.log(pers_2p_1.newFunc()); //-- da undefined
//console.log(pers_2p_1.anotherFunc()); //-- da undefined
//console.log(pers_2p_2.newFunc()); //-- da undefined
//console.log(pers_2p_2.anotherFunc()); //-- da undefined
console.log("-");
//console.log(pers_3_1.newFunc()); //-- da undefined
//console.log(pers_3_1.anotherFunc()); //-- da undefined
//console.log(pers_3_2.newFunc()); //-- da undefined
//console.log(pers_3_2.anotherFunc()); //-- da undefined



console.log(NewPersType.prototype);
console.log(pers_2_1.__proto__);
console.log(NewPersType.prototype === pers_2_1.__proto__);


console.log(stud_1_1.fullInfo());
console.log(stud_1_1.printNota());
console.log(stud_1_1.anotherFunc());
console.log(stud_1_2.fullInfo());
console.log(stud_1_2.printNota());
console.log(stud_1_2.anotherFunc());




console.log("-");





