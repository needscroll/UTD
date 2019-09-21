import json_lines
import numpy as np

f = open('NewYorkTimesClean.jsonl')

data = []
bits = []

labels = []
text = []

#this shoves the jsonl file into an array and converts it into an array of strings
for item in json_lines.reader(f):
    data = np.append(data, str(item))

#this goes through the array of raw string data and pulls out the labels
for i in data:
    tag = str.split(i)
    labels = np.append(labels, tag[1])

#prints labels
#for i in range(0, len(labels)):
    #print(labels[i])
    
for i in data:
    body = i.split("lead_paragraph")
    text = np.append(text, body[1])

'''
labels now contains the labels
text now contains the body of the article
'''

#cleaning the strings
for i in range(0, len(labels)):
    labels[i] = labels[i][1:len(labels[i]) - 2]

#prints the labels for debugging
'''    
for i in range(0, len(labels)):
    print(labels[i])
'''

#cleaning the body text
for i in range(0, len(text)):
    text[i] = text[i][4:len(text[i]) - 2]


'''
labels now contain clean labels
text now contains the cleaned version of the body of the article
'''