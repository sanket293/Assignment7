package khushboo.sanket.assignment7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
     webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyBrowser());
        String url="https://upload.wikimedia.org/wikipedia/commons/thumb/e/e8/Montage_of_TTC_2.jpg/800px-Montage_of_TTC_2.jpg";

       webView.getSettings().setLoadWithOverviewMode(true);
       webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(url);
    }


public void onButtonClick(View view){
        startActivity(new Intent(MainActivity.this,TransitInfo.class));
}

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
