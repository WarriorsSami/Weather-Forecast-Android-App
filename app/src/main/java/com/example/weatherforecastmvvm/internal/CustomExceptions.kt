package com.example.weatherforecastmvvm.internal

import java.io.IOException
import java.lang.Exception

class NoInternetConnectionException: IOException()
class LocationPermissionNotGrantedException: Exception()