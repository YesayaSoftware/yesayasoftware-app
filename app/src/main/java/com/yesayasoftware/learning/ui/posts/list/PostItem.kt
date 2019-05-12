package com.yesayasoftware.learning.ui.posts.list

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import com.yesayasoftware.learning.R
import com.yesayasoftware.learning.data.db.converter.ConvertPostEntry
import com.yesayasoftware.learning.internal.glide.GlideApp
import kotlinx.android.synthetic.main.item_post.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class PostItem(
    private val postEntry : ConvertPostEntry
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            tv_user.text = postEntry.creatorName
            updateDate()
            updateTitle()
            updateThumbnailImage()
        }
    }

    override fun getLayout() = R.layout.item_post

    private fun ViewHolder.updateDate() {
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        tv_created_at.text = postEntry.created_at.format(dtFormatter)
    }

    private fun ViewHolder.updateTitle() {
        tv_title.text = postEntry.title
    }

    private fun ViewHolder.updateThumbnailImage() {
        GlideApp.with(this.containerView)
            .load(postEntry.thumbnailPath)
            .into(iv_thumbnail)
    }
}