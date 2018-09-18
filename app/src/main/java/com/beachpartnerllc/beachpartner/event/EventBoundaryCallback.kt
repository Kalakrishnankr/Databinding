/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beachpartnerllc.beachpartner.event

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.beachpartnerllc.beachpartner.etc.common.PagingRequestHelper
import com.beachpartnerllc.beachpartner.etc.common.createStatusLiveData
import com.beachpartnerllc.beachpartner.etc.model.rest.ApiService
import com.beachpartnerllc.beachpartner.etc.model.rest.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.Executor

/**
 * This boundary callback gets notified when user reaches to the edges of the list such that the
 * database cannot provide any more data.
 * <p>
 * The boundary callback might be called multiple times for the same direction so it does its own
 * rate limiting using the PagingRequestHelper class.
 */
class EventBoundaryCallback(
        private val eventDate: Date,
        private val api: ApiService,
        private val handleResponse: (Date, Resource<List<Event>>?) -> Unit,
        private val ioExecutor: Executor,
        private val networkPageSize: Int)
    : PagedList.BoundaryCallback<Event>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData<Event>()

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            api.getEvent(
                    date = eventDate,
                    limit = networkPageSize)
                    .enqueue(createWebserviceCallback(it))
        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: Event) {
        /*helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            api.getEvent(eventDate, networkPageSize)
                    .enqueue(createWebserviceCallback(it))
        }*/
    }

    /**
     * every time it gets new items, boundary callback simply inserts them into the database and
     * paging library takes care of refreshing the list if necessary.
     */
    private fun insertItemsIntoDb(response: Response<Resource<List<Event>>>, it: PagingRequestHelper.Request.Callback) {
        ioExecutor.execute {
            handleResponse(eventDate, response.body())
            it.recordSuccess()
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: Event) {
        // ignored, since we only ever append to what's in the DB
    }

    private fun createWebserviceCallback(it: PagingRequestHelper.Request.Callback): Callback<Resource<List<Event>>> {
        return object : Callback<Resource<List<Event>>> {
            override fun onFailure(call: Call<Resource<List<Event>>>, t: Throwable) {
                it.recordFailure(t)
            }

            override fun onResponse(call: Call<Resource<List<Event>>>, response: Response<Resource<List<Event>>>) {
                insertItemsIntoDb(response, it)
            }
        }
    }
}