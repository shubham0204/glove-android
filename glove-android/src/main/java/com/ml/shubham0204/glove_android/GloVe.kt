package com.ml.shubham0204.glove_android

import com.chaquo.python.PyObject
import com.chaquo.python.Python
import kotlin.math.sqrt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A port of
 * [GloVe (Global Vectors for Word Representation)](https://nlp.stanford.edu/projects/glove/) for
 * Android. Internally, it uses a Python script to load 50D GloVe-embeddings from a HDF5 file and
 * maintains an index as a `dict` for faster access.
 *
 * ### Example Usage:
 * ```
 * val gloveEmbeddings: GloVeEmbeddings
 *
 * GloVe.loadEmbeddings {
 * gloveEmbeddings = it
 * }
 *
 * val embedding = gloveEmbeddings.getEmbedding( "hello" )
 * println( embedding.contentToString() )
 * ```
 */
class GloVe {

    companion object {

        /**
         * A suspend function which loads embeddings from HDF5 files internally, and stores them in
         * a hashmap for faster access. Once, the embeddings are loaded, retrieval of embeddings is
         * nearly O( 1 ).
         *
         * @param result A callback which provides [GloVeEmbeddings] object to access the embeddings
         */
        suspend fun loadEmbeddings(result: ((GloVeEmbeddings) -> Unit)) =
            withContext(Dispatchers.IO) {
                val python = Python.getInstance()
                val module = python.getModule("glove")
                result(GloVeEmbeddings(module["get_embedding"]!!))
            }

        /**
         * Computes the cosine of the angle (also called 'cosine similarity') between the given two
         * embeddings (vectors) The cosine similarity is a number in the range [ -1 , 1 ], where 1
         * indicates perfect similarity and -1 indicates a complete dissimilarity.
         *
         * @param embedding1 The first vector as a `FloatArray`
         * @param embedding2 The second vector as a `FloatArray`
         * @return The cosine similarity between `embedding1` and `embedding2`
         */
        fun compare(embedding1: FloatArray, embedding2: FloatArray): Float {
            val mag1 = sqrt(embedding1.map { it * it }.sum())
            val mag2 = sqrt(embedding1.map { it * it }.sum())

            val dot = embedding1.mapIndexed { i, xi -> xi * embedding2[i] }.sum()
            return dot / (mag1 * mag2)
        }
    }

    /**
     * After execution of [GloVe.loadEmbeddings], an instance of this class is provided in the
     * result callback. The [GloVeEmbeddings.getEmbedding] method should be used to retrieve
     * embeddings.
     */
    class GloVeEmbeddings(private val embeddingFunc: PyObject) {

        /**
         * Given the word, retrieve the corresponding GloVe embedding
         *
         * @param word The word whose embedding has to be retrieved
         * @return The embedding of the given `word` represented as a `FloatArray` with 50 elements
         */
        fun getEmbedding(word: String): FloatArray {
            val output = embeddingFunc.call(word).toJava(FloatArray::class.java)
            return output
        }
    }
}
