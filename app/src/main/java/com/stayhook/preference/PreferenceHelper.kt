package com.stayhook.preference

import android.content.SharedPreferences
/*
this class helps for storing and retrieve the small amount of data in device storage
*/
class PreferenceHelper(private val sharedPref : SharedPreferences) {
    fun put(key:String,value:String){
        sharedPref.edit().putString(key,value).apply()
    }
    fun put(key:String,value:Boolean){
        sharedPref.edit().putBoolean(key,value).apply()
    }
    fun put(key:String,value:Int){
        sharedPref.edit().putInt(key,value).commit()
    }

    operator fun get(key: String,defaultValue:String):String?{
        return sharedPref.getString(key,defaultValue)
    }

    operator fun get(key: String,defaultValue: Int):Int{
        return sharedPref.getInt(key,defaultValue)
    }

    operator fun get(key: String,defaultValue: Boolean):Boolean{
        return sharedPref.getBoolean(key,defaultValue)
    }


    fun clearSharedPref(){
        sharedPref.edit().clear().apply()
    }

}