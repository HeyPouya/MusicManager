package ir.heydarii.musicmanager.base

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.features.albumdetails.AlbumDetailsViewModel

/**
 * All fragments inherit this class.
 * Provides some functions to implement DRY
 */
open class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

    protected lateinit var binding: T
    protected lateinit var viewModel: V
    private var loading: ProgressBar? = null

    @LayoutRes
    protected open var layout = -1

    fun isBindingInitialized() = ::binding.isInitialized

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        require(layout != -1) { "Layout is not provided" }
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return binding.root
    }

    protected fun showError(message: String) {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red_400))
        val view = snackbar.view
        val tv = view.findViewById<View>(R.id.snackbar_text) as TextView

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
        else
            tv.gravity = Gravity.CENTER_HORIZONTAL

        snackbar.show()
    }

    protected fun setVM(vm: Lazy<V>) {
        viewModel = vm.value
    }

    protected fun setProgressBar(pb: ProgressBar) {
        loading = pb
    }

    protected fun isLoading(state: Boolean) {
        loading?.isVisible = state
    }
}