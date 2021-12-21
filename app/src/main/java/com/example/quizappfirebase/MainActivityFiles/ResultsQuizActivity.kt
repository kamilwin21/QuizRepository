package com.example.quizappfirebase.MainActivityFiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TestLooperManager
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.quizappfirebase.MainActivityFiles.MainClasses.Statics
import com.example.quizappfirebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_results_quiz.*
import java.util.*
import kotlin.collections.ArrayList

class ResultsQuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results_quiz)


        setValueForTextViews()

        btn_return_activity_results_quiz.setOnClickListener(MyOnClickListener)


    }

    private fun setValueForTextViews(){

        val twNameQuiz: TextView = tw_name_quiz_in_activity_results_quiz
        val twPointsToReceived: TextView = tw_points_received_quiz_in_activity_results_quiz
        val twTotalPointsToReceived:TextView = tw_total_points_to_received_quiz_in_activity_results_quiz
        val btnRetun: Button = btn_return_activity_results_quiz
        if (intent.hasExtra("points_received") && intent.hasExtra("total_points")
            && intent.hasExtra("quiz_name"))
        {

            twTotalPointsToReceived.text = intent.getIntExtra("total_points", 0).toString()
            twPointsToReceived.text = intent.getIntExtra("points_received", 0).toString()
            twNameQuiz.text = intent.getStringExtra("quiz_name").toString()
        }else{
            val nodata: String = "Brak danych"
            twTotalPointsToReceived.text = nodata
            twPointsToReceived.text = nodata
            twNameQuiz.text = nodata
        }

    }

    private val MyOnClickListener = View.OnClickListener {
        val randomId = Date().time
        val connectionWithUserStatistics = FirebaseDatabase.getInstance("https://quizfirebase-4cb19-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Statistics").orderByChild("quizName").equalTo(intent.getStringExtra("quiz_name").toString())

        //var statics: Statics = Statics(randomId.toString(), intent.getStringExtra("quiz_name").toString(), intent.getIntExtra("points_received",0).toString())

       // connectionWithUserStatistics.child("Statistics").child(randomId.toString()).setValue(statics)

        connectionWithUserStatistics.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               // var statisticsUserList: ArrayList<Statics?> = arrayListOf()
                if (snapshot.exists())
                {
                    //Do if user statistics of this quiz was created in firebase

                    var statics: Statics? = Statics()
                        for (i in snapshot.children)
                         {
                        statics = i.getValue(Statics::class.java)
                      //  statisticsUserList.add(statics)

                         }



                        val saveUserStatisticsInFireBase = FirebaseDatabase.getInstance("https://quizfirebase-4cb19-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Statistics").child(statics!!.id)


                        //saveUserStatisticsInFireBase.setValue()




                    Toast.makeText(applicationContext,"${statics}",Toast.LENGTH_SHORT).show()
                }else{
                    val userStatics: Statics = Statics(randomId.toString(),intent.getStringExtra("quiz_name").toString(), intent.getIntExtra("points_received", 0).toString()  )
                    val saveUserStatisticsInFireBase = FirebaseDatabase.getInstance("https://quizfirebase-4cb19-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Statistics").child(randomId.toString())
                    saveUserStatisticsInFireBase.setValue(userStatics)
                    //Do if user statistics of this quiz not created yet in firebase

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


        val intentToFinishResltsQuizData = Intent(applicationContext, MainActivity::class.java)
        //startActivity(intentToFinishResltsQuizData)
    }
}