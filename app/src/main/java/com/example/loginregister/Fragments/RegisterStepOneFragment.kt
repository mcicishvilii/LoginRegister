package com.example.loginregister.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.loginregister.DashboardActivity
import com.example.loginregister.R
import com.example.loginregister.databinding.RegisterfirststepBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterStepOneFragment: Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: RegisterfirststepBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterfirststepBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.btnNext.setOnClickListener{
            registerUser()
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flContent, DashboardActivity())
                addToBackStack(DashboardActivity::javaClass.name)
                commit()
            }
        }


    }

    private fun registerUser(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                auth.createUserWithEmailAndPassword(email,password).await()
                withContext(Dispatchers.Main){

                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.flContent, RegisterStepTwoFragment())
                        addToBackStack(RegisterStepTwoFragment::javaClass.name)
                        commit()
                    }

                }
            }
        }else{
            Toast.makeText(requireContext(),"hey!",Toast.LENGTH_SHORT).show()
        }
    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}