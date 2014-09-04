import pandas as pd
import numpy as np
import sys
from scipy.cluster.vq import kmeans2, whiten
from sklearn.cluster import KMeans
from sklearn import metrics

if len(sys.argv) < 3:
    print("Usage: %s <first csv> <second csv>" % sys.argv[0])
    sys.exit(-1)

csv1 = sys.argv[1]
csv2 = sys.argv[2]

# location id and image id are the index together
f1 = pd.io.parsers.read_csv(csv1, header=None, index_col=[0,1], engine='c', delimiter=";")
f2 = pd.io.parsers.read_csv(csv2, header=None, index_col=[0,1], engine='c', delimiter=";")

# merge the tables by joining indices
#
# file1: loc_id, img_id, clust_id
# file2: loc_id, img_id, clust_id
# merged: loc_id, img_id, clust_id_file1, clust_id_file2
merged = f1.merge(f2, left_index=True, right_index=True)
clust_id_dict = dict()
clust_num_dict = dict()
# the new cluster id is the fields clust_id_file1 and clust_id_file2 together
# the new cluster ids are converted into numbers
for ix, row in merged.iterrows():
    new_clust = str(row[0]) + "_" + str(row[1])
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