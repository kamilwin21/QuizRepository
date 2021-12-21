package com.example.quizappfirebase.MainActivityFiles

import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.quizappfirebase.MainActivityFiles.MainClasses.Category
import com.example.quizappfirebase.MainActivityFiles.MainClasses.MyOwnButtonClickListener
import com.example.quizappfirebase.MainActivityFiles.MainClasses.Question
import com.example.quizappfirebase.R
import com.example.quizappfirebase.RegistrationAndLoginUser.Classes.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_question.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import kotlin.random.Random

class QuestionActivity : AppCompatActivity() {
    private var doubleBackClick:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)







            //Add connection from database and will read the questions here and will show it here
            //===================================================================================
            if (intent.hasExtra("question_category") )
            {

                val questionName: String = intent.getStringExtra("question_category").toString()
                read_questions_in_category_from_database(object :MyCallback{
                    override fun onCallback(list: ArrayList<Question>) {

                        if (list.isEmpty())
                            {
                                Toast.makeText(applicationContext,"Nie udało się wczytać pytań", Toast.LENGTH_LONG).show()
                            }else{

                            var listOfTextView:ArrayList<TextView> = arrayListOf (
                                tw_activity_question_question,
                                tw_activity_question_answer_1,
                                tw_activity_question_answer_2,
                                tw_activity_question_answer_3,
                                tw_activity_question_answer_4,
                                questionTimer
                            )

                            var currentIdQuestion: Int = 0
                                var counterCorrectQuestion: Int = 0

                                showQuestion(list, currentIdQuestion)


                                var myOwnClickListener = MyOwnButtonClickListener(currentIdQuestion,listOfTextView,list,counterCorrectQuestion, questionName)



                                tw_activity_question_answer_1.setOnClickListener(myOwnClickListener)

                                tw_activity_question_answer_2.setOnClickListener(myOwnClickListener)

                                tw_activity_question_answer_3.setOnClickListener(myOwnClickListener)

                                tw_activity_question_answer_4.setOnClickListener(myOwnClickListener)

                                setIntervalTimer()



                         }

                    }
                }, connection_from_database())


            }else{
                Toast.makeText(applicationContext,"Nie udało się załadować pytań", Toast.LENGTH_LONG).show()
            }



    }

    private fun setIntervalTimer(){

        val timer = object : CountDownTimer(5000, 1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                var counter = millisUntilFinished / 1000


                    questionTimer.text = counter.toString()



            }

            override fun onFinish() {

                //tw_activity_question_answer_1.performClick()
                questionTimer.text = ""
                super.cancel()




            }

        }

        timer.start()



    }

    override fun onBackPressed() {
        if (doubleBackClick)
        {
            super.onBackPressed()
            return
        }
        doubleBackClick= true
        Toast.makeText(applicationContext,"Na pewno chcesz wyjsć z quizu?", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackClick = false },2000)




    }






    private fun showQuestion(randomQuestionsList: ArrayList<Question>, id: Int){
        tw_activity_question_question.text = randomQuestionsList[id].title
        tw_activity_question_answer_1.text = randomQuestionsList[id].answer1
        tw_activity_question_answer_2.text = randomQuestionsList[id].answer2
        tw_activity_question_answer_3.text = randomQuestionsList[id].answer3
        tw_activity_question_answer_4.text = randomQuestionsList[id].answer4

    }


    private fun connection_from_database():DatabaseReference{

        var categoryId = intent.getLongExtra("id",-1)
        var databaseConnection = FirebaseDatabase.getInstance("https://quizfirebase-4cb19-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference.child("Categories").child(categoryId.toString()).child("questions")
        return databaseConnection
    }

    interface MyCallback {
        fun onCallback(list: ArrayList<Question>)
    }
    private fun read_questions_in_category_from_database(myCallback: MyCallback,databaseConnection: DatabaseReference) {

        databaseConnection.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var count = 0
                    var list: ArrayList<Question> = arrayListOf()

                    var question: Question?
                    for (h in snapshot.children) {
                        count++
                        question = h.getValue(Question::class.java)
                        list.add(question!!)


                    }

                    myCallback.onCallback(list)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }




}
