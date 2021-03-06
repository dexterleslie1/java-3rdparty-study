package com.future.demo.android.websocket;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private WebSocketClient webSocketClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button button = findViewById(R.id.buttonConnect);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webSocketClient!=null) {
                    webSocketClient.close();
                    webSocketClient = null;
                }

                URI uri = URI.create("ws://192.168.1.203:8080/websocketEndpoint");
                String username = "";
                try {
                    username = URLEncoder.encode("A用户", "utf-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
                Map<String, String> headers = new HashMap<>();
                headers.put("username", username);
                webSocketClient = new WebSocketClient(uri, new Draft_6455(), headers, 8000) {
                    @Override
                    public void onOpen(ServerHandshake handshakedata) {
                        Log.d(TAG, "onOpen");
                    }

                    @Override
                    public void onMessage(String message) {
                        Log.d(TAG, "收到消息：" + message);
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        Log.i(TAG, "code=" + code + ",reason=" + reason + ",remote=" + remote);
                    }

                    @Override
                    public void onError(Exception ex) {
                        Log.e(TAG, ex.getMessage(), ex);
                    }

                    @Override
                    public void onWebsocketPing(WebSocket conn, Framedata f) {
                        super.onWebsocketPing(conn, f);
                    }
                };
                webSocketClient.connect();
            }
        });

        button = findViewById(R.id.buttonSend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (webSocketClient != null) {
                        webSocketClient.send("你好，" + new Date());
                    }
                } catch (Exception ex) {
                    Log.e(TAG, ex.getMessage(), ex);
                }
            }
        });

        button = findViewById(R.id.buttonDisconnect);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webSocketClient!=null) {
                    webSocketClient.close();
                    webSocketClient = null;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
