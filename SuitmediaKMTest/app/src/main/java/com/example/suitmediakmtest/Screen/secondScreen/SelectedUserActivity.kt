package com.example.suitmediakmtest.Screen.secondScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.example.suitmediakmtest.R
import com.example.suitmediakmtest.Screen.thirdScreen.UsersListActivity
import com.example.suitmediakmtest.databinding.ActivitySelectedUserBinding

class SelectedUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySelectedUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvUserName.text = intent.getStringExtra(USER_NAME)

        binding.btnBack.setOnClickListener(this)
        binding.btnChoose.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view) {
            binding.btnBack -> {
                finish()
            }
            binding.btnChoose -> {
                val intent = Intent(this, UsersListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val USER_NAME = "user_name"
    }
}