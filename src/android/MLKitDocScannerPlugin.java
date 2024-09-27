package com.example.mlkit.docscanner;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import android.Manifest;

import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions;
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning;
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult;
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MLKitDocScannerPlugin extends CordovaPlugin {

    private CallbackContext callbackContext;
    private ActivityResultLauncher<IntentSenderRequest> scannerLauncher;
    private ActivityResultLauncher<String[]> requestPermissionsLauncher;

    private static final String[] REQUIRED_PERMISSIONS = {
        Manifest.permission.CAMERA
    };
    
    @Override
    protected void pluginInitialize() {
        scannerLauncher = cordova.getActivity().registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    GmsDocumentScanningResult scanningResult = GmsDocumentScanningResult.fromActivityResultIntent(result.getData());
                    if (scanningResult != null) {
                        processResult(scanningResult);
                    } else {
                        callbackContext.error("Scanning result is null");
                    }
                } else {
                    callbackContext.error("Scanning cancelled or failed");
                }
            }
        );
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        System.out.println("MLKitDocScannerPlugin: execute called with action: " + action);
        this.callbackContext = callbackContext;

        if (action.equals("scanDocument")) {
            System.out.println("MLKitDocScannerPlugin: scanDocument called");
            startDocumentScanner();
            //initialize();
            //checkPermissionsAndScan();
            return true;
        }

        return false;
    }

    private void startDocumentScanner() {
        System.out.println("MLKitDocScannerPlugin: startDocumentScanner called");
        GmsDocumentScannerOptions options = new GmsDocumentScannerOptions.Builder()
            .setGalleryImportAllowed(true)
            .setPageLimit(5)
            .setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_JPEG, GmsDocumentScannerOptions.RESULT_FORMAT_PDF)
            .build();

        GmsDocumentScanner scanner = GmsDocumentScanning.getClient(options);
        scanner.getStartScanIntent(cordova.getActivity())
        .addOnSuccessListener(intentSender -> {
            System.out.println("MLKitDocScannerPlugin: Got start scan intent");
            scannerLauncher.launch(new IntentSenderRequest.Builder(intentSender).build());
        })
        .addOnFailureListener(e -> {
            System.out.println("MLKitDocScannerPlugin: Failed to start scanner: " + e.getMessage());
            callbackContext.error("Failed to start scanner: " + e.getMessage());
        });
    }

    private void processResult(GmsDocumentScanningResult result) {
        if (result == null) {
            callbackContext.error("Scanning result is null");
            return;
        }
        
        JSONObject resultJson = new JSONObject();
        try {
            resultJson.put("pageCount", result.getPages().size());
            // Add more result processing as needed
            callbackContext.success(resultJson);
        } catch (JSONException e) {
            callbackContext.error("Error processing result: " + e.getMessage());
        }
    }
}