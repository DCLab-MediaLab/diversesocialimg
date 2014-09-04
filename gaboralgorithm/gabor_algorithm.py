import pandas as pd
import numpy as np
import sys

if len(sys.argv) < 3:
    print("Usage: %s <clusters csv> <relevance csv>" % sys.argv[0])
    sys.exit(-1)

cluster_csv = sys.argv[1]
relevance_csv = sys.argv[2]

clust = pd.io.parsers.read_csv(cluster_csv, header=None, engine='c', delimiter=";")
rel = pd.io.parsers.read_csv(relevance_csv, header=None, engine='c', delimiter=";")

merged = clust.merge(rel, left_index=True, right_index=True)

loc_ids = pd.Series(merged['0_x'].values.ravel()).unique()

for loc_id in loc_ids:
    data = merged[merged['0_x'] == loc_id]
    order = []
    # select the best item
    maxidx = data['0_y'].idxmax()
    order += [data.ix[maxidx][1]]
    # remove from the list
    data = data.drop(maxidx)

    # TODO: select the next best
    # for 

    print(order)
    