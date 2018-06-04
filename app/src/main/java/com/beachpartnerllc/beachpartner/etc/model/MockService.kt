package com.beachpartnerllc.beachpartner.etc.model

import retrofit2.mock.BehaviorDelegate

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 05 Dec 2017 at 7:07 PM
 */
class MockService(private val delegate: BehaviorDelegate<ApiService>)