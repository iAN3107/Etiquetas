package com.cutti.etiquetas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class Box extends AppCompatActivity {

    private WebView mWebView;
    private Button imprimeBoxButton;
    private TextInputEditText manifestoBox;
    private TextInputEditText box;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.box);

        imprimeBoxButton = findViewById(R.id.imprimirBoxButton);
        box = findViewById(R.id.box);
        manifestoBox = findViewById(R.id.manifestoBox);

        imprimeBoxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doWebViewPrint(manifestoBox.getText().toString(), box.getText().toString());
            }
        });

    }

    private void doWebViewPrint(String manifesto, String box) {
        // Create a WebView object specifically for printing
        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("TAG", "page finished loading " + url);
                createWebPrintJob(view);
                mWebView = null;
            }
        });

        // Generate an HTML document on the fly:
        String htmlDocument = "<html><body style=\"border-style:solid;\"><center>   <img style=\"margin-top: 50px\" border=\"0\" width=\"392\" height=\"96\" src=\"DSOlogo.png\" alt=\"nothing\"/> " +
                "<h1 style=\"font-size:105px;margin-top:60px;margin-bottom:1px\"> MANIFESTO </h1>" +
                "<p style=\"font-size:80px;margin-top:1px;margin-bottom:1px\">" + manifesto + "</p>" +
                "<h1 style=\"font-size:180px;margin-top:20px;margin-bottom:1px\"> BOX </h1>" +
                "<p style=\"font-size:180px;margin-top:1px;margin-bottom:1px\"> "+ box + "</p>" +
                "</center> " +
                "</body></html>";
        webView.loadDataWithBaseURL("file:///android_asset/images/", htmlDocument, "text/HTML", "UTF-8", null);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;
    }

    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        String jobName = getString(R.string.app_name) + " Document";

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        // Save the job object for later status checking
        //printJobs.add(printJob);
    }

}