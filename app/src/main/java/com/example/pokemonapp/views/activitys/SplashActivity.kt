package com.example.pokemonapp.views.activitys

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pokemonapp.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.anim_alpha_out)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        }
        )

        lo_splash.startAnimation(anim)
    }
    override fun finish() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.white_two)
        super.finish()
        this.startActivity(Intent(this, HomeActivity::class.java))
        this.overridePendingTransition(0, 0);
    }

}