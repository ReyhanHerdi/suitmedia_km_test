package com.example.suitmediakmtest.screen.secondScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.suitmediakmtest.databinding.ActivitySelectedUserBinding
import com.example.suitmediakmtest.screen.thirdScreen.UsersListActivity

class SelectedUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySelectedUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvUserName.text = intent.getStringExtra(USER_NAME)

        if (!intent.getStringExtra(SELECTED_USER_NAME).isNullOrEmpty()) {
            binding.tvSelectedUserName.text = intent.getStringExtra(SELECTED_USER_NAME)
        }

        binding.btnBack.setOnClickListener(this)
        binding.btnChoose.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view) {
            binding.btnBack -> {
                finish()
            }
            binding.btnChoose -> {
                val username = intent.getStringExtra(USER_NAME)
                Log.d("NAME BTN", username.toString())
                val intent = Intent(this, UsersListActivity::class.java)
                intent.putExtra(UsersListActivity.USER_NAME, username)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val USER_NAME = "user_name"
        const val SELECTED_USER_NAME = "selected_user_name"
    }
}