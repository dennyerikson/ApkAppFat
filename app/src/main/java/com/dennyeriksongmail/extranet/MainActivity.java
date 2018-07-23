package com.dennyeriksongmail.extranet;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import static android.provider.Settings.Global.getString;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    ImageButton imageButton;

    String site = String.valueOf(R.string.site);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        checkConnection();

//        imageButton = (ImageButton) findViewById(R.id.imageButton) ;
        final WebView wv = (WebView) findViewById(R.id.webwiew);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.pb);

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);

        wv.loadUrl("http://dennyeriks0n.pythonanywhere.com/");

        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){

                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url){

                pb.setVisibility(View.INVISIBLE);
                wv.setVisibility(View.VISIBLE);

            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                wv.loadUrl("file:///android_asset/myerrorpage.html");

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setTitle(R.string.app_name);
//        getSupportActionBar().setIcon(getDrawable(R.drawable.icon120));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                wv.loadUrl("http://anhanguera.com/home/");
//                Snackbar.make(view, "Direcionando para o Portal do Aluno", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                wv.loadUrl("http://dennyeriks0n.pythonanywhere.com/");
//            }
//        });
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            Toast.makeText(MainActivity.this, "Conectado", Toast.LENGTH_SHORT).show();
        } else {
            message = "Sorry! Not connected to internet";
            Toast.makeText(MainActivity.this, "Desculpe, não há conexão com a internet", Toast.LENGTH_SHORT).show();
        }
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
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(" Bem vindo ao AppFat");
//
//        builder.setNegativeButton("Entendi", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.show();

        //wv.loadUrl("http://10.100.1.240/hsantos3/Sistemas/ExtranetApps/ResetSenha/#");
        //wv.loadUrl("http://dennyeriks0n.pythonanywhere.com/");
    }

    @Override
    public void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

}
