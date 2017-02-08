package com.kyald.keretaapi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by gilangpramudya on 2/5/17.
 */

public class PreferenceUtils {
        public static final String USERID = "USERID";
        public static final String EMAIL = "email";
        public static final String USERNAME = "username";
        public static final String TOKEN = "token";
        public static final String CODE = "code";
    public static final String TRAIN_ID = "TRAIN_ID";
    public static final String ROLE = "ROLE";


        private final String SP_COMMON = "go_golf_db";

        /**
         * Singleton instance
         */
        private static PreferenceUtils instance = null;

        /**
         * Private constructor to avoid instantiation outside class
         */
        protected PreferenceUtils() {
        }

        /**
         * Get the singleton instance
         *
         * @return the PreferenceUtility instance
         */
        public static PreferenceUtils getInstance() {
            if (instance == null) {
                return new PreferenceUtils();
            }
            return instance;
        }

        /**
         * Set the singleton instance
         *
         * @param instance the PreferenceUtility instance
         */
        public static synchronized void setInstance(PreferenceUtils instance) {
            PreferenceUtils.instance = instance;
        }

        /**
         * private constructor, not to be instantiated with new keyword
         *
         * @param context application context
         * @return SharedPreference object of current application
         */
        public SharedPreferences sharedPreferences(Context context) {
            return context.getSharedPreferences(SP_COMMON, Context.MODE_PRIVATE);
        }

        /**
         * Save data base on key given, and the value is on integer format
         *
         * @param context
         * @param key
         * @param value
         */
        public void saveData(Context context, String key, int value) {
            saveData(context, key, String.valueOf(value));
        }

        /**
         * Save data base on key given, and the value is on string format
         *
         * @param context
         * @param key
         * @param value
         */
        public void saveData(Context context, String key, String value) {
            sharedPreferences(context).edit().putString(key, value).commit();
        }

        public void saveData(Context context, String key, Set<String> value) {
            sharedPreferences(context).edit().putStringSet(key, value).commit();
        }

        /**
         * Save data base on key given, and the value is on string format
         *
         * @param context
         * @param key
         * @param value
         */
        public void saveData(Context context, String key, Boolean value) {
            sharedPreferences(context).edit().putBoolean(key, value).commit();
        }

        /**
         * Save data base on key given, and the value is on long format
         *
         * @param context
         * @param key
         * @param value
         */
        public void saveData(Context context, String key, long value) {
            sharedPreferences(context).edit().putLong(key, value).commit();
        }

        /**
         * get data base on key given, and the value is on integer format
         *
         * @param context
         * @param key
         * @return
         */
        public int loadDataInt(Context context, String key) {
            return Integer.parseInt(sharedPreferences(context).getString(key, "0"));
        }

        /**
         * get data base on key given, and the value is on String format
         *
         * @param context
         * @param key
         * @return
         */
        public String loadDataString(Context context, String key) {
            return sharedPreferences(context).getString(key, "");
        }

        public Set<String> loadDataStringSet(Context context, String key) {
            Set<String> stringSet = new HashSet<>();
            return sharedPreferences(context).getStringSet(key, stringSet);
        }

        /**
         * get data base on key given, and the value is on String format
         *
         * @param context
         * @param key
         * @return
         */
        public Boolean loadDataBoolean(Context context, String key) {
            return loadDataBoolean(context, key, true);
        }

        /**
         * get data base on key given, and the value is on String format
         *
         * @param context
         * @param key
         * @return
         */
        public Boolean loadDataBoolean(Context context, String key, boolean defaultValue) {
            return sharedPreferences(context).getBoolean(key, defaultValue);
        }

        /**
         * get data base on key given, and the value is on long format
         *
         * @param context
         * @param key
         * @return
         */
        public long loadDataOfLong(Context context, String key) {
            return sharedPreferences(context).getLong(key, 0);
        }

        /**
         * Save data base on key given, and the value is on string format
         *
         * @param context
         * @param key
         */
        public void delete(Context context, String key) {
            sharedPreferences(context).edit().remove(key).commit();
        }

        /**
         * Delete data base for all existing key, and based on context
         *
         * @param context
         */
        public void deleteAll(Context context) {
            sharedPreferences(context).edit().clear().commit();
        }

}