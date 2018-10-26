package com.beachpartnerllc.beachpartner.messaging

import android.text.format.DateFormat
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.beachpartnerllc.beachpartner.etc.common.bindable
import com.google.firebase.Timestamp

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 01 Oct 2018 at 5:10 PM
 */
class Message : BaseObservable() {
    @get: Bindable
    var content: String? by bindable(null, BR.content)
    var sentAt: Timestamp? = null
    var senderId: Int? = null

    fun displayTime(): String? = if (sentAt != null) {
        DateFormat.format("h:mm a", sentAt!!.toDate()).toString()
    } else null
}