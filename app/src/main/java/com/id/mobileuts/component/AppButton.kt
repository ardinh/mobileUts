package com.id.mobileuts.component

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updatePadding
import com.id.mobileuts.R
import kotlin.math.roundToInt

class AppButton(context: Context, attrs: AttributeSet) : AppCompatButton(context, attrs) {

    private val _defaultFontFamily = 2
    private val _defaultTextSize = 15f

    val FONT_FAMILIES = listOf(
        R.font.lato_regular,
        R.font.lato_italic,
        R.font.lato_bold,
        R.font.lato_bold_italic,
        R.font.lato_semi_bold,
        R.font.lato_medium
    )

    init {
        /* Default button state */
        setTextColor(context.getColor(R.color.white))
        setTextSize(TypedValue.COMPLEX_UNIT_SP, _defaultTextSize)
        isAllCaps = false
        setBackgroundResource(R.drawable.bg_button_primary)

        minHeight = 0
        minWidth = 0

        /*Attributes TextView*/
        val attrText = context.theme.obtainStyledAttributes(
            attrs, R.styleable.AppTextView, 0, 0
        )
        setAttributesText(attrText)

        /* Attributes Button */
        val attrButton = context.theme.obtainStyledAttributes(
            attrs, R.styleable.AppButton, 0, 0
        )
        setAttributesButton(attrButton)
    }

    private fun setAttributesText(typedArray: TypedArray) {
        typedArray.apply {
            try {
                val valTextStyle = getInteger(
                    R.styleable.AppTextView_text_style,
                    _defaultFontFamily
                )
                typeface = ResourcesCompat.getFont(
                    context,
                    FONT_FAMILIES[valTextStyle]
                )

                val valTextSize = getDimension(R.styleable.AppTextView_android_textSize, 0f)
                if (valTextSize > 0f) setTextSize(TypedValue.COMPLEX_UNIT_PX, valTextSize)

                val valTextColor = getColor(R.styleable.AppTextView_android_textColor, 0)
                if (valTextColor != 0) setTextColor(valTextColor)
            } finally {
                recycle()
            }
        }
    }

    private fun setAttributesButton(typedArray: TypedArray) {
        typedArray.apply {
            try {
                val valBackground = getResourceId(
                    R.styleable.AppButton_android_background, 0
                )
                if (valBackground != 0) background =
                    ContextCompat.getDrawable(context, valBackground)

                val valHorizontalPadding = getDimensionPixelSize(
                    R.styleable.AppButton_android_paddingHorizontal,
                    dipToPixel(resources, 16f)
                )
                val valVerticalPadding = getDimensionPixelSize(
                    R.styleable.AppButton_android_paddingVertical,
                    dipToPixel(resources, 16f)
                )

                updatePadding(
                    valHorizontalPadding,
                    valVerticalPadding,
                    valHorizontalPadding,
                    valVerticalPadding
                )

            } finally {
                recycle()
            }
        }
    }

    fun dipToPixel(resources: Resources, value: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            resources.displayMetrics
        ).roundToInt()
    }

}