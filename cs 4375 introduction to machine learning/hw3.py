import numpy as np
import pandas as pd
from sklearn.model_selection import cross_val_score
from sklearn.naive_bayes import GaussianNB
from sklearn.neighbors import KNeighborsClassifier

""" Load data """
data = pd.read_csv('train.csv')

""" Sample data """
def sample_data(x):
    for i in range(10):
        idx_by_label = np.array(data.index[data['label']==i].tolist())
        idx_by_label_sample = np.random.choice(idx_by_label, size=100, replace=False)
        if i == 0:
            idx_all = idx_by_label_sample
        else:
            idx_all = np.append(idx_all, idx_by_label_sample)
            data_sample = data.iloc[np.sort(idx_all)]
    return data_sample

data1 = sample_data(1000)
data_features = data1.iloc[:, 1:]
data_labels = data1.iloc[:, 0]

knearest = KNeighborsClassifier(n_neighbors=2).fit(data_features, data_labels)
bayes = GaussianNB().fit(data_features, data_labels)

print("knearest accuracy", knearest.score(data_features, data_labels))
print("NB accuracy", bayes.score(data_features, data_labels))
print("knearest cross validation on k = 2", cross_val_score(knearest, data_features, data_labels, cv = 2))
print("NB cross validation on k = 2", cross_val_score(bayes, data_features, data_labels, cv = 2))