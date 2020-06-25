var exec = require('cordova/exec');

exports.getInstallationId = function(success, error) {
    exec(success, error, "KaribooPlugin", "getInstallationId", []);
};

exports.getPreferences = function(success, error) {
    exec(success, error, "KaribooPlugin", "getPreferences", []);
};

exports.setIABConsentCMPPresent = function(iABConsentCMPPresent, success, error) {
    exec(success, error, "KaribooPlugin", "setIABConsentCMPPresent", [iABConsentCMPPresent]);
};

exports.setIABConsentSubjectToGDPR = function(iABConsentSubjectToGDPR, success, error) {
    exec(success, error, "KaribooPlugin", "setIABConsentSubjectToGDPR", [iABConsentSubjectToGDPR]);
};

exports.setIABConsentConsentString = function(iABConsentCMPPresent, success, error) {
    exec(success, error, "KaribooPlugin", "setIABConsentConsentString", [iABConsentCMPPresent]);
};

exports.setIABConsentParsedPurposeConsents = function(iABConsentParsedPurposeConsents, success, error) {
    exec(success, error, "KaribooPlugin", "setIABConsentParsedPurposeConsents", [iABConsentParsedPurposeConsents]);
};

exports.setIABConsentParsedVendorConsents = function(iABConsentParsedVendorConsents, success, error) {
    exec(success, error, "KaribooPlugin", "setIABConsentParsedVendorConsents", [iABConsentParsedVendorConsents]);
};

exports.requestLocationPermission = function(success, error) {
    exec(success, error, "KaribooPlugin", "requestLocationPermission", []);
};

exports.getLocationPermission = function(success, error) {
    exec(success, error, "KaribooPlugin", "getLocationPermission", []);
};

