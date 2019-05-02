import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.cluster import KMeans
from sklearn.manifold import TSNE
from sklearn.cluster import DBSCAN
from sklearn.cluster import AgglomerativeClustering
from sklearn.metrics import accuracy_score 
from statistics import mode
from sklearn import metrics



tsne = TSNE(n_components=2, random_state=0)
#tsne_3d = TSNE(n_components=3, random_state=0)

""" Load data """
data = pd.read_csv('train.csv')

""" Sample data """
for i in range(10):
    idx_by_label = np.array(data.index[data['label']==i].tolist())
    idx_by_label_sample = np.random.choice(idx_by_label, size=100, replace=False)
    if i == 0:
        idx_all = idx_by_label_sample
    else:
        idx_all = np.append(idx_all, idx_by_label_sample)

data_sample = data.iloc[np.sort(idx_all)]

""" Divide data to features and labels """
data_features = data_sample.iloc[:, 1:]
data_labels = data_sample.iloc[:, 0]

points = tsne.fit_transform(data_features)
#points_3d = tsne_3d.fit_transform(data_features)

"plotting function 2d space"
def plot_embedding(x, y, title):
    plt.figure()
    plt.scatter(x[:, 0], x[:, 1], color=plt.cm.Set1(y/10))
    
    plt.xticks([])
    plt.yticks([])
    plt.title(title)
    plt.show()
    
    
"plotting function 2d space"
def plot_embedding_3d(x, y, title):
    plt.figure()
    plt.scatter(x[:, 0], x[:, 1], x[:, 2], color=plt.cm.Set1(y/10))
    
    plt.xticks([])
    plt.yticks([])
    plt.title(title)
    plt.show()

def mapLabels(clust, trainLabel):
    labels = np.zeros_like(clust)
    for i in range(10):
        mask = (clust == i)
        labels[mask] = mode(trainLabel[mask])
    return labels


"kmeans on 2d reduced data"
kmeans = KMeans(n_clusters=10).fit(data_features)
centers = kmeans.cluster_centers_

"kmeans accuracy score"
kpredict = kmeans.predict(data_features)
#print(metrics.accuracy_score(data_labels, kmeans.predict(data_features)))
print("kmeans accuracy score: ", accuracy_score(mapLabels(kmeans.labels_, data_labels), data_labels))


"plotting kmeans"
plot_embedding(points, kmeans.labels_, 'kmeans 2d')
#plot_embedding_3d(points_3d, kmeans.labels_, 'kmeans 3d')

"dbscan"
db = DBSCAN(eps=1400, min_samples = 5).fit(data_features)
core_samples_mask = np.zeros_like(db.labels_, dtype=bool)
core_samples_mask[db.core_sample_indices_] = True
labels_pred = db.labels_
n_clusters_ = len(set(labels_pred)) - (1 if -1 in labels_pred else 0)
print(n_clusters_)

"dbscan accuracy"
print("dbscan accuracy score", metrics.accuracy_score(data_labels, db.labels_))


"plotting dbscan"
plot_embedding(points, db.labels_, 'dbscan 2d')
#plot_embedding_3d(points_3d, db.labels_, 'dbscan 3d')

'''
unique_labels = set(labels_pred)
colors = [plt.cm.Spectral(each)
          for each in np.linspace(0, 1, len(unique_labels))]
for k, col in zip(unique_labels, colors):
    if k == -1:
        # Black used for noise.
        col = [0, 0, 0, 1]

    class_member_mask = (labels_pred == k)

    xy = points[class_member_mask & core_samples_mask]
    plt2 = plt.plot(xy[:, 0], xy[:, 1], 'o', markerfacecolor=tuple(col),
             markeredgecolor='k', markersize=7)

    xy = points[class_member_mask & ~core_samples_mask]
    plt2 = plt.plot(xy[:, 0], xy[:, 1], 'o', markerfacecolor=tuple(col),
             markeredgecolor='k', markersize=3)

'''
"hiearchical clustering"
hcluster = AgglomerativeClustering(n_clusters = 10, linkage='ward').fit(data_features)
"plotting hiearchical clustering"
plot_embedding(points, hcluster.labels_, 'hiearchial 2d')
#plot_embedding_3d(points_3d, hcluster.labels_, 'hiearchial 3d')

"hiearchical accuracy"
#print(metrics.accuracy_score(data_labels, hcluster.labels_))
print("hiearchical accuracy score: ", accuracy_score(mapLabels(hcluster.labels_, data_labels), data_labels))


"kmeans outliers"
d=[]
ktransform = kmeans.transform(data_features)

for i in range(0, 10):
    Indicies = np.array(kmeans.labels_==i).tolist()
    for k in range(len(Indicies)):
        if Indicies[k]==True:
            d.append(ktransform[k])
    d = np.sort(d)
    print('top 5 outliers for ', i, ': ')
    for j in range(0, 5):
        print(d[j])
    d = []
    