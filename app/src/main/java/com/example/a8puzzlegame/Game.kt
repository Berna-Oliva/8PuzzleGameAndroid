package com.example.a8puzzlegame

import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Collections.shuffle

class Game : AppCompatActivity() {
    private val currList: MutableList<String> = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "0")
    private val finList = listOf("1", "2", "3", "4", "5", "6", "7", "8", "0")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val playerState = findViewById<TextView>(R.id.playerState)
        val resetBtn = findViewById<Button>(R.id.resetBtn)
        //initialize buttons
        val btns = arrayOf(
                findViewById<Button>(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8),
                findViewById(R.id.button0)
        )

        //shuffle initial list
        setupGame(currList, btns)

        //listener for button clicked
        for((index, currBtn) in btns.withIndex()){
            currBtn.setOnClickListener{
                currList.switchCell(btns, index) //perform switch cell function to check if 0 is adjacent, then move if yes
                if(currList == finList) {
                    playerState.text = "SOLVED!"
                }
                d("berna", btns[index].text.toString() + "was clicked!")
                d("berna", currList.toString())
            }
            resetBtn.setOnClickListener {
                setupGame(currList, btns)
            }
        }

    }

    private fun setupGame(currList: MutableList<String>, btns: Array<Button>){
        val currList: MutableList<String> = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "0")
        shuffle(currList)
        while(!solvable(currList)){ //continue only if current list is solvable
            shuffle(currList)
        }
        //for resetting, make sure all buttons are visible
        for(i in 0..8){
            btns[i].visibility = View.VISIBLE
        }
        //reassign values to buttons
        for(i in 0..8){
            btns[i].text = currList[i]
            if(btns[i].text == "0"){//set button with value 0 to invisible
                btns[i].visibility = View.INVISIBLE
            }
        }
    }

    private fun MutableList<String>.switchCell(btns: Array<Button>, index: Int) {
        //index is the position in btns array
        when (index) {
            0 -> {//this is the cell on the top left corner so we only have to check cells to the right and below
                when {
                    checkRight(btns, index) -> moveRight(this, btns, index)
                    checkBelow(btns, index) -> moveDown(this, btns, index)
                }
                return
            }
            1 -> {//this is the cell on the top middle corner so we only have to check cells to the left, right, and below
                when {
                    checkLeft(btns, index) -> moveLeft(this, btns, index)
                    checkRight(btns, index) -> moveRight(this, btns, index)
                    checkBelow(btns, index) -> moveDown(this, btns, index)
                }
                return
            }
            2 -> {//this is the cell on the top right corner so we only have to check cells to the left, and below
                when {
                    checkLeft(btns, index) -> moveLeft(this, btns, index)
                    checkBelow(btns, index) -> moveDown(this, btns, index)
                }
                return
            }
            3 -> {//this is the cell on the middle left so we only have to check cells to the above, right, and below
                when {
                    checkAbove(btns, index) -> moveUp(this, btns, index)
                    checkRight(btns, index) -> moveRight(this, btns, index)
                    checkBelow(btns, index) -> moveDown(this, btns, index)
                }
                return
            }
            4 -> {//this is the cell on the middle so we have to check cells above, left, right, and below
                when {
                    checkAbove(btns, index) -> moveUp(this, btns, index)
                    checkLeft(btns, index) -> moveLeft(this, btns, index)
                    checkRight(btns, index) -> moveRight(this, btns, index)
                    checkBelow(btns, index) -> moveDown(this, btns, index)
                }
                return
            }
            5 -> {//this is the cell on the middle right so we have to check cells above, left, and below
                when {
                    checkAbove(btns, index) -> moveUp(this, btns, index)
                    checkLeft(btns, index) -> moveLeft(this, btns, index)
                    checkBelow(btns, index) -> moveDown(this, btns, index)
                }
                return
            }
            6 -> {//this is the cell on the bottom left so we have to check cells to the right and above
                when {
                    checkAbove(btns, index) -> moveUp(this, btns, index)
                    checkRight(btns, index) -> moveRight(this, btns, index)
                }
                return
            }
            7 -> {//this is the cell on the bottom middle so we have to check cells to the left, right, and above
                when {
                    checkAbove(btns, index) -> moveUp(this, btns, index)
                    checkRight(btns, index) -> moveRight(this, btns, index)
                    checkLeft(btns, index) -> moveLeft(this, btns, index)
                }
                return
            }
            8 -> {//this is the cell on the bottom right so we have to check cells to the left, and above
                when {
                    checkAbove(btns, index) -> moveUp(this, btns, index)
                    checkLeft(btns, index) -> moveLeft(this, btns, index)
                }
                return
            }
        }
    }

    private fun checkRight(btns: Array<Button>, index: Int): Boolean{
        return btns[index + 1].text.toString() == "0"
    }

    private fun checkLeft(btns: Array<Button>, index: Int): Boolean{
        return btns[index - 1].text.toString() == "0"
    }

    private fun checkBelow(btns: Array<Button>, index: Int): Boolean{
        return btns[index + 3].text.toString() == "0"
    }

    private fun checkAbove(btns: Array<Button>, index: Int): Boolean{
        return btns[index - 3].text.toString() == "0"
    }

    private fun moveRight(currList: MutableList<String>, btns: Array<Button>, index: Int){
        btns[index + 1].text = btns[index].text.toString()
        currList[index + 1] = btns[index].text.toString()
        currList[index] = "0"
        btns[index].text = "0"
        btns[index].visibility = View.INVISIBLE
        btns[index + 1].visibility = View.VISIBLE
    }

    private fun moveLeft(currList: MutableList<String>, btns: Array<Button>, index: Int){
        btns[index - 1].text = btns[index].text.toString()
        currList[index - 1] = btns[index].text.toString()
        currList[index] = "0"
        btns[index].text = "0"
        btns[index].visibility = View.INVISIBLE
        btns[index - 1].visibility = View.VISIBLE
    }

    private fun moveDown(currList: MutableList<String>, btns: Array<Button>, index: Int){
        btns[index + 3].text = btns[index].text.toString()
        currList[index + 3] = btns[index].text.toString()
        currList[index] = "0"
        btns[index].text = "0"
        btns[index].visibility = View.INVISIBLE
        btns[index + 3].visibility = View.VISIBLE

    }

    private fun moveUp(currList: MutableList<String>, btns: Array<Button>, index: Int){
        btns[index - 3].text = btns[index].text.toString()
        currList[index - 3] = btns[index].text.toString()
        currList[index] = "0"
        btns[index].text = "0"
        btns[index].visibility = View.INVISIBLE
        btns[index - 3].visibility = View.VISIBLE

    }

    fun solvable(currList: MutableList<String>): Boolean {
        val intArr = IntArray(9)
        var sum = 0
        var toTheRight: Int
        //parse into int array
        for (i in currList.indices) {
            intArr[i] = currList[i].toInt()
        }

        //cycle through all values except 0
        for (i in intArr.indices) {
            if (intArr[i] == 0) continue
            toTheRight = 0
            //check how many int to the right of curr index are less than the
            // current value of index
            for (temp in i until intArr.size) {
                if (intArr[temp] == 0) continue else if (intArr[temp] < intArr[i]) toTheRight += 1
            }
            //increment sum by toTheRight
            sum += toTheRight
        }
        //if sum is odd, the puzzle is unsolvable and vice versa
        return sum % 2 != 1
    }

}