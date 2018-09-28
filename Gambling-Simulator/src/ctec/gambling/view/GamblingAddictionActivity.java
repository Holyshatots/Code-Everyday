package ctec.gambling.view;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Class that displays a webview with a site about gambling addiction
 * 
 * @author Mitch
 *
 */
public class GamblingAddictionActivity extends Activity
{

	private WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		webview = new WebView(getApplicationContext());
		setContentView(webview);

		webview.loadUrl("http://www.helpguide.org/articles/addiction/gambling-addiction-and-problem-gambling.htm");
	}
}
