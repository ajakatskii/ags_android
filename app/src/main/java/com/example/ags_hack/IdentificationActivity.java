package com.example.ags_hack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.speech.tts.Voice;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.voiceit.voiceit2.VoiceItAPI2;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class IdentificationActivity extends AppCompatActivity {

    private static final String KEY = "key_612a5fdf8d3b4179affad55494d19984";
    private static final String TOKEN = "tok_191b02eac0e543b29b80c9b3f1b88909";
    private static final String PHRASE = "never forget tomorrow is a new day";
    private static final String USERID = "usr_35f1994903a542909f18dcf2e5a2154b";

    private VoiceItAPI2 api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        this.api = new VoiceItAPI2(KEY,TOKEN);
        this.api.encapsulatedVoiceEnrollment(this,USERID,"en-US",PHRASE, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("My_API JSONResult : " + response.toString());
                verifyWithVoice();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    System.out.println("My_API JSONResult : " + errorResponse.toString());
                }
            }
        });
    }

    protected void verifyWithVoice(){
        api.encapsulatedVoiceVerification(this, USERID, "en-US", PHRASE, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("encapsulatedVoiceVerification onSuccess Result : " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //checkResponse(errorResponse);
                if (errorResponse != null) {
                    System.out.println("encapsulatedVoiceVerification onFailure Result : " + errorResponse.toString());
                }
            }
        });
    }





}
