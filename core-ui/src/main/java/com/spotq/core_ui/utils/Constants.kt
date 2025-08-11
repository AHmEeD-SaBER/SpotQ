package com.spotq.core_ui.utils

/**
 * Constants object containing shared constant values across the app
 */
object Constants {
    
    // App Information
    const val APP_NAME = "SpotQ"
    const val APP_VERSION = "1.0.0"
    
    // Animation Durations (in milliseconds)
    const val ANIMATION_DURATION_SHORT = 200L
    const val ANIMATION_DURATION_MEDIUM = 300L
    const val ANIMATION_DURATION_LONG = 500L
    const val ANIMATION_DURATION_EXTRA_LONG = 1000L
    
    // Splash Screen Constants
    const val SPLASH_DELAY_DURATION = 2000L
    const val SPLASH_FADE_DURATION = 1000L
    const val SPLASH_LOGO_SCALE_FACTOR = 1.2f
    
    // Navigation Delays
    const val NAVIGATION_DELAY = 100L
    const val TRANSITION_DELAY = 150L
    
    // UI Constants
    const val ALPHA_DISABLED = 0.38f
    const val ALPHA_MEDIUM = 0.54f
    const val ALPHA_HIGH = 0.87f
    const val ALPHA_FULL = 1.0f
    const val ALPHA_TRANSPARENT = 0.0f
    
    // Network Constants
    const val NETWORK_TIMEOUT = 30000L // 30 seconds
    const val RETRY_DELAY = 1000L
    const val MAX_RETRY_ATTEMPTS = 3
    
    // Cache Constants
    const val CACHE_SIZE = 10 * 1024 * 1024 // 10MB
    const val CACHE_DURATION_HOURS = 24
    
    // Validation Constants
    const val MIN_PASSWORD_LENGTH = 6
    const val MAX_USERNAME_LENGTH = 50
    const val MAX_EMAIL_LENGTH = 254
    
    // Database Constants
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "spotq_database"
    
    // SharedPreferences Keys
    const val PREF_NAME = "spotq_preferences"
    const val PREF_FIRST_LAUNCH = "first_launch"
    const val PREF_USER_ID = "user_id"
    const val PREF_IS_LOGGED_IN = "is_logged_in"
    const val PREF_THEME_MODE = "theme_mode"
    const val PREF_LANGUAGE = "language"
    
    // Intent Extras
    const val EXTRA_USER_ID = "extra_user_id"
    const val EXTRA_ITEM_ID = "extra_item_id"
    const val EXTRA_TITLE = "extra_title"
    const val EXTRA_DATA = "extra_data"
    
    // Request Codes
    const val REQUEST_CODE_PERMISSIONS = 1001
    const val REQUEST_CODE_CAMERA = 1002
    const val REQUEST_CODE_GALLERY = 1003
    const val REQUEST_CODE_LOCATION = 1004
    
    // Error Codes
    const val ERROR_CODE_NETWORK = 1000
    const val ERROR_CODE_SERVER = 1001
    const val ERROR_CODE_AUTHENTICATION = 1002
    const val ERROR_CODE_VALIDATION = 1003
    const val ERROR_CODE_UNKNOWN = 9999
    
    // Date and Time Formats
    const val DATE_FORMAT_DEFAULT = "yyyy-MM-dd"
    const val DATE_FORMAT_DISPLAY = "MMM dd, yyyy"
    const val TIME_FORMAT_DEFAULT = "HH:mm:ss"
    const val TIME_FORMAT_DISPLAY = "hh:mm a"
    const val DATETIME_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    
    // File and Media
    const val MAX_FILE_SIZE_MB = 10
    const val SUPPORTED_IMAGE_FORMATS = "jpg,jpeg,png,webp"
    const val SUPPORTED_VIDEO_FORMATS = "mp4,avi,mov"
    
    // UI Limits
    const val MAX_ITEMS_PER_PAGE = 20
    const val DEFAULT_PAGE_SIZE = 10
    const val MAX_SEARCH_RESULTS = 100
    
    // Timeouts and Intervals
    const val DEBOUNCE_DELAY = 300L
    const val REFRESH_INTERVAL = 5000L
    const val LOCATION_UPDATE_INTERVAL = 10000L
    
    // Scale Factors
    const val SCALE_FACTOR_SMALL = 0.9f
    const val SCALE_FACTOR_NORMAL = 1.0f
    const val SCALE_FACTOR_LARGE = 1.1f
    
    // Default Values
    const val DEFAULT_RADIUS = 10.0
    const val DEFAULT_LATITUDE = 0.0
    const val DEFAULT_LONGITUDE = 0.0
    const val DEFAULT_ZOOM_LEVEL = 15f
}
