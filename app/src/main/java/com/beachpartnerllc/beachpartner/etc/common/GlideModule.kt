package com.beachpartnerllc.beachpartner.etc.common

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 25 Sep 2018 at 2:29 PM
 */
@GlideModule
class GlideModule : AppGlideModule() {
	override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
		val client = OkHttpClient.Builder()
			.readTimeout(15, TimeUnit.SECONDS)
			.connectTimeout(15, TimeUnit.SECONDS)
			.build()
		
		val factory = OkHttpUrlLoader.Factory(client)
		
		glide.registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
	}
}