# Jointag Proximity Cordova plugin

## Table of Contents

1. [Installation and usage](#installation-and-usage)
2. [Requirements and compatibility](#requirements-and-compatibility)
3. [Initialization](#initialization)
4. [GDPR](#gdpr)
5. [Troubleshooting](#troubleshooting)
## Installation and usage

### Installation

Make sure you have a Jointag Proximity / Kariboo client id (API key) and secret.

Run the following command to add the plugin to your cordova project:
```bash
$ cordova plugin add @jointag/cordova-plugin-jointag-proximity --save --variable KARIBOO_ID="YOUR_API_KEY" --variable KARIBOO_SECRET="YOUR_API_SECRET"
```

If you need to change your `KARIBOO_ID` or `KARIBOO_SECRET` after installation of the plugin, it's recommended that you remove and then re-add the plugin as above.
Note that changes to the `KARIBOO_ID` or `KARIBOO_SECRET` value in your `config.xml` file will *not* be propagated to the individual platform builds.

### Tracking users

The SDK associates each tracked request with the *advertisingId*. If the *advertisingId* is not available due to a user permission denial, the device can be identified by the *installationId*. The *installationId* identifies in particular a specific installation of the SDK in a certain app on a certain device. If the app containing the SDK is uninstalled and then installed again the *installationId* will be a different one. You can retrieve the *installationId* after the initialization of the SDK anywhere in your code with the following line:

```javascript
Kariboo.getInstallationId(function (value) {
  alert("installationId: " + value);
});
```

## Requirements and compatibility

### Cordova requirements

Cordova CLI version: from `7.0.1` to  `8.1.2`
### Android requrements

Minimum cordova-android version: `8.0.0`
Minimum API level: `14` (Android 4.0)
> **Note**: to use functionalities that rely on BLE, the minimum API level is `18` (Android 4.3). If the device API level is between `14` and `17` the SDK won't be able to access BLE and therefore it will be not possible to obtain data from BLE devices.

### iOS requirements

Minimum cordova-ios version: `5.0.1`

This plugin requires the following permissions on iOS: 
- NSLocationWhenInUseUsageDescription
- NSLocationAlwaysAndWhenInUseUsageDescription
- NSLocationAlwaysUsageDescription

In order to publish your app on App Store without rejection customize permission strings in ios platform tag of your config.xml:
 ```xml
        <config-file target="*-Info.plist" parent="NSLocationWhenInUseUsageDescription">
            <string>Questa applicazione ha bisogno di accedere alla tua posizione per fornirti un'esperienza ottimale.
            </string>
        </config-file>
        <config-file target="*-Info.plist" parent="NSLocationAlwaysAndWhenInUseUsageDescription">
            <string>Questa applicazione ha bisogno di accedere alla tua posizione per fornirti un'esperienza ottimale.
            </string>
        </config-file>
        <config-file target="*-Info.plist" parent="NSLocationAlwaysUsageDescription">
            <string>Questa applicazione ha bisogno di accedere alla tua posizione per fornirti un'esperienza ottimale.
            </string>
        </config-file>
  ```
##Initialization

This plugin takes advantage of Cordova plugin onload phase to initialize Kariboo native SDK, thus no explicit initialization is required.

##GDPR
As a publisher, you should integrate a Consent Management Platform (CMP) and request for vendor and purpose consents as outlined in IAB Europe’s Mobile In-App CMP API v1.0: Transparency & Consent Framework.
To ensure that the SDK support the handling of user-consent preferences when a IAB-compatible CMP library is present, you must enable the feature through the `ENABLE_CMP` installation variable parameter, 
that expects "true" or "false" value. The default value is true.
You can find a reference implementation of a web-based CMP and the corresponding native wrappers here: https://github.com/InteractiveAdvertisingBureau/GDPR-Transparency-and-Consent-Framework.
If you are embedding your own custom CMP, the collected end user consent information needs to be stored invoking the following methods of the plugin:


Key	Value

| Key                               | Type    | Description                     |
| --------------------------------- | ------- | ------------------------------- |
| setIABConsentCMPPresent           | String  | 'true' if a CMP that follows the iAB specification is present in the application otherwise 'false' |
| setIABConsentSubjectToGDPR        | String  | `1` - (subject to GDPR), `0` - (not subject to GDPR), `-1` - Unknown (default before initialization) |
| setIABConsentConsentString        | String  | (Base64-encoded consent string as defined in Consent string and vendor list format v1.1) |
| setIABConsentParsedPurposeConsents| String  | (String of `0`s and `1`s, where the character at position N indicates the consent status to purposeID N as defined in the Global Vendor List) |
| setIABConsentParsedVendorConsents | String  | (String of `0`s and `1`s, where the character at position N indicates the consent status to vendorID N as defined in the Global Vendor List) |


## Troubleshooting

#### Multidex error
if you occur in multidex error with the following message : 

```The number of method references in a .dex file cannot exceed 64k```

you can fix it with ```cordova-plugin-enable-multidex```  plugin 

## Google Play Services and Android Support Library compatibility

As you can see [here](https://github.com/jointag/JTProximitySDK-Android) this plugin uses internally Google Play 
Services library (version >= 11.6.0) and Android Support Library library (version >= 26.1.0), if you have other
 cordova plugins using these libraries, in order of avoid conflicts, you should specify the correct version during the installation. 
 Please note the minimum supported versions. 
- APPCOMPACT_V7_VERSION indicates the version of com.android.support:appcompat-v7 where the default value is 28.0.0
- PLAY_SERVICES_ADS_VERSION indicates the version of com.google.android.gms:play-services-ads where the default value is 16.+
- PLAY_SERVICES_LOCATION_VERSION indicates the version of com.google.android.gms:play-services-location where the default value is 16.+

E.g.

```bash
$ cordova plugin add @jointag/cordova-plugin-jointag-proximity --save --variable KARIBOO_ID="YOUR_API_KEY" --variable KARIBOO_SECRET="YOUR_API_SECRET --variable APPCOMPACT_V7_VERSION="28.0.0" --variable APPCOMPACT_V7_VERSION="16.+"
```

