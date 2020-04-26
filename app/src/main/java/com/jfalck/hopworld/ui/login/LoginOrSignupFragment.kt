package com.jfalck.hopworld.ui.login

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.jfalck.hopworld.ui.HopWorldDialogFragment
import com.jfalck.hopworld.utils.LoginUtils

abstract class LoginOrSignupFragment(@LayoutRes layoutRes: Int) :
    HopWorldDialogFragment(layoutRes) {

    protected lateinit var loginListener: LoginUtils.IOnLogin

    companion object {
        private const val PARCELABLE_LISTENER_KEY = "listener"

        fun getBundle(loginListener: LoginUtils.IOnLogin): Bundle {
            val args = Bundle()
            args.putParcelable(PARCELABLE_LISTENER_KEY, loginListener)
            return args
        }
    }

    override fun initView() {
        (arguments?.getParcelable(PARCELABLE_LISTENER_KEY) as? LoginUtils.IOnLogin?)?.let { listener ->
            loginListener = listener
        }
    }
}