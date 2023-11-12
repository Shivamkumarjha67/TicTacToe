package com.example.tictactoe

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var gridLayout : GridLayout
    private lateinit var reset : MaterialButton
    private var board = Array(3){Array(3){0}}
    private var currentPlayer = 1
    private var gameOver = false

    private lateinit var nameFirst : String
    private lateinit var nameSecond: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent
        nameFirst = intent.getStringExtra("1").toString()
        nameSecond = intent.getStringExtra("2").toString()

        showToast("${nameFirst} start game...")

        gridLayout = findViewById(R.id.gridLayout)
        reset = findViewById(R.id.btnReset)

        initializeGrid()

        reset.setOnClickListener {
            resetGame()
        }
    }

    private fun resetGame() {
        for(i in 0 until 3) {
            for(j in 0 until 3) {
                val cell = gridLayout.getChildAt(i*3+j) as Button
                cell.text = ""
            }
        }

        gameOver = false
        currentPlayer = 1
        reset.visibility = View.INVISIBLE
        board.map { it.fill(0) }
        showToast("${nameFirst} start game...")
    }

    private fun initializeGrid() {
        for(i in 0 until 3) {
            for(j in 0 until 3) {
                val cell = Button(this)
                cell.layoutParams = GridLayout.LayoutParams()
                cell.textSize = 45f
                cell.text = ""
                cell.setOnClickListener(CellClickListner(i,j))
                gridLayout.addView(cell)
            }
        }
    }

    inner class CellClickListner(private val i: Int, private val j: Int) : View.OnClickListener {
        override fun onClick(v : View?) {
//            showToast("Cell Clicked")
            if(board[i][j] == 0 && !gameOver) {
                val symbol = if (currentPlayer == 1)  "X" else "O"
                (v as Button).text = symbol
                board[i][j] = currentPlayer

                if(checkForWin(i, j)) {
                    gameOver = true
                    reset.visibility = View.VISIBLE

                    var name = if(currentPlayer == 1) nameFirst else nameSecond
                    showToast("Congratulation ${name} you won the game")
                } else if(!board.flatten().contains(0) && !gameOver) {
                    gameOver = true
                    reset.visibility = View.VISIBLE
                    showToast("Its a draw. Play Again!!")
                } else {
                    // This condition for continuing the game
                    currentPlayer = if (currentPlayer == 1) 2 else 1
                }
            }
        }
    }

    private fun checkForWin(i: Int, j: Int): Boolean {
        if(board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
        if(board[0][j] == currentPlayer && board[1][j] == currentPlayer && board[2][j] == currentPlayer) return true
        if(i == j && board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if(i == j && board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true

        return false
    }

    private fun showToast(msg : String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}