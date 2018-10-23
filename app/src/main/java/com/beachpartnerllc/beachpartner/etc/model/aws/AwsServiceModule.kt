package com.beachpartnerllc.beachpartner.etc.model.aws

import android.app.Application
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Abraham Mathew <abraham.mathew@seqato.com>
 * @created on 12 Oct 2018 at 9:18 AM
 */
@Module
class AwsServiceModule {
    @Provides
    @Singleton
    fun transferUtilProvider(app: Application): TransferUtility {
        return TransferUtility.builder()
            .context(app)
            .awsConfiguration(AWSMobileClient.getInstance().configuration)
            .s3Client(AmazonS3Client(AWSMobileClient.getInstance().credentialsProvider))
            .build()
    }

    companion object {
        const val URL_BUCKET = "https://s3.amazonaws.com/beachpartner-deployments-mobilehub-1720304644/"
    }
}