package com.example.quizappfirebase.MainActivityFiles.Adapters


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quizappfirebase.MainActivityFiles.MainActivity
import com.example.quizappfirebase.MainActivityFiles.MainClasses.Category
import com.example.quizappfirebase.MainActivityFiles.MainClasses.Question
import com.example.quizappfirebase.MainActivityFiles.QuestionActivity
import com.example.quizappfirebase.R
import kotlinx.android.synthetic.main.fragment_main_page.view.*
import kotlinx.android.synthetic.main.layout_position_categories_in_adaptercategory.view.*
import java.io.Serializable
import kotlin.coroutines.coroutineContext
import kotlin.random.Random

class AdapterCategory (val context: Context,val categoriesList: ArrayList<Category>): RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.layout_position_categories_in_adaptercategory,parent,false)
        return MyViewHolder(positionList)
    }







    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val layout = holder.view.linear_layout_id_position_categories
        val tw= holder.view.tw_category
        //layout.setBackgroundColor(colors.get(position))

        tw.text = categoriesList[position].categoryName



        layout.setOnClickListener {
            //Go to QuestionActivity
            val intentQuestion =  Intent(holder.view.context.applicationContext, QuestionActivity::class.java)
            intentQuestion.putExtra("question_category", categoriesList[position].categoryName)
            intentQuestion.putExtra("id", categoriesList[position].id)
            holder.view.context.startActivity(intentQuestion)
            it.background = ContextCompat.getDrawable(holder.view.context, R.drawable.radius2)







        }

    }
    //Size of recycler elements
    override fun getItemCount(): Int {
        return categoriesList.size
    }
    //Listener lets go to QuestionActivity
    private val myOnClickListener = View.OnClickListener {
        val intentQuestion = Intent(context.applicationContext, QuestionActivity::class.java)
        context.startActivity(intentQuestion)
        //Here, I have no idea how I can take holder and position variables TO CHANGE!!

    }



}



class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){}