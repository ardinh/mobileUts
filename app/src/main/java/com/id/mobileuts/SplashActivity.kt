package com.id.mobileuts

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.id.mobileuts.services.SPManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private val _spManager: SPManager by lazy {
        SPManager(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        var email = _spManager.getString(SPManager.SP_USER_EMAIL)
        var isLogin = _spManager.getBoolean("logged",false)
        lifecycleScope.launch {
            delay(1500)
            val intent = Intent(
                applicationContext, if (isLogin) {
                    MainActivity::class.java
                } else if (!email.isNullOrEmpty()) {
                    LoginActivity::class.java
                } else  {
                    RegisterActivity::class.java
                }
            )
            startActivity(intent)
            finish()
        }
    }
}