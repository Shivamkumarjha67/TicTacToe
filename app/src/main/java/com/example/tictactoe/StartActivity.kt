
package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class StartActivity : AppCompatActivity() {
    private lateinit var nameFirst : TextInputEditText
    private lateinit var nameSecond : TextInputEditText
    private lateinit var startGame : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        nameFirst = findViewById(R.id.p1editText)
        nameSecond = findViewById(R.id.p2editText)
        startGame = findViewById(R.id.btnStart)

        startGame.setOnClickListener {
            val playerFirst = nameFirst.text.toString()
            val playerSecond = nameSecond.text.toString()

            if(playerFirst.isNotEmpty() && playerSecond.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("1", playerFirst)
                intent.putExtra("2", playerSecond)

                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Please Fill your name first!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}