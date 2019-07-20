package com.example.ags_hack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;
import org.json.JSONException;

import java.net.URISyntaxException;
import java.util.logging.Logger;


public class paymentActivity extends AppCompatActivity {

    private static final String SERVER = "";

    private Socket socket;

    private TextView txtPayee;
    private TextView txtAmount;

    protected void msg(String message){
        TextView vw =  this.findViewById(R.id.txtDebug);
        vw.setText(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        this.initControls();
        //init socket
        this.initSocket();
    }

    private void initControls(){
        this.txtPayee = (TextView) this.findViewById(R.id.txtPayee);
        this.txtAmount = (TextView) this.findViewById(R.id.txtAmount);
        this.hideDebugOutput((TextView)this.findViewById(R.id.txtDebug));
    }

    private void initSocket()
    {
        //create socket
        try {
            this.socket = IO.socket(SERVER);
            this.socket.connect();
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
        final paymentActivity thisActivity = this;
        Emitter.Listener onPaymentMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                thisActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String payee;
                        double amt;
                        try {
                            payee = data.getString("payee");
                            amt = data.getDouble("amount");
                            thisActivity.updateDetails(payee, amt);
                        } catch (JSONException e) {
                            return;
                        }
                    }
                });
            }
        };
        this.socket.on("do_payment",onPaymentMessage);
    }

    public void updateDetails(String payee, double amount) {
        this.txtAmount.setText(String.format("Rs. {0}", amount));
        this.txtPayee.setText(String.format("{0}", amount));
    }


    public void hideDebugOutput(View view) {
        view.setVisibility( view.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
    }
}
