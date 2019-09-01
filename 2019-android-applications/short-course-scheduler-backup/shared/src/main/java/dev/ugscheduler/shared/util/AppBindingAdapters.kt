package dev.ugscheduler.shared.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.pixelcan.inkpageindicator.InkPageIndicator
import dev.ugscheduler.shared.R
import dev.ugscheduler.shared.util.glide.GlideApp

@BindingAdapter("offscreenPageLimit")
fun setPageOffscreenLimit(pager: ViewPager?, limit: Int) {
    pager?.offscreenPageLimit = limit
}

@BindingAdapter("viewPager")
fun setViewPager(indicator: InkPageIndicator, pager: ViewPager?) {
    indicator.setViewPager(pager)
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    GlideApp.with(view)
        .load(url)
        .placeholder(R.drawable.ic_default_avatar_2)
        .error(R.drawable.ic_default_avatar_2)
        .into(view)
}
