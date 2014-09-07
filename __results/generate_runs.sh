#!/bin/bash

# run1: textclusters.csv relevance_zsombor5
../gaboralgorithm/reorder textclusters.csv relevance_zsombor5.csv 2 run1_text_cluster_mixed_relevance 0 > run1_text_cluster_mixed_relevance

# run2: visual_clusters.csv relevance_zsombor5.csv
../gaboralgorithm/reorder visual_clusters.csv relevance_zsombor5.csv 2 run2_vis_cluster_mixed_relevance 0 > run2_vis_cluster_mixed_relevance

# run3: vis_text_mixed_clusters.csv relevance_zsombor5.csv
../gaboralgorithm/reorder vis_text_mixed_clusters.csv relevance_zsombor5.csv 2 run3_mixed_cluster_mixed_relevance 0 > run3_mixed_cluster_mixed_relevance

# run4: vis_text_mixed_clusters.csv relevance_crediblity.csv
../gaboralgorithm/reorder vis_text_mxixed_clusters.csv relevance_crediblity.csv 2 run4_mixed_cluster_cred_relevance 0 > run4_mixed_cluster_cred_relevance

# run5: vis_text_mixed_clusters.csv relevance_zsombor5.csv
../gaboralgorithm/reorder vis_text_mixed_clusters.csv relevance_zsombor5.csv 2 run5_mixed_cluster_mixed_relevance_clust_size 1 > run5_mixed_cluster_mixed_relevance_clust_size