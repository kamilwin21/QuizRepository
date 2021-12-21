package com.example.quizappfirebase.MainActivityFiles.MainClasses

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.Looper
import android.text.BoringLayout
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.quizappfirebase.MainActivityFiles.ResultsQuizActivity
import com.example.quizappfirebase.R
import kotlinx.android.synthetic.main.activity_question.*
import java.util.*
import java.util.logging.Handler
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class MyOwnButtonClickListener : View.OnClickListener {
    var currentIdQuestion: Int = 0
    var textViewList: ArrayList<TextView>
    var questionsList: ArrayList<Question>
    var counterCorrectQuestion: Int = 0
    var questionName: String = ""


    constructor(currentIdQuestion: Int, textViewList: ArrayList<TextView>, questionsList: ArrayList<Question>, counterCorrectQuestion: Int, questionName: String){
        this.currentIdQuestion = currentIdQuestion
        this.textViewList = textViewList
        this.questionsList = questionsList
        this.counterCorrectQuestion = counterCorrectQuestion
        this.questionName = questionName



    }


    override fun onClick(v: View?) {



        //Do something if user click on view(This example is textView with choose answer in question)
        mySetXmlParametersOnClickListener(v)
        counterCorrectQuestion = countCorrectUserAnswer(v, questionsList[currentIdQuestion].correctAnswer, counterCorrectQuestion)

        currentIdQuestion++
        if (currentIdQuestion < questionsList.size)
        {
            showQuestions(questionsList, currentIdQuestion)


        }else if (currentIdQuestion == questionsList.size){
            goToResultsQuizActivity(v, counterCorrectQuestion, questionsList.size, questionName)

        }



    }

    private fun goToResultsQuizActivity(v: View?,pointsReceived: Int, totalPoints: Int, quizName: String){
        //Function allows user go to ResultsQuizActivity
        val intentResultsQuizActivity = Intent(v!!.context.applicationContext, ResultsQuizActivity::class.java)
        intentResultsQuizActivity.putExtra("points_received", pointsReceived)
        intentResultsQuizActivity.putExtra("total_points", totalPoints)
        intentResultsQuizActivity.putExtra("quiz_name", quizName)
        intentResultsQuizActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        v!!.context.applicationContext.startActivity(intentResultsQuizActivity)
    }

    private fun countCorrectUserAnswer(v: View?, correctAnswer: String, pointsReceived: Int): Int{
        //Counter correctUserAnswers
        var counter = pointsReceived
        if (v!!.findViewById<TextView>(v!!.id).text == correctAnswer)
        {
            counter++
        }
        return counter
    }

    private fun showQuestions(questionsList: ArrayList<Question>, id: Int){
        //Show current question in QuestionActivity
       textViewList[0].text = questionsList[id].title
       textViewList[1].text = questionsList[id].answer1
       textViewList[2].text = questionsList[id].answer2
       textViewList[3].text = questionsList[id].answer3
       textViewList[4].text = questionsList[id].answer4

    }
    private fun mySetXmlParametersOnClickListener(v: View?){
        //change xml parameters if user clicked on something view like button or text_view
        v!!.background = ContextCompat.getDrawable(v!!.context.applicationContext, R.drawable.radius2)
        Timer("Set_xml_value", false).schedule(250){
            v!!.background = ContextCompat.getDrawable(v!!.context.applicationContext, R.drawable.radius)
        }
        //Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackClick = false },2000)
    }


}