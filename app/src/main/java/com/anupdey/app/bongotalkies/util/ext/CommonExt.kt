package com.anupdey.app.bongotalkies.util.ext

import android.app.Activity
import androidx.core.view.isVisible
import com.anupdey.app.bongotalkies.databinding.LayoutErrorBinding
import com.anupdey.app.bongotalkies.util.network.ApiError
import java.text.SimpleDateFormat
import java.util.*

fun formatToHHMM(minute: Int): String {
    val hours = minute / 60
    val minutes = minute % 60
    return if (hours > 0) {
        String.format("%dh %02dm", hours, minutes)
    } else {
        String.format("%02dm", minutes)
    }
}

fun formatDate(input: String?, inputFormat: String = "yyyy-MM-dd", outputFormat: String = "dd MMM, yyyy"): String {
    input ?: return ""
    val sdf1 = SimpleDateFormat(inputFormat, Locale.US)
    val sdf2 = SimpleDateFormat(outputFormat, Locale.US)

    return try {
        val date = sdf1.parse(input)
        if (date != null) {
            sdf2.format(date)
        } else {
            input
        }
    } catch (e: Exception) {
        e.printStackTrace()
        input
    }
}

fun Activity.showError(binding: LayoutErrorBinding, apiError: ApiError, retry: () -> Unit) {
    binding.parent.isVisible = true
    binding.retryBtn.isEnabled = true
    binding.progressBar.isVisible = false
    binding.msg.text = apiError.message
    binding.retryBtn.setOnClickListener {
        retry.invoke()
        binding.retryBtn.isEnabled = false
        binding.progressBar.isVisible = true
    }
}

fun Activity.hideError(binding: LayoutErrorBinding) {
    binding.parent.isVisible = false
}