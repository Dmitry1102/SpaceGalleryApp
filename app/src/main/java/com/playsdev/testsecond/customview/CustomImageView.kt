package com.playsdev.testsecond.customview

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.abs
import kotlin.math.roundToInt

class CustomImageView : AppCompatImageView {
    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            mode = ZOOM
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactor = detector.scaleFactor
            val newScale = saveScale * scaleFactor
            if (newScale < maxScale && newScale > minScale) {
                saveScale = newScale
                val width = width.toFloat()
                val height = height.toFloat()
                right = originalBitmapWidth * saveScale - width
                bottom = originalBitmapHeight * saveScale - height
                val scaledBitmapWidth = originalBitmapWidth * saveScale
                val scaledBitmapHeight = originalBitmapHeight * saveScale
                if (scaledBitmapWidth <= width || scaledBitmapHeight <= height) {
                    newMatrix.postScale(scaleFactor, scaleFactor, width / 2, height / 2)
                } else {
                    newMatrix.postScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
                }
            }
            return true
        }
    }

    private var mode = NONE
    private val newMatrix = Matrix()
    private val last = PointF()
    private val start = PointF()
    private val minScale = 0.5f
    private var maxScale = 4f
    private lateinit var m: FloatArray
    private var redundantXSpace = 0f
    private var redundantYSpace = 0f
    private var saveScale = 1f
    private var right = 0f
    private var bottom = 0f
    private var originalBitmapWidth = 0f
    private var originalBitmapHeight = 0f
    private var mScaleDetector: ScaleGestureDetector? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        super.setClickable(true)
        mScaleDetector = ScaleGestureDetector(context, ScaleListener())
        m = FloatArray(9)
        imageMatrix = newMatrix
        scaleType = ScaleType.MATRIX
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val bmHeight = bmHeight
        val bmWidth = bmWidth
        val width = measuredWidth.toFloat()
        val height = measuredHeight.toFloat()
        //Fit to screen.
        val scale = if (width > height) height / bmHeight else width / bmWidth
        newMatrix.setScale(scale, scale)
        saveScale = 1f
        originalBitmapWidth = scale * bmWidth
        originalBitmapHeight = scale * bmHeight
        redundantYSpace = height - originalBitmapHeight
        redundantXSpace = width - originalBitmapWidth
        newMatrix.postTranslate(redundantXSpace / 2, redundantYSpace / 2)
        imageMatrix = newMatrix
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mScaleDetector!!.onTouchEvent(event)
        newMatrix.getValues(m)
        val x = m[Matrix.MTRANS_X]
        val y = m[Matrix.MTRANS_Y]
        val curr = PointF(event.x, event.y)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                last[event.x] = event.y
                start.set(last)
                mode = DRAG
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                last[event.x] = event.y
                start.set(last)
                mode = ZOOM
            }
            MotionEvent.ACTION_MOVE ->                 //if the mode is ZOOM or
                //if the mode is DRAG and already zoomed
                if (mode == ZOOM || mode == DRAG && saveScale > minScale) {
                    var deltaX = curr.x - last.x
                    var deltaY = curr.y - last.y
                    val scaleWidth = (originalBitmapWidth * saveScale).roundToInt()
                        .toFloat()
                    val scaleHeight = (originalBitmapHeight * saveScale).roundToInt()
                        .toFloat()
                    var limitX = false
                    var limitY = false

                    if (scaleWidth < width && scaleHeight < height) {
                    } else if (scaleWidth < width) {
                        deltaX = 0f
                        limitY = true
                    } else if (scaleHeight < height) {
                        deltaY = 0f
                        limitX = true
                    } else {
                        limitX = true
                        limitY = true
                    }
                    if (limitY) {
                        if (y + deltaY > 0) {
                            deltaY = -y
                        } else if (y + deltaY < -bottom) {
                            deltaY = -(y + bottom)
                        }
                    }
                    if (limitX) {
                        if (x + deltaX > 0) {
                            deltaX = -x
                        } else if (x + deltaX < -right) {
                            deltaX = -(x + right)
                        }
                    }

                    newMatrix.postTranslate(deltaX, deltaY)
                    last[curr.x] = curr.y
                }
            MotionEvent.ACTION_UP -> {
                mode = NONE
                val xDiff = abs(curr.x - start.x).toInt()
                val yDiff = abs(curr.y - start.y).toInt()
                if (xDiff < CLICK && yDiff < CLICK) performClick()
            }
            MotionEvent.ACTION_POINTER_UP -> mode = NONE
        }
        imageMatrix = newMatrix
        invalidate()
        return true
    }

    private val bmWidth: Int
        get() {
            val drawable = drawable
            return drawable?.intrinsicWidth ?: 0
        }
    private val bmHeight: Int
        get() {
            val drawable = drawable
            return drawable?.intrinsicHeight ?: 0
        }

    companion object {
        const val NONE = 0
        const val DRAG = 1
        const val ZOOM = 2
        const val CLICK = 3
    }

}