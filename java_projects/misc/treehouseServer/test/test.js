
    var assert  = require('assert')
    var request = require('request');
    var _ = require('underscore');

////////////////////////////////////////////////////////////////
//
//    var WebSocket = require('ws');
//    var ws = new WebSocket('ws://localhost:90/api/table/aaa/listen/');
//
//    ws.on('open', function() {
//        console.log('WS:open');
//        ws.send(JSON.stringify({"msg":"subscribe", "id":"aaa"}));
//    });
//
//    ws.on('close', function() {
//        console.log('WS:close');
//    });
//
//    ws.on('message', function(data, flags) {
//        // flags.binary will be set if a binary data is received
//        // flags.masked will be set if the data was masked
//        console.log('WS:' + data);
//    });
//
//////////////////////////////////////////////////////////////

    var CONTENT_TYPE = 'application/json; charset=UTF-8';

    var HOST = 'http://localhost:90';

    var mode;
    var curTest;

    var states = [
        {id:"@OR", state:"✓", west:"✓", dis:"Oregon"},
        {id:"@VA", state:"✓", east:"✓", dis:"Virginia", nested:{foo:42,bar:99}},
        {id:"@NC", state:"✓", east:"✓", dis:"North Carolina"},
    ];

    var cities = [
        {id:"@OR.PTL", city:"✓", dis:"Portland",   stateRef:"@OR", pop:100},
        {id:"@VA.BLK", city:"✓", dis:"Blacksburg", stateRef:"@VA", pop:200},
        {id:"@NC.ASV", city:"✓", dis:"Asheville",  stateRef:"@NC", pop:300},
    ];

    var disStates = [
        {id:"@OR Oregon",         state:"✓", west:"✓", dis:"Oregon"},
        {id:"@VA Virginia",       state:"✓", east:"✓", dis:"Virginia", nested:{foo:42,bar:99}},
        {id:"@NC North Carolina", state:"✓", east:"✓", dis:"North Carolina"},
    ];

    var disCities = [
        {id:"@OR.PTL Portland",   city:"✓", dis:"Portland",   stateRef:"@OR Oregon", pop:100},
        {id:"@VA.BLK Blacksburg", city:"✓", dis:"Blacksburg", stateRef:"@VA Virginia", pop:200},
        {id:"@NC.ASV Asheville",  city:"✓", dis:"Asheville",  stateRef:"@NC North Carolina", pop:300},
    ];

    /**
      * stringifyParams
      */
    function stringifyParams(params)
    {
        var s = '';
        for (var key in params)
        {
            if (s != '')
                s = s + '&';

            s = s + key + '='; 

            var val = params[key];
            if (typeof val === 'object')
                s = s + JSON.stringify(val);
            else 
                s = s + val;
        }
        return s;
    }

    /**
      * callRest
      */
    function callRest(uri, params, expected)
    {
        console.log(uri + ': ' + mode + ', ' + JSON.stringify(expected));

        var options;

        if (mode == "GET") {
            options = {
                'url': HOST + '/' + uri + '?' + stringifyParams(params),
                'method': 'GET',
                'headers': { 'Content-Type': CONTENT_TYPE }};
        }
        else if (mode == "POST") {
            options = {
                'url': HOST + '/' + uri,
                'method': 'POST',
                'headers': { 'Content-Type': CONTENT_TYPE },
                'body': JSON.stringify(params)};
        }

        request(options, function (error, resp, body) {
            if (expected == "FAIL") {
                assert(resp.statusCode != 200);
            }
            else {
                assert(!error && resp.statusCode == 200);

                var result = JSON.parse(body);

                if ((typeof result === 'object') && (result instanceof Array)) {
                    var func = function(a, b) {
                        var x = JSON.stringify(a);
                        var y = JSON.stringify(b);
                        if (x < y) return -1;
                        if (x > y) return 1;
                        return 0;
                    };
                    result.sort(func);
                    expected.sort(func);
                }

                assert.deepEqual(result, expected);
            }
            nextTest();
        });
    }

    /**
      * isFinished
      */
    function isFinished()
    {
        if (mode == "GET")
        {
            console.log('-----------------------------------------------------------');

            curTest = -1;
            mode = "POST"
            nextTest();
        }
        else
        {
            console.log('-----------------------------------------------------------');
            console.log('Success!');
            console.log();
        }
    }

    /**
      * nextTest
      */
    function nextTest()
    {
        curTest++;
        if (curTest < tests.length)
        {
            var func     = tests[curTest].func;
            var uri      = tests[curTest].uri;
            var params   = tests[curTest].params;
            var expected = tests[curTest].expected;
            func(uri, params, expected);
        }
    }

    function padZero(num)
    {
        return (num < 10) ? "0" + num : "" + num;
    }

    function hisItems()
    {
        items = [];
        for (var day = 1; day < 3; day++)
        {
            for (var hour = 0; hour < 23; hour++)
            {
                var ts = "2014-01-" + padZero(day) + "T" + padZero(hour) + ":00:00-05:00 New_York";
                var num = day*hour;
                items.push({ts: ts, obj: num});

                ts = "2014-01-" + padZero(day) + "T" + padZero(hour+1) + ":00:00-05:00 New_York";
                var rec = {foo: "@foo" + ((day*hour) % 4), num: num};
                items.push({ts: ts, obj: rec});
            }
        }
        return items;
    }

