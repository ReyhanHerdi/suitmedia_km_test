package com.example.suitmediakmtest.Screen.thirdScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.suitmediakmtest.databinding.ActivityUsersListBinding

class UsersListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUsersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUsersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view) {
            binding.btnBack -> finish()
        }
    }
}