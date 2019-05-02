import numpy as np
import pandas as pd
from sklearn import tree
from sklearn.neural_network import MLPClassifier
from sklearn.model_selection import cross_val_score

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

all_features = data.iloc[:, 1:]
all_labels = data.iloc[:, 0]

d_tree = tree.DecisionTreeClassifier()
d_tree = d_tree.fit(data_features, data_labels)

d_tree1 = tree.DecisionTreeClassifier()
d_tree1 = d_tree1.fit(all_features, all_labels)

d_tree2 = tree.DecisionTreeClassifier(max_depth = 5)
d_tree2 = d_tree2.fit(all_features, all_labels)

print("d tree accuracy from task 1 on test data: ", d_tree.score(data_features, data_labels))
print("d tree accuracy from task 1 on all data: ", d_tree.score(all_features, all_labels))
print("d tree accuracy from task 2 on all data: ", d_tree1.score(all_features, all_labels))
print("d tree accuracy from task 2 on all data with depth = 5: ", d_tree2.score(all_features, all_labels))
print("d tree cross validation on k = 2", cross_val_score(d_tree, data_features, data_labels, cv = 2))
print("d tree cross validation on k = 4", cross_val_score(d_tree, data_features, data_labels, cv = 4))

print("--------------")

mlp = MLPClassifier()
mlp.fit(data_features, data_labels)

mlp1 = MLPClassifier()
mlp1.fit(all_features, all_labels)

print("mlp accuracy from task 1 on test data", mlp.score(data_features, data_labels))
print("mlp accuracy from task 1 on all data", mlp.score(all_features, all_labels))
print("mlp accuracy from task 2 on all data", mlp1.score(all_features, all_labels))
print("mlp cross validation on k = 2", cross_val_score(mlp, data_features, data_labels, cv = 2))
print("mlp tree cross validation on k = 4", cross_val_score(mlp, data_features, data_labels, cv = 4))

