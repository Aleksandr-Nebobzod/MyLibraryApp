package org.nebobrod.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);

		Intent intent = getIntent();
		if(null!=intent){
			String url = intent.getStringExtra("url");
			webView = findViewById(R.id.webView);
//			webView.scrollTo(0,100);
			// не понятно )) webView.setY(100);
//		 	webView.getSettings().getJavaScriptEnabled();
//		 	webView.setWebViewClient(new WebViewClient());
//			не работает в окне (ну и не сильно нужно пока что);
			webView.loadUrl(url);
		}

		 webView = findViewById(R.id.webView);
		 webView.loadUrl("https://vk.com/public216345855");
//		 webView.getSettings().getJavaScriptEnabled();
//		 webView.setWebViewClient(new WebViewClient());
//		не работает в окне (ну и не сильно нужно пока что);
	}
}