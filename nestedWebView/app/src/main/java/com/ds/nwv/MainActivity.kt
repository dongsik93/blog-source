package com.ds.nwv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import androidx.viewpager2.widget.MarginPageTransformer
import com.ds.nwv.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewPager()
    }

    private fun setViewPager() {
        with(binding.vpBody) {
            adapter = BodyPagerAdapter(this@MainActivity, ::setVpSwipeStatus)
            offscreenPageLimit = 1
            setPageTransformer(MarginPageTransformer(dpToPx(10)))
        }
    }

    /**
     * ViewPager Swipe Status 변경처리
     */
    private fun setVpSwipeStatus(status: Boolean) {
        binding.vpBody.isUserInputEnabled = status
    }

    private fun dpToPx(dp: Int): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).roundToInt()
}