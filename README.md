# WebViewJSinject

This code will execute JavaScript function f() immediately when loadUrl() is called. Calling loadUrl() repeatedly doesn't reload WebView's content, as you might instinctively suggest from a method name.
In situations where you want to execute JS code when the page is fully loaded, which is almost all of the time, you end up listening to the onPageFinished() callback:
https://stackoverflow.com/questions/65503802/cant-navigate-to-other-fragment-from-webview
