import pandas as pd
import numpy as np
import sys
from scipy.cluster.vq import kmeans2, whiten
from sklearn.cluster import KMeans
from sklearn import metrics

groupid = sys.argv[1]
groupname = sys.argv[2]

postfixes = ["CM", "CM3x3", "CN", "CN3x3", "CSD", "GLRLM", "GLRLM3x3", "HOG", "LBP", "LBP3x3"]

data = None
for p in postfixes:
    tmp = pd.io.parsers.read_csv("../dataset/%s %s.csv" % (groupname, p), header=None, prefix=p, index_col=0, engine='c')
    if data is not None:
        data = data.merge(tmp, left_index=True, right_index=True)
    else:
        data = tmp

normdata = whiten(data.values)

m = 999
cn = 0
for i in range(6,18):
    kmeans = KMeans(n_clusters=i)
    kmeans.fit(normdata)
    tmpm = metrics.silhouette_score(normdata, kmeans.labels_)
    if m > tmpm:
        m = tmpm
        cn = i

kmeans = KMeans(n_clusters=cn)
kmeans.fit(normdata)

format=np.vstack((data.index, kmeans.labels_)).T

for i,l in format:
    print("%s;%d;%d" % (groupid, i, l))
