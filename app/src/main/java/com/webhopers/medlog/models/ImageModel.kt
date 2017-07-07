package com.webhopers.medlog.models

class ImageModel() {
    var uid: String? = null
    var url: String? = null

    constructor(uid: String, url: String) : this() {
        this.uid = uid
        this.url = url
    }
}