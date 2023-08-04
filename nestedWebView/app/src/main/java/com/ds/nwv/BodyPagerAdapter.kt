package com.ds.nwv

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class BodyPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val swipeStatusCallback: ((status: Boolean) -> Unit)
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = ITEM_SIZE

    override fun createFragment(position: Int): Fragment {
        return BodyFragment.newInstance(
            BodyFragment.Param(swipeStatusCallback)
        )
    }

    companion object {
        private const val ITEM_SIZE = 5
    }
}