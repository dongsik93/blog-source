package com.ds.nwv

import android.os.Bundle
import android.view.*
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.ds.nwv.databinding.FragmentBodyBinding
import kotlin.math.abs

class BodyFragment : Fragment(), View.OnTouchListener {

    private lateinit var binding: FragmentBodyBinding
    private var param: Param? = null
    private lateinit var scrollGestureDetector: GestureDetector

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBodyBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scrollGestureDetector = GestureDetector(binding.root.context, scrollGestureListener)
        setWebView()
    }

    private fun setWebView() {
        with(binding.nsvBody) {
            binding.nsvBody.loadUrl(URL)
            /* 웹뷰 캐시 삭제 */
            clearCache(true)
            clearHistory()

            this.settings.apply {
                javaScriptEnabled = true
                loadsImagesAutomatically = true
                domStorageEnabled = true
                defaultTextEncodingName = "utf-8"
                setGeolocationEnabled(false)
                javaScriptCanOpenWindowsAutomatically = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                useWideViewPort = true
                loadWithOverviewMode = true
                builtInZoomControls = true
                setSupportZoom(true)
                displayZoomControls = false
            }

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    println(">>>>>> !!!! ")
                    binding.nsvBody.loadUrl("javascript:setBodyContent('${TEST}');")
                }
            }

            setOnTouchListener(::onTouch)
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN ->
                param?.swipeStatusCallback?.invoke(false)
            MotionEvent.ACTION_UP ->
                param?.swipeStatusCallback?.invoke(true)
        }

        scrollGestureDetector.onTouchEvent(motionEvent)
        return false
    }

    private val scrollGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            // x 축 스크롤 민감도 조정
            if (abs(distanceX) > abs(distanceY)) {
                val status = if (e1.x > e2.x) {
                    // 우 -> 좌
                    (e1.x - SCROLL_Y_BUFFER * 10 > e2.x) && binding.nsvBody.canScrollHorizontally(1).not()
                } else {
                    // 좌 -> 우
                    (e2.x - SCROLL_Y_BUFFER * 10 > e1.x) && binding.nsvBody.canScrollHorizontally(-1).not()
                }
                param?.swipeStatusCallback?.invoke(status)
                // y 축 스크롤 민감도 조정
            } else if (abs(distanceY) > SCROLL_Y_BUFFER) {
                param?.swipeStatusCallback?.invoke(false)
            }
            return super.onScroll(e1, e2, distanceX, distanceY)
        }
    }


    data class Param(
        val swipeStatusCallback: (status: Boolean) -> Unit,
    )

    companion object {
        private const val URL = "file:///android_asset/body.html"
        private const val TEST = "<h1>HTML Ipsum Presents </h1>\n" +
                "\n" +
                "\t\t\t\t<p><strong>Pellentesque habitant morbi tristique</strong> senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. <em>Aenean ultricies mi vitae est.</em> Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, <code>commodo vitae</code>, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. <a href=\"#\">Donec non enim</a> in turpis pulvinar facilisis. Ut felis.</p>\n" +
                "\n" +
                "\t\t\t\t<h2>Header Level 2</h2>\n" +
                "\n" +
                "\t\t\t\t<ol>\n" +
                "\t\t\t\t   <li>Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</li>\n" +
                "\t\t\t\t   <li>Aliquam tincidunt mauris eu risus.</li>\n" +
                "\t\t\t\t</ol>\n" +
                "\n" +
                "\t\t\t\t<blockquote><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi at felis aliquet congue. Ut a est eget ligula molestie gravida. Curabitur massa. Donec eleifend, libero at sagittis mollis, tellus est malesuada tellus, at luctus turpis elit sit amet quam. Vivamus pretium ornare est.</p></blockquote>\n" +
                "\n" +
                "\t\t\t\t<h3>Header Level 3</h3>\n" +
                "\n" +
                "\t\t\t\t<ul>\n" +
                "\t\t\t\t   <li>Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</li>\n" +
                "\t\t\t\t   <li>Aliquam tincidunt mauris eu risus.</li>\n" +
                "\t\t\t\t</ul>\n" +
                "\n" +
                "\t\t\t\t<pre><code>\n" +
                "\t\t\t\t#header h1 a {\n" +
                "\t\t\t\t  display: block;\n" +
                "\t\t\t\t  width: 300px;\n" +
                "\t\t\t\t  height: 80px;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\t</code></pre>"

        private const val SCROLL_Y_BUFFER = 30
        fun newInstance(param: Param) = BodyFragment().apply {
            this.param = param
        }
    }
}