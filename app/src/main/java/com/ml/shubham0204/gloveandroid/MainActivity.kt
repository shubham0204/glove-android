package com.ml.shubham0204.gloveandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*
        CoroutineScope( Dispatchers.IO ).launch {
            GloVe.loadEmbeddings {
                val embedding = it.getEmbedding( "hello" )
                println( "Embedding is $embedding" )
            }
        }

         */


    }



}