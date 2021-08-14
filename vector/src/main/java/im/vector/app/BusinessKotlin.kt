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

package im.vector.app

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import im.vector.app.core.di.ActiveSessionHolder
import org.matrix.android.sdk.api.session.Session
import java.util.Locale


abstract class BusinessKotlin : AppCompatActivity(), View.OnClickListener {

    private var webView: WebView? = null
    private var buttonClose: Button? = null
    val USER_AGENT = "Cron"
    val TAG = "user_id"
    var nick = "nick"
    private val activeSessionHolder: ActiveSessionHolder? = null
    private val session: Session? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bussines)

        webView = findViewById<View>(R.id.webMy) as WebView?
        activeSessionHolder?.let { Log.d("myLog", it.getUserId(session!!)) } //${it.myUserId}
        webView!!.settings.javaScriptEnabled = true
        val lang = Locale.getDefault().language
        buttonClose = findViewById<View>(R.id.buttonClosed) as Button?
        webView!!.loadUrl("https://$lang.mybusines.app//test_mode=yes&iam=Maxim&User=yes&userName=")
        /*${session.myUserId}&userAndroid=${session.myUserId}     https://" + lang + ".mybusines.app/ */ ///https://ru.qaim.me/project11/templates/device.php?
        webView!!.settings.setUserAgentString(USER_AGENT)
        buttonClose!!.setOnClickListener(this)
        val anim = AnimationUtils.loadAnimation(this, R.anim.alerter_slide_in_from_left)
        buttonClose!!.startAnimation(anim)
        val webViewClient: WebViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            @TargetApi(Build.VERSION_CODES.N)
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(request.url.toString())
                return true
            }
        }
        webView!!.webViewClient = webViewClient
    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        val anim2 = AnimationUtils.loadAnimation(this, R.anim.alerter_slide_out_to_right)
        buttonClose!!.startAnimation(anim2)
        finish()
    }
}
