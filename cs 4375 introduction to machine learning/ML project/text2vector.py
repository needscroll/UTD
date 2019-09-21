import numpy as np 
from pprint import pprint
import pickle
import json
from pycorenlp import StanfordCoreNLP
import pandas as pd
from collections import Counter
from sklearn.manifold import TSNE
import matplotlib.pyplot as plt
from randomcolor import RandomColor
import os


class Text2Vec(object):
    def __init__(self, wordvec_path, preload=False):
        self.wp = wordvec_path
        self.wv = {}    
        self.__read_wv__(preload=preload)
        self.nlp = StanfordCoreNLP('http://localhost:9000')


    def __read_wv__(self, sep=" ", preload=False):
        if not preload:
            with open(self.wp, 'r') as f:
                for line in f:
                    tmp = line.split(sep) 
                    word = tmp[0]
                    vec = np.array([float(each) for each in tmp[1:]])
                    self.wv[word] = vec
            print("Number of tokens: ", len(self.wv))
            # pprint(self.wv.keys())
            # dump wordvector
            with open('gloveWordVector.bin', 'wb') as f2:
                pickle.dump(self.wv, f2)
        else:
            with open(self.wp, 'rb') as f:
                self.wv = pickle.load(f)
    
    def convert2vec(self, in_path, out_path, has_keywords=False):
        vectors = []
        labels = []
        with open(in_path) as f:
            for i, line in enumerate(f):
                data = json.loads(line)
                section = data['section']
                if has_keywords:
                    keywords = data['keywords']
                else:
                    keywords = None
                headline = data['headline']
                lead_paragraph = data['lead_paragraph']
                tokens = []
                try:
                    out = self.nlp.annotate(lead_paragraph, properties={
                        'annotators': 'tokenize, ssplit, pos',
                        'outputFormat': 'json'
                    })
                    # take sentence 1
                    if isinstance(out, dict) and out['sentences']:
                        sentence = out['sentences'][0]
                        for each in sentence['tokens']:
                            word = each['word'].lower()
                            word = word.strip('.')
                            word = word.strip(',')
                            word = word.strip(')')
                            word = word.strip('(')
                            pos = each['pos']
                            if "JJ" in pos or "NN" in pos or "VB" in pos:
                                tokens.append(word)
                except AssertionError:
                    pass
                # add keywords
                if keywords:
                    for each in keywords:
                        tmp = each['value']
                        tmp = tmp.split(" ")
                        tmp = [each.strip(',').lower() for each in tmp]
                        tmp = [each.strip('.').lower() for each in tmp]
                        tmp = [each.strip(')').lower() for each in tmp]
                        tmp = [each.strip('(').lower() for each in tmp]
                        tokens += tmp
                
                # add headline
                if headline:
                    tmp = headline.split(' ')
                    tmp = [each.strip(',').lower() for each in tmp]
                    tmp = [each.strip('.').lower() for each in tmp]
                    tmp = [each.strip(')').lower() for each in tmp]
                    tmp = [each.strip('(').lower() for each in tmp]
                    tokens += tmp

                wv = None
                fail2find = []
                count = 0
                for t in tokens:
                    if t in self.wv:
                        if wv is None:
                            if float('inf') not in self.wv[t] and -float('inf') not in self.wv[t] and all(self.wv[t] < 1e5):
                                wv = self.wv[t]
                                count += 1
                        else:
                            if float('inf') not in self.wv[t] and -float('inf') not in self.wv[t] and all(self.wv[t] < 1e5):
                                wv += self.wv[t]
                                count += 1
                    else:
                        fail2find.append(t) 
                print("article %s -- Tokens not in word vector dictionary: %s" % (i, fail2find))
                if wv is not None:
                    vectors.append(wv/count)
                    labels.append(section)
                
        vectors = np.array(vectors)
        print(vectors.shape)
        print(vectors)
        unique_labels = set(labels)
        label_mapping = {}
        for i, each in enumerate(unique_labels):
            label_mapping[each] = i
        new_labels = []
        for each in labels:
            new_labels.append(label_mapping[each])
        new_labels = np.array(new_labels).reshape(-1, 1)
        print(new_labels.shape)
        complete_data = np.concatenate((vectors, new_labels), axis=1)
        print(complete_data)
        np.savetxt('NewYorkTime.csv', complete_data, delimiter=',')

    @staticmethod
    def plot_data(data):
        num_sample=5000
        label = data[:, -1]
        feature = data[:, :-1]

        assignment = {}

        for i in range(len(feature)):
            if label[i] not in assignment:
                assignment[label[i]] = []
            
            assignment[label[i]].append(i)
        
        # down sample
        old_assignment = assignment
        assignment = {}

        indicies = []
        for label in old_assignment:
            last_length = len(indicies)
            indicies += np.random.choice(
                old_assignment[label], 
                size=min(int(num_sample/len(old_assignment)), 
                len(old_assignment[label])), replace=False
                ).tolist()
            assignment[label] = np.arange(last_length, len(indicies))
        
        feature = feature[indicies]
        print(feature.shape) 
        print(len(indicies))
        print(len(np.unique(indicies)))

        tsne = TSNE()
        x = tsne.fit_transform(feature)

        fig, ax = plt.subplots()

        # ax.plot(x[:, 0], x[:, 1], '*')
        r = RandomColor()
        colors = r.generate(count=len(assignment))
        for i, label in enumerate(assignment):
            ax.plot(x[assignment[label]][:, 0], x[assignment[label]][:, 1], '*', color=colors[i], label=label)
        plt.legend()
        plt.show()

