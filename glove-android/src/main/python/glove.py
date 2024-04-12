import pickle
from os.path import dirname, join
import h5py

def get_path( path ):
    return join(dirname(__file__), path )

hf = h5py.File(get_path('glove_vectors_50d.h5'), 'r')
vectors = hf.get( "glove_vectors" )

with open(get_path("glove_words.pkl")  , "rb") as file:
    indexes = pickle.load( file )

def get_embedding( word ):
    try:
        return vectors[ indexes[ word ] ]
    except KeyError:
        return [ 0.0 ] * 50
