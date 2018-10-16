package com.beachpartnerllc.beachpartner.etc.model.rest.mock

import android.app.Application
import androidx.lifecycle.LiveData
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiResponse
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import com.beachpartnerllc.beachpartner.user.auth.Auth
import com.beachpartnerllc.beachpartner.user.profile.Gender
import com.beachpartnerllc.beachpartner.user.profile.Profile
import com.beachpartnerllc.beachpartner.user.profile.Session
import com.beachpartnerllc.beachpartner.user.profile.UserType
import com.beachpartnerllc.beachpartner.user.state.State
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.mock.BehaviorDelegate
import java.io.IOException
import java.nio.charset.Charset

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 05 Dec 2017 at 7:07 PM
 */
class MockService(
	private val delegate: BehaviorDelegate<ApiService>,
	private val serializer: Gson,
	private val app: Application) : ApiService {
	
	override fun getStates(): LiveData<ApiResponse<List<State>>> {
		val data = loadJSONFromAsset("get_state")
		val response: List<State> = serializer.fromJson(data, object : TypeToken<List<State>>() {}.type)
		return delegate.returningResponse(response).getStates()
	}

	override fun signIn(auth: Auth): Call<Resource<Session>> {
		val profile = Profile(1, "Sam", "Rob", 1, Gender.MALE, UserType.ATHLETE,
				"sam.rob@test.com", "9663379596", null, "1994-05-14")
		val session = Session(profile, "test_session_id")
		return delegate.returningResponse(Resource.success(session)).signIn(auth)
	}

	override fun register(profile: Profile): Call<Resource<Any>> {
		return delegate.returningResponse(Resource.success(profile)).register(profile)
	}
	
	override fun update(profile: Profile): Call<Resource<Profile>> {
		return delegate.returningResponse(Resource.success(profile)).update(profile)
	}
	
	private fun loadJSONFromAsset(filename: String): String {
		try {
			val file = app.assets.open("$filename.json")
			val size = file.available()
			val buffer = ByteArray(size)
			file.read(buffer)
			file.close()
			return String(buffer, Charset.forName("UTF-8"))
		} catch (ex: IOException) {
			IllegalStateException("Mock response file $filename.json not found in asset folder")
		}
		
		return ""
	}
}