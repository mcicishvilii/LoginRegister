package com.example.loginregister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.loginregister.Fragments.LoginFragment
import com.example.loginregister.Fragments.RegisterStepOneFragment
import com.example.loginregister.databinding.DashboardActivityBinding
import com.google.firebase.auth.FirebaseAuth


class DashboardActivity:Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: DashboardActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DashboardActivityBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        checkLoggedInState()

        binding.btnLogout.setOnClickListener {
            checkLoggedInStateToLogOut()
        }


            binding.btnLogin.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.flContent, LoginFragment())
                    addToBackStack(LoginFragment::javaClass.name)
                    commit()
                }
            }


        binding.btnRegister.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flContent, RegisterStepOneFragment())
                addToBackStack(RegisterStepOneFragment::javaClass.name)
                commit()
            }
        }


    }

    private fun checkLoggedInState() {
        val user = auth.currentUser
        if (user == null) {
            binding.tvUsername.text = "You are not logged in"
        } else {
            binding.tvUsername.text = "logged in as ${user.displayName}"

        }
    }

    private fun checkLoggedInStateToLogOut() {
        val user = auth.currentUser
        if (user == null) {
            binding.tvUsername.text = "You are not logged in"
        } else {
            auth.signOut()
            binding.tvUsername.text = "You are not logged in"
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}