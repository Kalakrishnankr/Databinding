package com.beachpartnerllc.beachpartner.etc.model.db

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 01 Oct 2018 at 10:43 AM
 */
@Module
class FirestoreDatabaseModule {
	@Singleton
	@Provides
	fun firestoreProvider(): FirebaseFirestore {
		val firestore = FirebaseFirestore.getInstance()
		val settings = FirebaseFirestoreSettings.Builder()
			.setTimestampsInSnapshotsEnabled(true)
			.build()
		firestore.firestoreSettings = settings
		return firestore
	}
}