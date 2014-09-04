import pandas as pd
import numpy as np
import sys

if len(sys.argv) < 3:
    print("Usage: %s <clusters csv> <relevance csv>" % sys.argv[0])
    sys.exit(-1)

def p_at_n(order, n):


cluster_csv = sys.argv[1]
relevance_csv = sys.argv[2]

clust = pd.io.parsers.read_csv(cluster_csv, header=None, engine='c', delimiter=";")
rel = pd.io.parsers.read_csv(relevance_csv, header=None, engine='c', delimiter=";")

merged = clust.merge(rel, left_index=True, right_index=True)


