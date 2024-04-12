# `glove-android`: Using GloVe word embeddings in Android

`glove-android` is an Android library that provides an interface for using popular [GloVe](https://nlp.stanford.edu/projects/glove/) 
word embeddings.

<html>
<p float="left">
  <img src="https://user-images.githubusercontent.com/41076823/232805813-e6b28680-1865-4add-9fd2-8712d20abb0c.png" width="200" />
  <img src="https://user-images.githubusercontent.com/41076823/232805936-82a0f3fb-dcd6-4e7f-9ab2-db2793a06c39.png" width="200" /> 
  <img src="https://user-images.githubusercontent.com/41076823/232806007-ad5e48df-403a-4aca-87b5-be6f660b303e.png" width="200" />
</p>
</html>

## Installation

1. Download `glove-android.aar` from the latest release. (See [Releases](https://github.com/shubham0204/glove-android/releases))
2. Move the AAR to `app/libs`.
3. In module-level `build.gradle`, add the dependency,

```groovy
dependencies {
    ...
    implementation files('libs/glove-android.aar')
    ...
}
```

## Usage

To load the embeddings from storage, use the `GloVe.loadEmbeddings` method, which returns a `GloveEmbeddings` 
object as a parameter in the given lambda function.

```kotlin
val gloveEmbeddings: GloVeEmbeddings

GloVe.loadEmbeddings {
    gloveEmbeddings = it
}
```

Call the `gloveEmbeddings.getEmbedding` method providing a word as parameter. The resultant embedding 
is returned as a `FloatArray` with `size=50` (indicating a 50-D embedding).

```kotlin
val embedding = gloveEmbeddings.getEmbedding( "hello" )
println( embedding.contentToString() )
```

> If no embedding is found for the given word, an empty `FloatArray` is returned

## Citation

```text
@inproceedings{pennington2014glove,
  author = {Jeffrey Pennington and Richard Socher and Christopher D. Manning},
  booktitle = {Empirical Methods in Natural Language Processing (EMNLP)},
  title = {GloVe: Global Vectors for Word Representation},
  year = {2014},
  pages = {1532--1543},
  url = {http://www.aclweb.org/anthology/D14-1162},
}
```

## Useful Resources

* [Reddit discussion on `r/androiddev`](https://www.reddit.com/r/androiddev/comments/168u3h7/gloveandroid_an_android_library_providing_access/)
