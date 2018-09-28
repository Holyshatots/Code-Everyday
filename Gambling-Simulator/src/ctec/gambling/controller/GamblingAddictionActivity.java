package ctec.gambling.controller;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class GamblingAddictionActivity extends Activity {

	private WebView webview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		webview = new WebView(getApplicationContext());
		setContentView(webview);
		
		webview.loadUrl("http://www.helpguide.org/articles/addiction/gambling-addiction-and-problem-gambling.htm");
	}
}
