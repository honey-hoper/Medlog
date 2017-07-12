package com.webhopers.medlog.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Playlist(@PrimaryKey
               var name: String? = null,
               var urls: RealmList<Image>? = null): RealmObject()