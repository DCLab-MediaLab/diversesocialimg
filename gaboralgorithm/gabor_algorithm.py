import pandas as pd
import numpy as np
import sys

def f1score(order, clust_num):
    p = 0
    c = 0
    cnt = 0
    clust_set = set()
    for index, d in order.iterrows():
        cnt += 1
        p += d['relevance']
        if d['relevance'] > 0:
            clust_set.add(d['clustID'])
    if cnt == 0:
        return 0.0
    p = p / cnt
    c = len(clust_set) / float(clust_num)
    return 2 * p * c / (p+c)

def select_next_best(data, order, clust_num):
    # select from the entire set
    score_list = [(index, f1score(order.append(row), clust_num)) for index, row in data.iterrows()]
    if len(score_list) > 0:
        best = max(score_list, key=lambda x: x[1])
        order = order.append(data.ix[best[0]], ignore_index=True)
        data = data.drop([best[0]])

    return data, order

def process_location(data):

#    order = []
#    # select the best item
#    maxidx = data['0_y'].idxmax()
#    order += [data.ix[maxidx][1]]
#    # remove from the list
#    data = data.drop(maxidx)

    clust_num = len(pd.Series(data['clustID'].values.ravel()).unique())

    order = pd.DataFrame()
    cnt = 0
    while not data.empty:
        cnt += 1
        if cnt > 50: break
        data, order = select_next_best(data, order, clust_num)

    return order

if len(sys.argv) < 3:
    print("Usage: %s <clusters csv> <relevance csv>" % sys.argv[0])
    sys.exit(-1)

cluster_csv = sys.argv[1]
relevance_csv = sys.argv[2]

clust = pd.io.parsers.read_csv(cluster_csv, header=None, engine='c', delimiter=";", index_col=[1], names=['locID', 'imgID', 'clustID'])
rel = pd.io.parsers.read_csv(relevance_csv, header=None, engine='c', delimiter=";", index_col=[0], names=['imgID', 'relevance'])

merged = clust.merge(rel, left_index=True, right_index=True)

loc_ids = pd.Series(merged['locID'].values.ravel()).unique()

print(merged)

for loc_id in loc_ids:
    data = merged[merged['locID'] == loc_id]
    order = process_location(data)
    print(order)
    