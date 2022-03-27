package com.yago.gas.ui.views.shared.base

import android.content.res.Configuration
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.yago.gas.data.utils.Status
import com.yago.gas.domain.injector.Injectable

abstract class BindingActivity<T : ViewDataBinding> : AppCompatActivity(), Injectable {

    lateinit var binding: T

    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBinding = createDataBinding()
        binding = dataBinding

        binding.lifecycleOwner = this

        binding.executePendingBindings()

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateLayoutToLandscape(newConfig)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            updateLayoutPortrait(newConfig)
        }
    }

    abstract fun createDataBinding() : T

    open fun updateLayoutToLandscape(newConfig: Configuration) = Unit
    open fun updateLayoutPortrait(newConfig: Configuration) = Unit

    fun calculateLoadingInProgressByStatus(status: Status): Boolean {
        return when (status) {
            Status.SUCCESS -> false
            Status.FASTLOAD -> false
            Status.LOADING -> true
            Status.ERROR -> false
            else -> false
        }
    }

}
