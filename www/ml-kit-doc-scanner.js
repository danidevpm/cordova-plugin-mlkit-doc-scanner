var exec = require('cordova/exec');

var MLKitDocScanner = {
    scanDocument: function(options, success, error) {
        exec(success, error, 'MLKitDocScannerPlugin', 'scanDocument', [options]);
    }
};

module.exports = MLKitDocScanner;