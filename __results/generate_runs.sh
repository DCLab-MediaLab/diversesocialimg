#!/bin/bash

prefix="me14div_DCLab2014_"
p="../gaboralgorithm/reorder"

# run1: textclusters.csv relevance_zsombor5
runnum="run1"
name="TextClusterMixedRelevance"
$p textclusters.csv relevance_zsombor5.csv 2 ${runnum}_${name} 0 > $prefix${runnum}_${name}.txt

# run2: visual_clusters.csv relevance_zsombor5.csv
runnum="run2"
name="VisClusterMixedRelevance"
$p visual_clusters.csv relevance_zsombor5.csv 2 ${runnum}_${name} 0 > $prefix${runnum}_${name}.txt

# run3: vis_text_mixed_clusters.csv relevance_zsombor5.csv
runnum="run3"
name="VisTextClusterMixedRelevance"
$p vis_text_mixed_clusters.csv relevance_zsombor5.csv 2 ${runnum}_${name} 0 > $prefix${runnum}_${name}.txt

# run4: vis_text_mixed_clusters.csv relevance_crediblity.csv
runnum="run4"
name="VisTextClusterCredRelevance"
$p vis_text_mixed_clusters.csv relevance_crediblity.csv 2 ${runnum}_${name} 0 > $prefix${runnum}_${name}.txt

# run5: vis_text_mixed_clusters.csv relevance_zsombor5.csv
runnum="run5"
name="VisTextClusterMixedRelevance2"
$p vis_text_mixed_clusters.csv relevance_zsombor5.csv 2 ${runnum}_${name} 0 > $prefix${runnum}_${name}.txt