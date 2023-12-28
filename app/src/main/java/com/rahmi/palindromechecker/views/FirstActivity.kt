package com.rahmi.palindromechecker.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.rahmi.palindromechecker.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener {
            val sentence = binding.edtSentence.text.toString()
            if (sentence.isBlank()) {
                AlertDialog.Builder(this)
                    .setMessage("Please enter a sentence to check.")
                    .show()
            } else {
                val isPalindrome = isPalindrome(sentence)
                AlertDialog.Builder(this)
                    .setMessage(if (isPalindrome) "$sentence is Palindrome" else "$sentence is not Palindrome")
                    .show()
            }
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("NAME", binding.edtName.text.toString())
            startActivity(intent)
        }
    }

    private fun isPalindrome(str: String): Boolean {
        val cleanStr = str.replace("\\s+".toRegex(), "").toLowerCase()
        return cleanStr == cleanStr.reversed()
    }
}