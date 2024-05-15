package com.id.mobileuts

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.id.mobileuts.databinding.ActivityNewRegisterBinding
import com.id.mobileuts.services.SPManager
import com.id.mobileuts.utils.RegexUtil
import com.id.mobileuts.utils.ValidationUtil

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewRegisterBinding

    private var passwordVisible = false

    private val _spManager: SPManager by lazy {
        SPManager(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.let { toolbar ->
            toolbar.pageTitle.text = getString(R.string.registration)
            toolbar.pageTitle.setTextColor(
                ContextCompat.getColor(this, R.color.white)
            )
            toolbar.toolbarAdditionalAction.isVisible = false
        }

        binding.btnRegister.isEnabled = false
        binding.etPassword.setEndIconOnClickListener {
            setPasswordVisibility()
        }

        binding.etPassword.editText?.addTextChangedListener {
            var isValid = true

            if (binding.etEmail.editText?.text.isNullOrBlank()) isValid = false
            if (binding.etPassword.editText?.text.isNullOrEmpty()) isValid = false
            if (binding.etPasswordConfirm.editText?.text.isNullOrEmpty()) isValid = false

            binding.btnRegister.isEnabled = isFormValid() && isValid
        }

        binding.etPassword.editText?.addTextChangedListener {
            var isValid = true

            if (binding.etEmail.editText?.text.isNullOrBlank()) isValid = false
            if (binding.etPassword.editText?.text.isNullOrEmpty()) isValid = false
            if (binding.etPasswordConfirm.editText?.text.isNullOrEmpty()) isValid = false

            binding.btnRegister.isEnabled = isFormValid() && isValid
        }
        binding.etPasswordConfirm.editText?.addTextChangedListener {
            var isValid = true

            if (binding.etEmail.editText?.text.isNullOrBlank()) isValid = false
            if (binding.etPassword.editText?.text.isNullOrEmpty()) isValid = false
            if (binding.etPasswordConfirm.editText?.text.isNullOrEmpty()) isValid = false

            binding.btnRegister.isEnabled = isFormValid() && isValid
        }

        binding.btnRegister.setOnClickListener {
            actionContinueRegistration()
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun actionContinueRegistration() {
        if (!isFormValid()) return

        val email = binding.etEmail.editText?.text.toString().trim()
        val passwd = binding.etPassword.editText?.text.toString()
        _spManager.putString(SPManager.SP_USER_EMAIL, email)
        _spManager.putString(SPManager.SP_USER_PASSWORD, passwd)

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        Log.d("Registrasi Activity", "Regis: Registrasi Berhasil")
        Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT)
            .show()

    }

    private fun setPasswordVisibility() {
        if (!passwordVisible) {
            binding.etPassword.endIconDrawable = ContextCompat.getDrawable(
                this,
                R.drawable.ic_eye_close
            )
            binding.etPassword.editText?.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
        } else {
            binding.etPassword.endIconDrawable = ContextCompat.getDrawable(
                this,
                R.drawable.ic_eye_open
            )
            binding.etPassword.editText?.transformationMethod =
                PasswordTransformationMethod.getInstance()
        }

        passwordVisible = !passwordVisible

        if (binding.etPassword.editText?.isFocused == true) binding.etPassword.editText?.text?.let {
            binding.etPassword.editText?.setSelection(
                it.length
            )
        }
    }

    private fun isFormValid(): Boolean {
        var isValid = true

        val email = binding.etEmail.editText?.text.toString().trim()
        if (email.isNotEmpty() && !RegexUtil.matchEmail(email)) {
            binding.etEmail.error = "Email tidak valid."
            isValid = false
        } else {
            binding.etEmail.error = null
        }

        val passwd = binding.etPassword.editText?.text.toString()
        val passRetry = binding.etPasswordConfirm.editText?.text.toString()

        val validation = ValidationUtil.passwordValidation(passwd)

        if (passwd.isNotEmpty() && !validation.status) {
            binding.etPassword.error = validation.message
            isValid = false
        } else if (passRetry != passwd) {
            binding.etPassword.error = null
            if (passRetry.isNotEmpty()) {
                binding.etPasswordConfirm.error = "Kata sandi tidak sama."
                isValid = false
            }
        } else {
            binding.etPasswordConfirm.error = null
        }

        return isValid
    }
}