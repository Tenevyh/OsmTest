package com.example.osmtest.customview

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import org.osmdroid.views.MapView

class CustomMapView(context: Context, attrs: AttributeSet?) : MapView(context, attrs) {

    private var longPressListener: ((Float, Float) -> Unit)? = null
    private var downTime: Long = 0
    private val handler = Handler()

    init {
        isLongClickable = true
    }

    fun setOnLongPressListener(listener: (Float, Float) -> Unit) {
        longPressListener = listener
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downTime = System.currentTimeMillis()
                handler.postDelayed({
                    val upTime = System.currentTimeMillis()
                    if (upTime - downTime >= 1500) {
                        longPressListener?.invoke(event.x, event.y)
                    }
                }, 1500)
            }
            MotionEvent.ACTION_UP -> {
                handler.removeCallbacksAndMessages(null)
            }
        }
        return super.onTouchEvent(event)
    }
}