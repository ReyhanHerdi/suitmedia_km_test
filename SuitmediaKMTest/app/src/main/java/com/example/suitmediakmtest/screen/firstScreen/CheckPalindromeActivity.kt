package com.example.suitmediakmtest.screen.firstScreen

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.suitmediakmtest.R
import com.example.suitmediakmtest.screen.secondScreen.SelectedUserActivity
import com.example.suitmediakmtest.databinding.ActivityCheckPalindromeBinding

class CheckPalindromeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCheckPalindromeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckPalindromeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }

    private fun isPalindrome(): Boolean {
        val text = binding.edtPalindrome.text.toString().toLowerCase()
        val reversedText = text.reversed()

        return text == reversedText
    }

    private fun createDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.apply {
            setTitle(R.string.result)
            if (isPalindrome()) setMessage("Text is palindrome") else setMessage("Text is not palindrome")
        }
        val showDialog = dialogBuilder.create()
        showDialog.show()
    }

    override fun onClick(view: View?) {
        when(view) {
            binding.btnCheck -> {
                createDialog()
            }
            binding.btnNext -> {
                val intent = Intent(this, SelectedUserActivity::class.java)
                intent.putExtra(SelectedUserActivity.USER_NAME, binding.edtName.text.toString())
                startActivity(intent)
            }
        }
    }
}