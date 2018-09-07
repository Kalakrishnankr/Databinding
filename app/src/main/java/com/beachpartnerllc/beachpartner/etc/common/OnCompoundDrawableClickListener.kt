package com.beachpartnerllc.beachpartner.etc.common

interface OnCompoundDrawableClickListener {
    fun onDrawableEnd()

    companion object {
        const val DRAWABLE_LEFT = 0
        const val DRAWABLE_TOP = 1
        const val DRAWABLE_RIGHT = 2
        const val DRAWABLE_BOTTOM = 3
    }
}
