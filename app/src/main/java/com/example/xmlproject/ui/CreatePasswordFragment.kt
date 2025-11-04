package com.example.xmlproject.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.xmlproject.R
import com.example.xmlproject.databinding.FragmentCreatePasswordBinding

class CreatePasswordFragment : Fragment() {

    private var _binding: FragmentCreatePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivTogglePassword.setOnClickListener {
            togglePasswordVisibility(binding.etPassword, binding.ivTogglePassword)
        }
        binding.ivToggleRePassword.setOnClickListener {
            togglePasswordVisibility(binding.etRePassword, binding.ivToggleRePassword)
        }

        binding.btnCreatePassword.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TripListFragment())
                .addToBackStack(null)
                .commit()
        }
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPassword()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etPassword.addTextChangedListener(watcher)
        binding.etRePassword.addTextChangedListener(watcher)
        binding.cbTerms.setOnCheckedChangeListener { _, _ -> checkPassword() }
    }

    private fun togglePasswordVisibility(editText: EditText, icon: View) {
        if (editText.inputType == (android.text.InputType.TYPE_CLASS_TEXT or
                    android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)
        ) {
            editText.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                    android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            (icon as? android.widget.ImageView)?.setImageResource(R.drawable.ic_eye)
        } else {
            editText.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                    android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            (icon as? android.widget.ImageView)?.setImageResource(R.drawable.ic_arrow_back)
        }
        editText.setSelection(editText.text.length)
    }

    private fun checkPassword() {
        val password = binding.etPassword.text.toString()
        val rePassword = binding.etRePassword.text.toString()
        val lengthValid = password.length >= 8
        val upperValid = password.any { it.isUpperCase() }
        val lowerValid = password.any { it.isLowerCase() }
        val numberValid = password.any { it.isDigit() }
        val specialValid = password.any { !it.isLetterOrDigit() }

        binding.tvRuleLength.setTextColor(getColorForRule(lengthValid))
        binding.tvRuleUpper.setTextColor(getColorForRule(upperValid))
        binding.tvRuleLower.setTextColor(getColorForRule(lowerValid))
        binding.tvRuleNumber.setTextColor(getColorForRule(numberValid))
        binding.tvRuleSpecial.setTextColor(getColorForRule(specialValid))

        binding.btnCreatePassword.isEnabled = lengthValid && upperValid && lowerValid &&
                numberValid && specialValid && password == rePassword && binding.cbTerms.isChecked

        binding.btnCreatePassword.setBackgroundColor(
            if (binding.btnCreatePassword.isEnabled) 0xFFA7C957.toInt() else 0xFFD3E4CD.toInt()
        )
    }

    private fun getColorForRule(isValid: Boolean): Int {
        return if (isValid) 0xFF4CAF50.toInt() else 0xFFF44336.toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
