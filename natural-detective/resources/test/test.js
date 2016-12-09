// https://github.com/emezeske/lein-cljsbuild/blob/1.1.5/example-projects/
// advanced/phantom/unit-test.js

var url = require('system').args[1];
var page = require('webpage').create();

page.onConsoleMessage = function(message) {
    console.log('cljs.test=> ' + message.trim());
};

page.open(url, function(status) {
    page.evaluate(function() {
        game.test.run();
    });
    phantom.exit(0);
});
