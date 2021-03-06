package com.beachpartnerllc.beachpartner.etc.model.rest

import androidx.lifecycle.LiveData
import com.beachpartnerllc.beachpartner.event.Event
import com.beachpartnerllc.beachpartner.event.EventStatus
import com.beachpartnerllc.beachpartner.finder.Flag
import com.beachpartnerllc.beachpartner.finder.Search
import com.beachpartnerllc.beachpartner.user.auth.Auth
import com.beachpartnerllc.beachpartner.user.profile.Profile
import com.beachpartnerllc.beachpartner.user.profile.Session
import com.beachpartnerllc.beachpartner.user.state.State
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 27 Nov 2017 at 2:48 PM
 */
interface ApiService {
    @Headers(HEADER_NO_AUTH)
    @POST("authenticate")
    fun signIn(@Body auth: Auth): Call<Resource<Session>>

    @Headers(HEADER_NO_AUTH)
    @POST("user/register")
    fun register(@Body profile: Profile): Call<Resource<Any>>

    @Headers(HEADER_NO_AUTH)
    @POST("user/update")
    fun update(@Body profile: Profile?): Call<Resource<Profile>>

    @Headers(HEADER_NO_AUTH)
    @GET("states")
    fun getStates(): LiveData<ApiResponse<List<State>>>

    @GET("get_events")
    fun getEvent(date: Date, limit: Int, index: Int = 0): Call<Resource<List<Event>>>

    @GET("get_event")
    fun getEvent(eventId: Int): LiveData<ApiResponse<Event>>

    @GET("get_connections")
    fun getConnections(): LiveData<ApiResponse<List<Profile>>>

    @POST("send_invitation")
    fun sendInvitation(@Body invitees: List<Int>): Call<Resource<Any>>

    @GET("users/search")
    fun searchProfile(search: Search): Call<Resource<List<Profile>>>

    @POST("users/request-friendship/{userId}")
    fun likeUser(userId: Int): Call<Resource<Profile>>

    @POST("users/reject-friendship/{userId}")
    fun dislikeUser(userId: Int): Call<Resource<Profile>>

    @POST("users/hifi/{userId}")
    fun hiFiveUser(userId: Int): Call<Resource<Profile>>

    @POST("users/flag-user")
    fun blockUser(@Body request: HashMap<String, Any>): Call<Flag>

    @GET("users/account/{userId}")
    fun getProfile(userId: Int): Call<Resource<Profile>>

    @GET("user/search")
    fun getBlueBpProfile(): Call<Resource<List<Profile>>>

    @GET("event/get-events-for-next")
    fun getEventsForNext(time: Date): LiveData<ApiResponse<List<Event>>>

    @GET("event/get-events-by-status")
    fun getEvents(status: EventStatus): LiveData<ApiResponse<List<Event>>>

    companion object {
        const val URL_BASE = "http://10.0.2.2:5000/"
        const val NO_AUTH = "No-Auth"
        private const val HEADER_NO_AUTH = "$NO_AUTH: true"
    }
}