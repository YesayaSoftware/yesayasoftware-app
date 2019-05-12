package com.yesayasoftware.learning.ui.posts.list

import com.yesayasoftware.learning.data.repository.YesayaSoftwareRepository
import com.yesayasoftware.learning.internal.lazyDeferred
import com.yesayasoftware.learning.ui.base.YesayaSoftwareViewModel

class PostViewModel(
    private val yesayaSoftwareRepository: YesayaSoftwareRepository
) : YesayaSoftwareViewModel(yesayaSoftwareRepository) {
    val postEntries by lazyDeferred {
        yesayaSoftwareRepository.getPostList()
    }
}
