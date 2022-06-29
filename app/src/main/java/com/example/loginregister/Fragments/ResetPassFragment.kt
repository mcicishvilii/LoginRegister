package com.example.loginregister.Fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.loginregister.DashboardActivity
import com.example.loginregister.R
import com.example.loginregister.databinding.ForgotPasswordActivityBinding
import com.example.loginregister.databinding.RegisterfirststepBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ResetPassFragment: Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: ForgotPasswordActivityBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ForgotPasswordActivityBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.btnReset.setOnClickListener {
            changePassword()
        }

    }


    private fun changePassword() {
        val email = binding.etEmail.text.toString()


        if(email.isNotEmpty()){
            auth.sendPasswordResetEmail(
                binding.etEmail.text.toString()
            )
            binding.etEmail.hint = "jane@example.com"
            Toast.makeText(requireContext(),"please check your e-mail",Toast.LENGTH_SHORT).show()
        }

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}