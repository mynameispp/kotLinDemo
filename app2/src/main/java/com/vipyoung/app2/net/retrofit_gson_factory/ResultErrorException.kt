package com.vipyoung.app2.net.retrofit_gson_factory

import java.io.IOException

/**
 * 创建者： feifan.pi 在 2017/3/22.
 */

class ResultErrorException : IOException {

    constructor() : super() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}

    constructor(cause: Throwable) : super(cause) {}
}