////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////

    var tests = [ 
        { func:callRest, uri:'api/table/aaa/batchInsert', params: {records:states}, expected:"ok"},
        { func:callRest, uri:'api/table/aaa/batchInsert', params: {records:states}, expected:"FAIL"},

        { func:callRest, uri:'api/table/aaa/insert', params: { record: cities[0]}, expected: "ok"},
        { func:callRest, uri:'api/table/aaa/insert', params: { record: cities[1]}, expected: "ok"},
        { func:callRest, uri:'api/table/aaa/insert', params: { record: cities[2]}, expected: "ok"},
        { func:callRest, uri:'api/table/aaa/insert', params: { record: cities[2]}, expected: "FAIL"},

        { func:callRest, uri:'api/table/aaa/read', params: { id: states[0].id}, expected: disStates[0]},
        { func:callRest, uri:'api/table/aaa/read', params: { id: states[1].id}, expected: disStates[1]},
        { func:callRest, uri:'api/table/aaa/read', params: { id: states[2].id}, expected: disStates[2]},
        { func:callRest, uri:'api/table/aaa/read', params: { id: cities[0].id}, expected: disCities[0]},
        { func:callRest, uri:'api/table/aaa/read', params: { id: cities[1].id}, expected: disCities[1]},
        { func:callRest, uri:'api/table/aaa/read', params: { id: cities[2].id}, expected: disCities[2]},

        { func:callRest, uri:'api/table/aaa/read',   params: { id: "@foo"}, expected: "FAIL" },
        { func:callRest, uri:'api/table/aaa/delete', params: { id: "@foo"}, expected: "FAIL" },
        { func:callRest, uri:'api/table/aaa/update', params: { id: "@foo",  diff: { remove: ["abcdef"]}}, expected: "FAIL"}, 

        { func:callRest, uri:'api/table/aaa/read', 
            params: { filter: "(pop == 200) or (pop == 100)" },
            expected: [
                {id:"@OR.PTL Portland", city:"✓", dis:"Portland",   stateRef:"@OR Oregon", pop:100},
                {id:"@VA.BLK Blacksburg", city:"✓", dis:"Blacksburg", stateRef:"@VA Virginia", pop:200}
        ]},

        { func:callRest, uri:'api/table/aaa/update', params: { id: "@NC",  diff: { put: {"abc": 789}, remove: ["dis"]}}, expected: "ok"}, 
        { func:callRest, uri:'api/table/aaa/update', params: { id: "@NC",  diff: { remove: ["state"]}}, expected: "ok"}, 
        { func:callRest, uri:'api/table/aaa/update', params: { id: "@NC",  diff: { put: {"abc": 123}}}, expected: "ok"}, 
        { func:callRest, uri:'api/table/aaa/update', params: { id: "@foo", diff: { remove: ["xyz"]}}, expected: "FAIL"}, 
        { func:callRest, uri:'api/table/aaa/read', params: { id: "@NC"}, expected: {"id":"@NC","abc":123,"east":"✓" }},

        { func:callRest, uri:'api/table/aaa/update', params: { filter: "east", diff: { put: {"foo":"✓"}}}, expected: "ok"},
        { func:callRest, uri:'api/table/aaa/update', params: { filter: "east", diff: { put: {"bar":"✓"}, remove: ["foo"]}}, expected: "ok"},
        { func:callRest, uri:'api/table/aaa/update', params: { filter: "east", diff: { remove: ["east"]}}, expected: "ok"},

        { func:callRest, uri:'api/table/aaa/read', 
            params: { filter: "bar"}, 
            expected: [
                {"id": "@VA Virginia", "bar": "✓", "dis": "Virginia", "state": "✓", "nested": {"bar": 99, "foo": 42}},
                {"id": "@NC", "abc": 123, "bar": "✓"}
        ]},

        { func:callRest, uri:'api/table/aaa/delete', params: { id: cities[0].id}, expected: "ok" },
        { func:callRest, uri:'api/table/aaa/delete', params: { id: cities[1].id}, expected: "ok" },
        { func:callRest, uri:'api/table/aaa/delete', params: { id: cities[2].id}, expected: "ok" },
        { func:callRest, uri:'api/table/aaa/read',   params: { id: cities[0].id}, expected: "FAIL" },

        { func:callRest, uri:'api/table/aaa/delete', params: { filter: "west or bar"}, expected: "ok"}, 

        { func:callRest, uri:'api/table/aaa/read', params: { id: "@asdfads"},   expected: "FAIL"},
        { func:callRest, uri:'api/table/aaa/read', params: { filter: "blork" }, expected: []},

        /////////////////////////////////////////////////////////////////////

        { func:callRest, uri:'api/table/bbb/readTags', params: {}, expected: []}, 

        { func:callRest, uri:'api/table/bbb/insert', params: { record: {"id":"@0", "a":"✓"}}, expected: "ok" },
        { func:callRest, uri:'api/table/bbb/readTags', params: {}, 
            expected: [{"count":1,"name":"a"},{"count":1,"name":"id"}]
        }, 

        { func:callRest, uri:'api/table/bbb/delete', params: { id: "@0"}, expected: "ok" },
        { func:callRest, uri:'api/table/bbb/readTags', params: {}, expected: []}, 

        { func:callRest, uri:'api/table/bbb/batchInsert', params: { 
            records: [{"id":"@0", "a":"✓"}, {"id":"@1", "a":"✓", "b":"✓"}]},
            expected: "ok" },
        { func:callRest, uri:'api/table/bbb/readTags', params: {}, expected: 
            [{"count":2,"name":"a"},{"count":1,"name":"b"},{"count":2,"name":"id"}]
        }, 
        { func:callRest, uri:'api/table/bbb/readTags', params: {}, expected: 
            [{"count":2,"name":"a"},{"count":1,"name":"b"},{"count":2,"name":"id"}]
        }, 

        { func:callRest, uri:'api/table/bbb/update', params: { id: "@1",  diff: { put: {"c": "✓"}, remove: ["b"]}}, expected: "ok"}, 
        { func:callRest, uri:'api/table/bbb/readTags', params: {}, expected: 
            [{"count":2,"name":"a"},{"count":1,"name":"c"},{"count":2,"name":"id"}]
        }, 

        { func:callRest, uri:'api/table/bbb/update', params: { filter: "a", diff: { put: {"d": "✓"}, remove: ["c"]}}, expected: "ok"},
        { func:callRest, uri:'api/table/bbb/readTags', params: {}, expected: 
            [{"count":2,"name":"a"},{"count":2,"name":"d"},{"count":2,"name":"id"}]
        }, 

        { func:callRest, uri:'api/table/bbb/delete', params: { filter: 'd and id == "@1"'}, expected: "ok"}, 
        { func:callRest, uri:'api/table/bbb/readTags', params: {}, expected: 
            [{"count":1,"name":"a"},{"count":1,"name":"d"},{"count":1,"name":"id"}]
        }, 

        { func:callRest, uri:'api/table/bbb/delete', params: { filter: "a"}, expected: "ok"}, 
        { func:callRest, uri:'api/table/bbb/readTags', params:{}, expected: []}, 

        /////////////////////////////////////////////////////////////////////

//        { func:callRest, uri:'api/table/bbb/batchInsert', 
//          params: { records:
//                [{'id':'@foo0', 'bar':'@bar0', 'a':'x'},
//                 {'id':'@foo1', 'bar':'@bar0', 'b':'x'},
//                 {'id':'@foo2', 'bar':'@bar1', 'a':'x'},
//                 {'id':'@foo3', 'bar':'@bar1', 'b':'x'},
//                 {'id':'@bar0', 'bar':'@bar0', 'c':'x'},
//                 {'id':'@bar1', 'bar':'@bar0', 'd':'x'}] }, 
//          expected:"ok"
//        },
//
//        { func:callRest, uri:'api/his/ccc/hisWrite', params: { items: hisItems()}, expected: "ok"}, 
//
//        { func:callRest, uri:'api/table/bbb/delete', params: { filter: "id"}, expected: "ok"}, 

        /////////////////////////////////////////////////////////////////////

        { func:isFinished }
    ];

    console.log('===========================================================');
    console.log("Treehouse JavaScript Test");
    console.log();

    console.log('-----------------------------------------------------------');
    curTest = -1;
    mode = "GET"
    nextTest();
