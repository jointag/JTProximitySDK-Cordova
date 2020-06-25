package com.jointag.cordova;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import com.jointag.proximity.ProximitySDK;
import com.jointag.proximity.util.Logger;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KaribooPlugin extends CordovaPlugin {
    public static final int ACCESS_FINE_LOCATION_REQ_CODE = 0;
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        try {
            if (action.equals("getInstallationId")) {
                this.getInstallationId(callbackContext);
                return true;
            }
            if (action.equals("getPreferences")) {
                this.getPreferences(callbackContext);
                return true;
            } else if (action.equals("setIABConsentCMPPresent")) {
                setIABConsentCMPPresent(args.getString(0));
                return true;
            } else if (action.equals("setIABConsentSubjectToGDPR")) {
                setIABConsentSubjectToGDPR(args.getString(0));
                return true;
            } else if (action.equals("setIABConsentConsentString")) {
                setIABConsentConsentString(args.getString(0));
                return true;
            } else if (action.equals("setIABConsentParsedPurposeConsents")) {
                setIABConsentParsedPurposeConsents(args.getString(0));
                return true;
            } else if (action.equals("setIABConsentParsedVendorConsents")) {
                setIABConsentParsedVendorConsents(args.getString(0));
                return true;
            } else if (action.equals("getLocationPermission")) {
                this.getLocationPermission(callbackContext);
                return true;
            } else if (action.equals("requestLocationPermission")) {
                this.requestLocationPermission();
                return true;
            }
            return false;
        } catch (JSONException e) {
            return false;
        }

    }

    private void getInstallationId(CallbackContext callbackContext) {
        String installationId = null;
        installationId = ProximitySDK.getInstance().getInstallationId();
        callbackContext.success(installationId);
    }
	
    private void getLocationPermission(CallbackContext callbackContext) {
		if (cordova.hasPermission(ACCESS_FINE_LOCATION)) {
			callbackContext.success(1);
		} else {
			callbackContext.success(0);
		}	
    }
	
    private void requestLocationPermission() {
		if (!cordova.hasPermission(ACCESS_FINE_LOCATION)) {
			cordova.requestPermission(this, ACCESS_FINE_LOCATION_REQ_CODE, ACCESS_FINE_LOCATION);
		}
    }

    private void getPreferences(CallbackContext callbackContext){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(cordova.getContext());
        callbackContext.success(new JSONObject(settings.getAll()).toString());
    }
    private void setIABConsentCMPPresent(String iABConsentCMPPresent) {
        if ("true".equalsIgnoreCase(iABConsentCMPPresent)) {
            updatePreferencesWithBoolean("IABConsent_CMPPresent", true);
        } else if ("false".equalsIgnoreCase(iABConsentCMPPresent)) {
            updatePreferencesWithBoolean("IABConsent_CMPPresent", false);
        }
    }


    private void setIABConsentSubjectToGDPR(String iABConsentSubjectToGDPR) {
        updatePreferencesWithString("IABConsent_SubjectToGDPR", iABConsentSubjectToGDPR);
    }

    private void setIABConsentConsentString(String iABConsentConsentString) {
        updatePreferencesWithString("IABConsent_ConsentString", iABConsentConsentString);
    }

    private void setIABConsentParsedPurposeConsents(String iABConsentParsedPurposeConsents) {
        updatePreferencesWithString("IABConsent_ParsedPurposeConsents", iABConsentParsedPurposeConsents);
    }

    private void setIABConsentParsedVendorConsents(String iABConsentParsedVendorConsents) {
        updatePreferencesWithString("IABConsent_ParsedVendorConsents", iABConsentParsedVendorConsents);
    }


    private void updatePreferencesWithString(String key, String value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(cordova.getContext());
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString(key, value);
        prefEditor.apply();
    }

    private void updatePreferencesWithBoolean(String key, Boolean value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(cordova.getContext());
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putBoolean(key, value);
        prefEditor.apply();
    }

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Logger.setTag("JointagProximitySDK.");
        Logger.setLogLevel(Logger.VERBOSE);
        Logger.d("initialize Kariboo Plugin");
        /*if (!cordova.hasPermission(ACCESS_FINE_LOCATION)) {
            getAccessFineLocationPermission(ACCESS_FINE_LOCATION_REQ_CODE);
        }*/

        if (cordova.getActivity().getString(cordova.getActivity().getResources().getIdentifier("enable_cmp", "string", cordova.getActivity().getPackageName())) == "true") {
            Logger.d("enable cmp");
            ProximitySDK.enabledCmp();
        }else{
            Logger.d("cmp is not true");
        }
        String apiKey = "@KARIBOO_ID@";
        String apiSecret = "@KARIBOO_SECRET@";
        ProximitySDK.init(cordova.getActivity().getApplication(), apiKey, apiSecret);
    }

    private void getAccessFineLocationPermission(int requestCode) {
        cordova.requestPermission(this, requestCode, ACCESS_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions,
                                          int[] grantResults) {
        if (requestCode == ACCESS_FINE_LOCATION_REQ_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ProximitySDK.getInstance().checkPendingPermissions();
        }
    }
}
