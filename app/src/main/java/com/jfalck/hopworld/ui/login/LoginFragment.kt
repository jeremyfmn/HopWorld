package com.jfalck.hopworld.ui.login

import com.jfalck.hopworld.R
import com.jfalck.hopworld.utils.LoginUtils
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment :
    LoginOrSignupFragment(R.layout.fragment_login) {

    companion object {
        const val TAG = "LoginFragment"

        fun newInstance(loginListener: LoginUtils.IOnLogin): LoginFragment {
            val fragment = LoginFragment()
            fragment.arguments = getBundle(loginListener)
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        bt_signin.setOnClickListener {
            val email = et_email.editText?.text?.toString()
            val password = et_password.editText?.text?.toString()
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                LoginUtils.loginWithEmailAndPassword(email, password, loginListener)
            }
        }
    }
}