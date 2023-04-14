package com.ml.shubham0204.glove_android

import com.chaquo.python.PyObject
import com.chaquo.python.Python
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sqrt

class GloVe {

    companion object {

        suspend fun loadEmbeddings( result : ((GloVeEmbeddings) -> Unit) ) = CoroutineScope( Dispatchers.IO ).launch {
            val python = Python.getInstance()
            val module = python.getModule( "glove" )
            result( GloVeEmbeddings( module[ "get_embedding" ]!! ) )
        }

        fun compare( embedding1 : FloatArray , embedding2 : FloatArray ) : Float {
            val mag1 = sqrt( embedding1.map { it * it }.sum() )
            val mag2 = sqrt( embedding1.map { it * it }.sum() )
            val dot = embedding1.mapIndexed{ i , xi -> xi * embedding2[ i ] }.sum()
            return dot / (mag1 * mag2)
        }

    }

    class GloVeEmbeddings( private val embeddingFunc : PyObject ) {

        fun getEmbedding( word : String ) : FloatArray {
            val output = embeddingFunc.call( word ).toJava( FloatArray::class.java )
            return output
        }

    }

}