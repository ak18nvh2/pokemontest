package com.example.pokemonapp.views.activitys

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonapp.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.anim_alpha_out)
        Handler().postDelayed({
            this.startActivity(Intent(this, HomeActivity::class.java))
            this.overridePendingTransition(0, 0);
            finish()
        }, 3000)

        lo_splash.startAnimation(anim)

    }

}