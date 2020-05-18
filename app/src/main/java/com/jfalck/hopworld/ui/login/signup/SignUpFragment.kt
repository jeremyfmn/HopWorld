package com.jfalck.hopworld.ui.login.signup

import com.google.android.material.snackbar.Snackbar
import com.jfalck.hopworld.R
import com.jfalck.hopworld.interfaces.HopWorldTextWatcher
import com.jfalck.hopworld.isEmailValid
import com.jfalck.hopworld.ui.login.LoginOrSignupFragment
import com.jfalck.hopworld.utils.LoginUtils
import kotlinx.android.synthetic.main.fragment_signup.*


class SignUpFragment :
    LoginOrSignupFragment(R.layout.fragment_signup) {

    companion object {
        const val TAG = "SignUpFragment"
        const val MIN_PASSWORD_CHARACTERS = 6

        fun newInstance(loginListener: LoginUtils.IOnLogin): SignUpFragment {
            val fragment = SignUpFragment()
            fragment.arguments = getBundle(loginListener)
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        bt_signup.setOnClickListener {
            val email = et_signup_email.editText?.text?.toString()
            val password = et_signup_password.editText?.text?.toString()
            if (email != null &&
                password != null &&
                email.isEmailValid() &&
                password.length > MIN_PASSWORD_CHARACTERS
            ) {
                LoginUtils.registerWithEmailAndPassword(email, password, loginListener)
            } else {
                Snackbar.make(
                    requireView(),
                    R.string.enter_valid_mail_and_password,
                    Snackbar.LENGTH_LONG
                ).setAction(R.string.ok) {
                    //do nothing
                }.show()
            }
        }
        addFieldsListeners()
    }

    private fun addFieldsListeners() {
        et_signup_password.editText?.addTextChangedListener(object : HopWorldTextWatcher {
            override fun onTextChanged(text: String?) {
                text?.let {
                    if ((it.isEmpty() || it.length >= MIN_PASSWORD_CHARACTERS) && et_signup_password.error != null) {
                        et_signup_password.error = null
                    } else if (it.length < MIN_PASSWORD_CHARACTERS && et_signup_password.error == null) {
                        et_signup_password.error = getString(
                            R.string.password_characters_min_error,
                            MIN_PASSWORD_CHARACTERS
                        )
                    }
                }
            }
        })

        et_signup_email.editText?.addTextChangedListener(object : HopWorldTextWatcher {
            override fun onTextChanged(text: String?) {
                text?.let {
                    if ((it.isEmpty() || it.isEmailValid()) && et_signup_email.error != null) {
                        et_signup_email.error = null
                    } else if (!it.isEmailValid() && et_signup_email.error == null) {
                        et_signup_email.error = getString(R.string.enter_valid_mail_error)
                    }
                }
            }

        })
    }
}
