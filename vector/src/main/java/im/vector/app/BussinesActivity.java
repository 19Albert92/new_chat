/*
 * Copyright (c) 2021 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.matrix.android.sdk.api.session.Session;
import org.matrix.android.sdk.api.session.user.model.User;

import java.util.Locale;

import im.vector.app.core.di.ActiveSessionHolder;
import im.vector.app.features.home.HomeDrawerFragment;

/*
 * В папке config.xml поменял:
 * <string name="pusher_http_url" translatable="false"> https://matrix.m.mybusines.app/_matrix/push/v1/notify</string>
 *  <string name="matrix_org_server_url" translatable="false">https://matrix.m.mybusines.app</string>
 * */


public class BussinesActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webView;
    private Button buttonClose;
    public static final String USER_AGENT = "Cron";
    public final String TAG = "user_id";
    public int id = 1;
    public String nick = "nick";
    private ActiveSessionHolder activeSessionHolder;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_bussines);
        webView = (WebView) findViewById(R.id.webMy);
        Log.d("myLog", " " + activeSessionHolder.getUserId(session));
        webView.getSettings().setJavaScriptEnabled(true);
        String lang = Locale.getDefault().getLanguage();
        buttonClose = (Button) findViewById(R.id.buttonClosed);
        webView.loadUrl("https://" + lang + ".mybusines.app//test_mode=yes&iam=Maxim&User=yes&userName=");
        webView.getSettings().setUserAgentString(USER_AGENT);
        buttonClose.setOnClickListener(this);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alerter_slide_in_from_left);
        buttonClose.startAnimation(anim);
        WebViewClient webViewClient = new WebViewClient() {
            @SuppressWarnings("deprecation")

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @TargetApi(Build.VERSION_CODES.N)

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        };
        webView.setWebViewClient(webViewClient);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alerter_slide_out_to_right);
        buttonClose.startAnimation(anim2);
        finish();
    }
}