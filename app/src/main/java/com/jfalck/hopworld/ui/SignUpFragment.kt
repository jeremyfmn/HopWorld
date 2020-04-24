package com.jfalck.hopworld.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.jfalck.hopworld.MainActivity
import com.jfalck.hopworld.R
import kotlinx.android.synthetic.main.fragment_signup.*


class SignUpFragment : DialogFragment() {

    companion object {
        const val TAG = "SignUpFragment"
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_signup, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        super.onCreateDialog(savedInstanceState).apply {
            window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.apply {
            setGravity(Gravity.BOTTOM)
            setLayout(MATCH_PARENT, MATCH_PARENT)
            setBackgroundDrawableResource(android.R.color.white)
            setWindowAnimations(R.style.BottomToTopAnimation)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bt_signin.setOnClickListener {
            val email = et_email.editText?.text.toString()
            val password = et_password.editText?.text.toString()
            authenticateWithEmailAndPassword(email, password)
        }
    }

    private fun authenticateWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                context?.startActivity(Intent(context, MainActivity::class.java))
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(
                    context, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // ...
        }
    }
}