'''
class Guardian(object):
    def __init__(self, dir_path, meta_path, wordvec_path, preload=False):
        self.dir_path = dir_path          
        self.meta_path = meta_path
        self.wp = wordvec_path
        self.wv = {}    
        self.__read_wv__(preload=preload)
        self.nlp = StanfordCoreNLP('http://locahost:9000')

    def __read_wv__(self, sep=" ", preload=False):
        if not preload:
            with open(self.wp, 'r') as f:
                for line in f:
                    tmp = line.split(sep) 
                    word = tmp[0]
                    vec = np.array([float(each) for each in tmp[1:]])
                    self.wv[word] = vec
            print("Number of tokens: ", len(self.wv))
            # pprint(self.wv.keys())
            # dump wordvector
            with open('gloveWordVector.bin', 'wb') as f2:
                pickle.dump(self.wv, f2)
        else:
            with open(self.wp, 'rb') as f:
                self.wv = pickle.load(f)

    def convert(self):
        vectors = []
        labels = []
        with open(self.meta_path, 'r') as f:
            for j, line in enumerate(f):
                meta = json.loads(line)
                aid = meta['articleId']
                section = meta['sectionId']
                title = meta['title']
                tokens = []
                try:
                    summary = ""
                    lead_paragraph = ""
                    with open(os.path.join(self.dir_path, str(aid))) as f2:
                        for i, line in enumerate(f2):
                            if i == 0:
                                summary = line.strip()
                            else:
                                if line.strip() and i == 2:
                                    lead_paragraph = line.strip()
                                    break

                         
                    try:
                        if lead_paragraph:
                            out = self.nlp.annotate(lead_paragraph, properties={
                                'annotators': 'tokenize, ssplit, pos',
                                'outputFormat': 'json'
                            })
                            # take sentence 1
                            if isinstance(out, dict) and out['sentences']:
                                sentence = out['sentences'][0]
                                for each in sentence['tokens']:
                                    word = each['word'].lower()
                                    word = word.strip('.')
                                    word = word.strip(',')
                                    word = word.strip(')')
                                    word = word.strip('(')
                                    pos = each['pos']
                                    if "JJ" in pos or "NN" in pos or "VB" in pos:
                                        tokens.append(word)
                    except AssertionError:
                        pass 
                    
                    # add summary
                    summary_tokens = [each.lower() for each in summary.strip().split(' ')]
                    for i in range(len(summary_tokens)):
                        summary_tokens[i] = summary_tokens[i].strip('.')
                        summary_tokens[i] = summary_tokens[i].strip(',')
                        summary_tokens[i] = summary_tokens[i].strip(')')
                        summary_tokens[i] = summary_tokens[i].strip('(')
                    
                    tokens += [each for each in summary_tokens if each]

                    # add titles
                    titles_tokens = [each.lower() for each in title.strip().split(' ')]
                    for i in range(len(titles_tokens)):
                        titles_tokens[i] = titles_tokens[i].strip('.')
                        titles_tokens[i] = titles_tokens[i].strip(',')
                        titles_tokens[i] = titles_tokens[i].strip(')')
                        titles_tokens[i] = titles_tokens[i].strip('(')
                    
                    tokens += [each for each in titles_tokens if each]

                    wv = None
                    fail2find = []
                    count = 0
                    for t in tokens:
                        if t in self.wv:
                            if wv is None:
                                if float('inf') not in self.wv[t] and -float('inf') not in self.wv[t] and all(self.wv[t] < 1e5):
                                    wv = self.wv[t]
                                    count += 1
                            else:
                                if float('inf') not in self.wv[t] and -float('inf') not in self.wv[t] and all(self.wv[t] < 1e5):
                                    wv += self.wv[t]
                                    count += 1
                        else:
                            fail2find.append(t) 
                    print("article %s -- Tokens not in word vector dictionary: %s" % (j, fail2find))
                    if wv is not None:
                        vectors.append(wv/count)
                        labels.append(section)

                except OSError:
                    continue
        
        vectors = np.array(vectors)
        print(vectors.shape)
        print(vectors)
        unique_labels = set(labels)
        label_mapping = {}
        for i, each in enumerate(unique_labels):
            label_mapping[each] = i
        new_labels = []
        for each in labels:
            new_labels.append(label_mapping[each])
        new_labels = np.array(new_labels).reshape(-1, 1)
        print(new_labels.shape)
        complete_data = np.concatenate((vectors, new_labels), axis=1)
        print(complete_data)
        np.savetxt('Guardian.csv', complete_data, delimiter=',')
'''
if __name__ == '__main__':
     t2v = Text2Vec('gloveWordVector.bin', preload=True)
     t2v.convert2vec(
         "NewYorkTimesClean.jsonl",
         "NewYorkTimes.csv",
         has_keywords=True
     )
     
     
     #data = pd.read_csv('NewYorkTime.csv').values

    # Text2Vec.plot_data(data)
   # guad = Guardian('guardian', 'NewYorkTimesClean.jsonl', 'gloveWordVector.bin', preload=True)
   # guad.convert()

   # data = pd.read_csv('Guardian.csv').values

   # Text2Vec.plot_data(data)