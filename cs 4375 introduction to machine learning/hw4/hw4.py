from sklearn.datasets import make_blobs
from sklearn.svm import SVC
import numpy as np
import pandas as pd
from sklearn.neural_network import MLPClassifier

x, y = make_blobs(n_samples=100, centers=2, random_state=6)
x1, y1 = make_blobs(n_samples=500, centers=10, random_state=6)

clf = SVC(gamma='auto')
clf = clf.fit(x, y)

clf1 = SVC(gamma='auto', decision_function_shape='ovr')
clf1 = clf1.fit(x1, y1)

clf2 = SVC(gamma='auto', decision_function_shape='ovo')
clf2 = clf1.fit(x1, y1)

print("accuracy of SVM on problem 1: ",clf.score(x, y))
print("accuracy of SVM in one vs all on problem 1: ", clf1.score(x1, y1))
print("accuracy of SVM in one vs one on problem 1: ",clf1.score(x1, y1))


""" Load data """
data = pd.read_csv('train.csv')

""" Sample data """
for i in range(10):
    idx_by_label_t = np.array(data.index[data['label']==i].tolist())
    idx_by_label_sample_t = np.random.choice(idx_by_label_t, size=80, replace=False)
    if i == 0:
        idx_all_t = idx_by_label_sample_t
    else:
        idx_all_t = np.append(idx_all_t, idx_by_label_sample_t)
        
        
data_sample_t = data.iloc[np.sort(idx_all_t)]

""" Divide data to features and labels """
data_features_t = data_sample_t.iloc[:, 1:]
data_labels_t = data_sample_t.iloc[:, 0]

for i in range(10):
    idx_by_label = np.array(data.index[data['label']==i].tolist())
    idx_by_label_sample = np.random.choice(idx_by_label, size=20, replace=False)
    if i == 0:
        idx_all = idx_by_label_sample
    else:
        idx_all = np.append(idx_all, idx_by_label_sample)
        
        
data_sample = data.iloc[np.sort(idx_all)]
data_features = data_sample.iloc[:, 1:]
data_labels = data_sample.iloc[:, 0]

mlp = MLPClassifier()
mlp.fit(data_features_t, data_labels_t)

print("accuracy of cnn from sklearn from problem 2 on training set: ", mlp.score(data_features_t, data_labels_t))
print("accuracy of cnn from sklearn from problem 2 on test set: ", mlp.score(data_features, data_labels))