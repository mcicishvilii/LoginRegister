package com.example.loginregister.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.loginregister.R
import com.example.loginregister.databinding.RegistersecondstepBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class RegisterStepTwoFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding: RegistersecondstepBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegistersecondstepBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener {
            registerUserName()
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flContent, RegisterStepOneFragment())
                addToBackStack(RegisterStepOneFragment::javaClass.name)
                commit()
            }
        }


    }

    private fun registerUserName(){
        auth.currentUser?.let{ user ->
            val userName = binding.etUsername.text.toString()
            val updateProfile = UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    user.updateProfile(updateProfile).await()
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(),"coming soon dear ${userName}", Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(),"not added", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}