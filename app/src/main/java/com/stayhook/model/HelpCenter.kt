package com.stayhook.model

data class HelpCenter(
    var question: String? = null,
    var answer: String? = null,
    var isExpanded:Boolean = false
)
