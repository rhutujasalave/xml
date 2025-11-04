//package com.example.xmlproject.ui
//
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.example.xmlproject.R
//import com.example.xmlproject.databinding.FragmentSignupBinding
//
//class SignUpFragment : Fragment() {
//
//    private var _binding: FragmentSignupBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentSignupBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.btnSignUp.setOnClickListener{
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, CreatePasswordFragment())
//                .addToBackStack(null)
//                .commit()
//        }
//
//        binding.tvSignIn.setOnClickListener{
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, SignInFragment())
//                .addToBackStack(null)
//                .commit()
//        }
//
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}




package com.example.xmlproject.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.xmlproject.R
import com.example.xmlproject.data.model.request.SignUpRequest
import com.example.xmlproject.databinding.FragmentSignupBinding
import com.example.xmlproject.ui.viewmodel.SignUpViewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            if (validateInputs()) {
                val request = SignUpRequest(
                    countryId = 1,
                    phone = binding.etMobile.text.toString().trim(),
                    email = binding.etEmail.text.toString().trim(),
                    password = "Password@123",
                    name = "Rhutuja",
                    role = "user"
                )
                viewModel.signUp(request)
            }
        }

        binding.tvSignIn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SignInFragment())
                .addToBackStack(null)
                .commit()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signUpResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                Toast.makeText(requireContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, CreatePasswordFragment())
                    .addToBackStack(null)
                    .commit()
            } else {
                val error = response.errorBody()?.string()
                Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    private fun validateInputs(): Boolean {
        val phone = binding.etMobile.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()

        if (phone.isEmpty()) {
            binding.etMobile.error = "Enter phone number"
            return false
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Enter valid email"
            return false
        }

        if (!binding.acceptTerms.isChecked) {
            Toast.makeText(requireContext(), "Please accept terms", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
