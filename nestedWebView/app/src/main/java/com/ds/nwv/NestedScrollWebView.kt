package com.ds.nwv

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.webkit.WebView
import android.widget.OverScroller
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat
import kotlin.math.abs

class NestedScrollWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.webViewStyle
) : WebView(context, attrs, defStyleAttr), NestedScrollingChild3 {
    private val scrollConsumed = IntArray(2)
    private val scrollOffset = IntArray(2)
    private var lastMotionY = 0
    private var velocityTracker: VelocityTracker? = null
    private val minimumVelocity: Int
    private val maximumVelocity: Int
    private val scroller: OverScroller
    private var lastScrollerY = 0
    private val childHelper: NestedScrollingChildHelper = NestedScrollingChildHelper(this)

    init {
        isNestedScrollingEnabled = true
        scroller = OverScroller(getContext())
        val configuration: ViewConfiguration = ViewConfiguration.get(getContext())
        minimumVelocity = configuration.scaledMinimumFlingVelocity
        maximumVelocity = configuration.scaledMaximumFlingVelocity
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray
    ) {
        childHelper.dispatchNestedScroll(
            dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
            offsetInWindow, type, consumed
        )
    }

    private fun initVelocityTrackerIfNotExists() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
    }

    private fun recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker!!.recycle()
            velocityTracker = null
        }
    }

    private fun fling(velocityY: Int) {
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_NON_TOUCH)
        scroller.fling(
            scrollX, scrollY,
            0, velocityY,
            0, 0, Int.MIN_VALUE, Int.MAX_VALUE,
            0, 0
        )
        lastScrollerY = scrollY
        ViewCompat.postInvalidateOnAnimation(this)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller.computeScrollOffset()) {
            val y = scroller.currY
            val dy = y - lastScrollerY
            if (dy != 0) {
                val scrollY = scrollY
                var dyUnConsumed = 0
                var consumedY = dy
                if (scrollY == 0) {
                    dyUnConsumed = dy
                    consumedY = 0
                } else if (scrollY + dy < 0) {
                    dyUnConsumed = dy + scrollY
                    consumedY = -scrollY
                }
                if (!dispatchNestedScroll(
                        0, consumedY, 0, dyUnConsumed, null,
                        ViewCompat.TYPE_NON_TOUCH
                    )
                ) {
                }
            }

            lastScrollerY = y
            ViewCompat.postInvalidateOnAnimation(this)
        } else {
            if (hasNestedScrollingParent(ViewCompat.TYPE_NON_TOUCH)) {
                stopNestedScroll(ViewCompat.TYPE_NON_TOUCH)
            }
            lastScrollerY = 0
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        initVelocityTrackerIfNotExists()
        val vtev = MotionEvent.obtain(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastMotionY = event.rawY.toInt()
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
                velocityTracker!!.addMovement(vtev)
                scroller.computeScrollOffset()
                if (!scroller.isFinished) {
                    scroller.abortAnimation()
                }
            }
            MotionEvent.ACTION_UP -> {
                val velocityTracker = this.velocityTracker
                velocityTracker!!.computeCurrentVelocity(1000, maximumVelocity.toFloat())
                val initialVelocity = velocityTracker.yVelocity.toInt()
                if (abs(initialVelocity) > minimumVelocity) {
                    fling(-initialVelocity)
                }
                stopNestedScroll()
                recycleVelocityTracker()
            }
            MotionEvent.ACTION_CANCEL -> {
                stopNestedScroll()
                recycleVelocityTracker()
            }
            MotionEvent.ACTION_MOVE -> {
                val y = event.rawY.toInt()
                val deltaY = lastMotionY - y
                if (dispatchNestedPreScroll(0, deltaY, scrollConsumed, scrollOffset)) {
                    vtev.offsetLocation(0f, scrollConsumed[1].toFloat())
                }
                lastMotionY = y
                val scrollY = scrollY
                var dyUnconsumed = 0
                if (scrollY == 0) {
                    dyUnconsumed = deltaY
                } else if (scrollY + deltaY < 0) {
                    dyUnconsumed = deltaY + scrollY
                    vtev.offsetLocation(0f, -dyUnconsumed.toFloat())
                }
                velocityTracker!!.addMovement(vtev)
                val result = super.onTouchEvent(vtev)
                if (dispatchNestedScroll(
                        0,
                        deltaY - dyUnconsumed,
                        0,
                        dyUnconsumed,
                        scrollOffset
                    )
                ) {
                }
                return result
            }
            else -> {
            }
        }
        return super.onTouchEvent(vtev)
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        childHelper.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return childHelper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return childHelper.startNestedScroll(axes)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return childHelper.startNestedScroll(axes, type)
    }

    override fun stopNestedScroll() {
        childHelper.stopNestedScroll()
    }

    override fun stopNestedScroll(type: Int) {
        childHelper.stopNestedScroll(type)
    }

    override fun hasNestedScrollingParent(): Boolean {
        return childHelper.hasNestedScrollingParent()
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return childHelper.hasNestedScrollingParent(type)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
        dyUnconsumed: Int, offsetInWindow: IntArray?
    ): Boolean {
        return childHelper.dispatchNestedScroll(
            dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
        dyUnconsumed: Int, offsetInWindow: IntArray?, type: Int
    ): Boolean {
        return childHelper.dispatchNestedScroll(
            dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
            offsetInWindow, type
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedPreScroll(
        dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return childHelper.dispatchNestedPreFling(velocityX, velocityY)
    }
}