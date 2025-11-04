//package com.example.xmlproject.ui
//
//import android.os.Bundle
//import android.text.Editable
//import android.text.InputType
//import android.text.TextWatcher
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.xmlproject.R
//import com.example.xmlproject.data.model.response.DialCode
//import com.example.xmlproject.databinding.FragmentSigninBinding
//import com.example.xmlproject.ui.viewmodel.CountryViewModel
//import com.example.xmlproject.ui.viewmodel.SignInViewModel
//import com.hbb20.CountryCodePicker
//
//class SignInFragment : Fragment() {
//
//    private var _binding: FragmentSigninBinding? = null
//    private val binding get() = _binding!!
//
//    private val signInViewModel: SignInViewModel by lazy {
//        ViewModelProvider(this)[SignInViewModel::class.java]
//    }
//
//    private val countryViewModel: CountryViewModel by lazy {
//        ViewModelProvider(this)[CountryViewModel::class.java]
//    }
//
//    private var dialCodeList: List<DialCode> = emptyList()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentSigninBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        setupPasswordVisibility()
//        setupCountryCodeDropdown()
//        setupNavigation()
////        setupRealTimeValidation()
//        observeViewModel()
//    }
//
//    private fun setupCountryCodeDropdown() {
//        val ccp: CountryCodePicker = binding.ccp
//
//        countryViewModel.countryListLiveData.observe(viewLifecycleOwner) { countryList: List<DialCode> ->
//            dialCodeList = countryList
//
//            // Set first API country code as default
//            val firstCode = countryList.firstOrNull()?.diaCode?.replace("+", "")?.toIntOrNull()
//            firstCode?.let { ccp.setCountryForPhoneCode(it) }
//        }
//
//        countryViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
//            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
//        }
//
//        // Fetch country codes when dropdown is clicked
//        binding.ccp.setOnClickListener {
//            countryViewModel.fetchCountryList()
//        }
//
//        // Selected country listener
//        ccp.setOnCountryChangeListener {
//            val selectedCode = ccp.selectedCountryCodeWithPlus
//            val selectedName = ccp.selectedCountryName
//            Toast.makeText(requireContext(), "Selected: $selectedName ($selectedCode)", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun setupPasswordVisibility() {
//        binding.ivVisiblePassword.setOnClickListener {
//            binding.edtPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//            binding.ivVisiblePassword.visibility = View.GONE
//            binding.ivHidePassword.visibility = View.VISIBLE
//            binding.edtPassword.setSelection(binding.edtPassword.text?.length ?: 0)
//        }
//
//        binding.ivHidePassword.setOnClickListener {
//            binding.edtPassword.inputType =
//                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//            binding.ivHidePassword.visibility = View.GONE
//            binding.ivVisiblePassword.visibility = View.VISIBLE
//            binding.edtPassword.setSelection(binding.edtPassword.text?.length ?: 0)
//        }
//    }
//
//    private fun setupNavigation() {
//        binding.btnSignIn.setOnClickListener {
//            val mobile = binding.etMobile.text.toString().trim()
//            val password = binding.edtPassword.text.toString().trim()
//            val diaCode = binding.ccp.selectedCountryCodeWithPlus
//
//            if (!isValidPhone(mobile)) {
//                Toast.makeText(requireContext(), "Enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            if (!isValidPassword(password)) {
//                Toast.makeText(
//                    requireContext(),
//                    "Password must contain at least 8 characters, including uppercase, lowercase, number & special character",
//                    Toast.LENGTH_LONG
//                ).show()
//                return@setOnClickListener
//            }
//
//            signInViewModel.loginUser(diaCode, mobile, password)
//        }
//
//        binding.tvForgotPassword.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, CreatePasswordFragment())
//                .addToBackStack(null)
//                .commit()
//        }
//
//        binding.btnSignUp.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, SignUpFragment())
//                .addToBackStack(null)
//                .commit()
//        }
//
//        binding.tvSignUp.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, SignUpFragment())
//                .addToBackStack(null)
//                .commit()
//        }
//    }
//
//    private fun setupRealTimeValidation() {
//        binding.etMobile.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val input = s.toString()
//                if (!input.matches(Regex("^[0-9]*$"))) {
//                    binding.etMobile.setText(input.replace(Regex("[^0-9]"), ""))
//                    binding.etMobile.setSelection(binding.etMobile.text?.length ?: 0)
//                    Toast.makeText(requireContext(), "Only numbers allowed", Toast.LENGTH_SHORT).show()
//                    return
//                }
//                if (input.length > 10) {
//                    binding.etMobile.setText(input.substring(0, 10))
//                    binding.etMobile.setSelection(10)
//                    Toast.makeText(requireContext(), "Enter valid 10-digit number", Toast.LENGTH_SHORT).show()
//                }
//            }
//            override fun afterTextChanged(s: Editable?) {}
//        })
//    }
//
//    private fun isValidPhone(phone: String) = phone.matches(Regex("^[0-9]{10}$"))
//
//    private fun isValidPassword(password: String): Boolean {
//        val passwordPattern =
//            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
//        return password.matches(passwordPattern)
//    }
//
//    private fun observeViewModel() {
//        signInViewModel.loginState.observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is SignInViewModel.LoginState.Idle -> {}
//                is SignInViewModel.LoginState.Loading -> {
//                    binding.btnSignIn.isEnabled = false
//                    binding.btnSignIn.text = "Signing In..."
//                }
//                is SignInViewModel.LoginState.Success -> {
//                    binding.btnSignIn.isEnabled = true
//                    binding.btnSignIn.text = "Sign In"
//                    Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()
//                    parentFragmentManager.beginTransaction()
//                        .replace(R.id.fragmentContainer, HomePageFragment())
//                        .addToBackStack(null)
//                        .commit()
//                }
//                is SignInViewModel.LoginState.Error -> {
//                    binding.btnSignIn.isEnabled = true
//                    binding.btnSignIn.text = "Sign In"
//                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
//
//package com.example.xmlproject.ui
//
//import android.os.Bundle
//import android.text.InputType
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import android.widget.Toast
//import androidx.activity.addCallback
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.xmlproject.R
//import com.example.xmlproject.data.model.response.DialCode
//import com.example.xmlproject.databinding.FragmentSigninBinding
//import com.example.xmlproject.ui.viewmodel.CountryViewModel
//import com.example.xmlproject.ui.viewmodel.SignInViewModel
//
//class SignInFragment : Fragment() {
//
//    private var _binding: FragmentSigninBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var signInViewModel: SignInViewModel
//    private lateinit var countryViewModel: CountryViewModel
//    private var dialCodeList: List<DialCode> = emptyList()
//    private var selectedCountry: DialCode? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentSigninBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        signInViewModel = ViewModelProvider(this)[SignInViewModel::class.java]
//        countryViewModel = ViewModelProvider(this)[CountryViewModel::class.java]
//
//        setupPasswordVisibility()
//        setupCountryDropdown()
//        setupNavigation()
//        observeViewModels()
//
//        countryViewModel.fetchCountryList()
//
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            requireActivity().finish()
//        }
//    }
//
//    private fun setupCountryDropdown() {
//        val countryDropdown = binding.actvCountry
//
//        countryViewModel.countryListLiveData.observe(viewLifecycleOwner) { list ->
//            dialCodeList = list
//
//            val countryCodes = list.map { "${it.countryCode} (${it.diaCode})" }
//            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, countryCodes)
//            countryDropdown.setAdapter(adapter)
//
//            // Set default to India
//            val defaultIndex = dialCodeList.indexOfFirst { it.countryCode.equals("IN", true) }
//            if (defaultIndex != -1) {
//                selectedCountry = dialCodeList[defaultIndex]
//                countryDropdown.setText("${selectedCountry?.countryCode} (${selectedCountry?.diaCode})", false)
//            }
//
//            countryDropdown.setOnItemClickListener { _, _, position, _ ->
//                selectedCountry = dialCodeList[position]
//                Toast.makeText(
//                    requireContext(),
//                    "Selected: ${selectedCountry?.countryCode} ${selectedCountry?.diaCode}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//
//        countryViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
//            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
//        }
//
//        // Show dropdown on click
//        countryDropdown.setOnClickListener {
//            if (dialCodeList.isEmpty()) {
//                countryViewModel.fetchCountryList()
//            } else {
//                countryDropdown.showDropDown()
//            }
//        }
//    }
//
//    private fun setupPasswordVisibility() {
//        binding.ivVisiblePassword.setOnClickListener {
//            binding.edtPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//            binding.ivVisiblePassword.visibility = View.GONE
//            binding.ivHidePassword.visibility = View.VISIBLE
//            binding.edtPassword.setSelection(binding.edtPassword.text?.length ?: 0)
//        }
//
//        binding.ivHidePassword.setOnClickListener {
//            binding.edtPassword.inputType =
//                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//            binding.ivHidePassword.visibility = View.GONE
//            binding.ivVisiblePassword.visibility = View.VISIBLE
//            binding.edtPassword.setSelection(binding.edtPassword.text?.length ?: 0)
//        }
//    }
//
//    private fun setupNavigation() {
//        binding.btnSignIn.setOnClickListener {
//            val mobile = binding.etMobile.text.toString().trim()
//            val password = binding.edtPassword.text.toString().trim()
//            val diaCode = selectedCountry?.diaCode ?: ""
//
//            if (!isValidPhone(mobile)) {
//                Toast.makeText(requireContext(), "Enter valid phone number", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            if (!isValidPassword(password)) {
//                Toast.makeText(requireContext(), "Invalid password format", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            if (diaCode.isEmpty()) {
//                Toast.makeText(requireContext(), "Please select country", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            signInViewModel.loginUser(diaCode, mobile, password)
//        }
//    }
//
//    private fun observeViewModels() {
//        signInViewModel.loginState.observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is SignInViewModel.LoginState.Idle -> {}
//                is SignInViewModel.LoginState.Loading -> {
//                    binding.btnSignIn.isEnabled = false
//                    binding.btnSignIn.text = "Signing In..."
//                }
//                is SignInViewModel.LoginState.Success -> {
//                    binding.btnSignIn.isEnabled = true
//                    binding.btnSignIn.text = "Sign In"
//                    Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()
//                    parentFragmentManager.beginTransaction()
//                        .replace(R.id.fragmentContainer, HomePageFragment())
//                        .addToBackStack(null)
//                        .commit()
//
//                    (activity as MainActivity).loadFragment(HomePageFragment(), showBottomNav = true)
//
//                }
//                is SignInViewModel.LoginState.Error -> {
//                    binding.btnSignIn.isEnabled = true
//                    binding.btnSignIn.text = "Sign In"
//                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    private fun isValidPhone(phone: String) = phone.matches(Regex("^[0-9]{10}$"))
//
//    private fun isValidPassword(password: String): Boolean {
//        val pattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
//        return password.matches(pattern)
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
//


