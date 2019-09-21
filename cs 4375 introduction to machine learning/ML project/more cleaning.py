
# coding: utf-8

# In[42]:


"""
Bag of words and TFIDF
"""
import json_lines
import numpy as np
from pprint import pprint
from sklearn.manifold import TSNE
from sklearn.decomposition import PCA
import re
import string
import itertools
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.cluster import KMeans
from collections import Counter
import matplotlib.pyplot as plt




"plotting function 2d space"
def plot_embedding(x, y, title):
    plt.figure()
    plt.scatter(x[:, 0], x[:, 1], color=plt.cm.Set1(y/8))
    
    plt.xticks([])
    plt.yticks([])
    plt.title(title)
    plt.show()

f = open('NewYorkTimesClean.jsonl')
data = []
bits = []
labels = []
text = []
cleanText = []
stoplist = set('for a of the and to in he she them they is are was on from it its and')

# Clean the lead paragraphs
def cleanStrings(doc):
    exclude = set(string.punctuation)
   # lemma = WordNetLemmatizer()
    
    stop_free = " ".join([i for i in doc.lower().split() if i not in stoplist])
    punc_free = ''.join(ch for ch in stop_free if ch not in exclude)
    #normalized = " ".join(lemma.lemmatize(word) for word in punc_free.split())
    processed = re.sub(r"\d+","",punc_free)
    y = processed.split()
    return y


#this shoves the jsonl file into an array and converts it into an array of strings
for item in json_lines.reader(f):
    data = np.append(data, str(item))

#this goes through the array of raw string data and pulls out the labels
for i in data:
    tag = str.split(i)
    labels = np.append(labels, tag[1])

#prints labels
#for i in range(0, len(labels)):
   # print(labels[i])
    

for i in data:
    
    body = i.split("lead_paragraph")
    text = np.append(text, body[1])

"""
labels now contains the labels
text now contains the body of the article
"""
for i in range(0, len(text)):
    if len(text[i]) > 1000:
        #print(len(text[i]))
        cleaning = cleanStrings(text[i])
        #print(cleaning)
        cleaning = ' '.join(cleaning)
        cleanText.append(cleaning)
        #print(cleanText[i])
    
"x now is basically data features"    
#vectors = TfidfVectorizer()
#final_vectors = vectors.fit_transform(cleanText)

print(len(cleanText))
f_write= open("long clean text.txt","w+")
for i in range(len(cleanText)):
    f_write.write(cleanText[i], "\n")