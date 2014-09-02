import pandas as pd
import numpy as np
import sys
from scipy.cluster.vq import kmeans2, whiten
from sklearn.cluster import KMeans
from sklearn import metrics

csv1 = sys.argv[1]
csv2 = sys.argv[2]

f1 = pd.io.parsers.read_csv(csv1, header=None, index_col=[0,1], engine='c', delimiter=";")
f2 = pd.io.parsers.read_csv(csv2, header=None, index_col=[0,1], engine='c', delimiter=";")

merged = f1.merge(f2, left_index=True, right_index=True)
clust_id_dict = dict()
clust_num_dict = dict()
for ix, row in merged.iterrows():
    new_clust = row[0] + "_" + row[1]
    key = (ix[0], new_clust)

    if key in clust_id_dict:
        pass
    else:
        if ix[0] in clust_num_dict:
            clust_num_dict[ix[0]] += 1
        else:
            clust_num_dict[ix[0]] = 0
        clust_id_dict[key] = clust_num_dict[ix[0]]

    print("%s;%s;%s" % (ix[0], ix[1], clust_id_dict[key]))