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
import com.id.mobileuts.databinding.ActivityNewLoginBinding
import com.id.mobileuts.services.SPManager
import com.id.mobileuts.utils.RegexUtil
import com.id.mobileuts.utils.ValidationUtil

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewLoginBinding

    private var passwordVisible = false


    private val _spManager: SPManager by lazy {
        SPManager(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.toolbar.let { toolbar ->
            toolbar.pageTitle.text = getString(R.string.login_title)
            toolbar.pageTitle.setTextColor(
                ContextCompat.getColor(this, R.color.white)
            )
            toolbar.toolbarAdditionalAction.isVisible = false
        }

        binding.btnLogin.isEnabled = false
        binding.etPassword.setEndIconOnClickListener {
            setPasswordVisibility()
        }

        binding.etPassword.editText?.addTextChangedListener {
            var isValid = true

            if (binding.etEmail.editText?.text.isNullOrBlank()) isValid = false
            if (binding.etPassword.editText?.text.isNullOrEmpty()) isValid = false

            binding.btnLogin.isEnabled = isFormValid() && isValid
        }

        binding.etPassword.editText?.addTextChangedListener {
            var isValid = true

            if (binding.etEmail.editText?.text.isNullOrBlank()) isValid = false
            if (binding.etPassword.editText?.text.isNullOrEmpty()) isValid = false

            binding.btnLogin.isEnabled = isFormValid() && isValid
        }

        binding.btnLogin.setOnClickListener {
            actionSubmit()
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun actionSubmit() {
        if (!isFormValid()) return

        val email = binding.etEmail.editText?.text.toString().trim()
        val passwd = binding.etPassword.editText?.text.toString()
        val savedEmail = _spManager.getString(SPManager.SP_USER_EMAIL, passwd)
        val savedPassword = _spManager.getString(SPManager.SP_USER_PASSWORD, passwd)

        if (email == savedEmail && savedPassword == passwd) {
            _spManager.putBoolean("logged",true)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Selamat Datang", Toast.LENGTH_SHORT)
                .show()
        }else{
            Log.d("Login Activity", "Login: Login Gagal")
            Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT)
                .show()
        }

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

        val validation = ValidationUtil.passwordValidation(passwd)

        if (passwd.isNotEmpty() && !validation.status) {
            binding.etPassword.error = validation.message
            isValid = false
        } else {
            binding.etPassword.error = null
        }

        return isValid
    }
}