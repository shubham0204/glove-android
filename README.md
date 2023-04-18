# `glove-android`: Using GloVe word embeddings in Android

`glove-android` is an Android library that provides an interface for using popular [GloVe](https://nlp.stanford.edu/projects/glove/) 
word embeddings.

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