package com.example.xmlproject.ui

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.xmlproject.R
import com.example.xmlproject.data.model.response.DialCode
import com.example.xmlproject.databinding.FragmentSigninBinding
import com.example.xmlproject.ui.viewmodel.CountryViewModel
import com.example.xmlproject.ui.viewmodel.SignInViewModel

class SignInFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

    private lateinit var signInViewModel: SignInViewModel
    private lateinit var countryViewModel: CountryViewModel
    private var dialCodeList: List<DialCode> = emptyList()
    private var selectedCountry: DialCode? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInViewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        countryViewModel = ViewModelProvider(this)[CountryViewModel::class.java]

        setupPasswordVisibility()
        setupCountryDropdown()
        setupNavigation()
        observeViewModels()

        countryViewModel.fetchCountryList()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    private fun setupCountryDropdown() {
        val countryDropdown = binding.actvCountry

        countryViewModel.countryListLiveData.observe(viewLifecycleOwner) { list ->
            dialCodeList = list

            val countryCodes = list.map { "${it.countryCode} (${it.diaCode})" }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, countryCodes)
            countryDropdown.setAdapter(adapter)

            // Set default to India
            val defaultIndex = dialCodeList.indexOfFirst { it.countryCode.equals("IN", true) }
            if (defaultIndex != -1) {
                selectedCountry = dialCodeList[defaultIndex]
                countryDropdown.setText("${selectedCountry?.countryCode} (${selectedCountry?.diaCode})", false)
            }

            countryDropdown.setOnItemClickListener { _, _, position, _ ->
                selectedCountry = dialCodeList[position]
                Toast.makeText(
                    requireContext(),
                    "Selected: ${selectedCountry?.countryCode} ${selectedCountry?.diaCode}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        countryViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        // Show dropdown on click
        countryDropdown.setOnClickListener {
            if (dialCodeList.isEmpty()) {
                countryViewModel.fetchCountryList()
            } else {
                countryDropdown.showDropDown()
            }
        }
    }

    private fun setupPasswordVisibility() {
        binding.ivVisiblePassword.setOnClickListener {
            binding.edtPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.ivVisiblePassword.visibility = View.GONE
            binding.ivHidePassword.visibility = View.VISIBLE
            binding.edtPassword.setSelection(binding.edtPassword.text?.length ?: 0)
        }

        binding.ivHidePassword.setOnClickListener {
            binding.edtPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.ivHidePassword.visibility = View.GONE
            binding.ivVisiblePassword.visibility = View.VISIBLE
            binding.edtPassword.setSelection(binding.edtPassword.text?.length ?: 0)
        }
    }

    private fun setupNavigation() {
        binding.btnSignIn.setOnClickListener {
            val mobile = binding.etMobile.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val diaCode = selectedCountry?.diaCode ?: ""

            if (!isValidPhone(mobile)) {
                Toast.makeText(requireContext(), "Enter valid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPassword(password)) {
                Toast.makeText(requireContext(), "Invalid password format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (diaCode.isEmpty()) {
                Toast.makeText(requireContext(), "Please select country", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            signInViewModel.loginUser(diaCode, mobile, password)
        }

        binding.tvSignUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.tvForgotPassword.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, CreatePasswordFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnSignUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun observeViewModels() {
        signInViewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SignInViewModel.LoginState.Idle -> {}
                is SignInViewModel.LoginState.Loading -> {
                    binding.btnSignIn.isEnabled = false
                    binding.btnSignIn.text = "Signing In..."
                }
                is SignInViewModel.LoginState.Success -> {
                    binding.btnSignIn.isEnabled = true
                    binding.btnSignIn.text = "Sign In"
                    Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()

                    val token = state.response.data?.accessToken ?: ""

                    if (token.isNotEmpty()) {
                        (activity as? MainActivity)?.saveAuthToken(token)
                        Toast.makeText(requireContext(), "Access token saved successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Access token not found in response", Toast.LENGTH_SHORT).show()
                    }

                    (activity as MainActivity).loadFragment(HomePageFragment(), showBottomNav = true)
                }


                is SignInViewModel.LoginState.Error -> {
                    binding.btnSignIn.isEnabled = true
                    binding.btnSignIn.text = "Sign In"
                    val message = state.message
                    if (message.contains("Invalid credentials", ignoreCase = true)) {
                        Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun isValidPhone(phone: String) = phone.matches(Regex("^[0-9]{10}$"))

    private fun isValidPassword(password: String): Boolean {
        val pattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
        return password.matches(pattern)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
