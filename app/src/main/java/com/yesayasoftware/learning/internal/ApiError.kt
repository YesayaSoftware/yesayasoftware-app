package com.yesayasoftware.learning.internal

class ApiError {
    var message: String? = null
        internal set
    var errors: Map<String, List<String>>? = null
        internal set
}
