package com.techlads.screen_curfew

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform