package com.example.loginregister.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.loginregister.DashboardActivity
import com.example.loginregister.R
import com.example.loginregister.databinding.LoginActivityBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginFragment: Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: LoginActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginActivityBinding.inflate(inflater,container,false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        checkLoggedInState()

        binding.btnLogin.setOnClickListener {
            loginWithUser()
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flContent, DashboardActivity())
                addToBackStack(DashboardActivity::javaClass.name)
                commit()
            }
        }


    }

    private fun loginWithUser(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email,password).await()
                    withContext(Dispatchers.Main){
                        checkLoggedInState()
                        Toast.makeText(requireContext(),"logged in!", Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(),"wrong", Toast.LENGTH_SHORT).show()
                    }
                }
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}