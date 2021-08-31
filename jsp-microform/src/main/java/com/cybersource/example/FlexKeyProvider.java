package com.cybersource.example;


import java.util.Properties;

import Model.FlexV1KeysPost200Response;
import Model.GeneratePublicKeyRequest;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.KeyGenerationApi;
import Invokers.ApiClient;
import Invokers.ApiException;

public class FlexKeyProvider {

    public String bindFlexKeyToSession() throws Exception {

        String flexPublicKey = "NoKeyReturned";

        try{
            GeneratePublicKeyRequest request = new GeneratePublicKeyRequest();
            request.encryptionType("RsaOaep256");
            request.targetOrigin ("http://localhost:8080");

            Properties props = new Properties();
            // HTTP_Signature = http_signature and JWT = jwt
            props.setProperty("authenticationType", "http_signature");
            props.setProperty("merchantID", "testrest");
            props.setProperty("runEnvironment", "CyberSource.Environment.SANDBOX");
            props.setProperty("requestJsonPath", "src/main/resources/request.json");
            // JWT Parameters
            props.setProperty("keyAlias", "testrest");
            props.setProperty("keyPass", "testrest");
            props.setProperty("keyFileName", "testrest");
            // P12 key path. Enter the folder path where the .p12 file is located.
            props.setProperty("keysDirectory", "src/main/resources");
            // HTTP Parameters
            props.setProperty("merchantKeyId", "08c94330-f618-42a3-b09d-e1e43be5efda");
            props.setProperty("merchantsecretKey", "yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=");
            // Logging to be enabled or not.
            props.setProperty("enableLog", "true");
            // Log directory Path
            props.setProperty("logDirectory", "log");
            props.setProperty("logFilename", "cybs");
            // Log file size in KB
            props.setProperty("logMaximumSize", "5M");

            MerchantConfig merchantConfig = new MerchantConfig(props);

            ApiClient apiClient = new ApiClient();

            apiClient.merchantConfig = merchantConfig;

            KeyGenerationApi keyGenerationApi = new KeyGenerationApi(apiClient);

            FlexV1KeysPost200Response response = keyGenerationApi.generatePublicKey("JWT", request);
            System.out.println("Response :" +response);

            String responseCode = apiClient.responseCode;
            String status = apiClient.status;
            flexPublicKey = response.getKeyId();

            System.out.println("ResponseCode :" +responseCode);
            System.out.println("Status :" +status);
        } catch (ApiException e) {

            e.printStackTrace();
        }

        return flexPublicKey;
    }
}
