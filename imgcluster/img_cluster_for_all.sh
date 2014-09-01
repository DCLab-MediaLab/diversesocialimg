#!/bin/bash
if [ $# -lt 2 ]; then
    echo "use like this: $0 <malna_user> <malna_pass>"
    exit -1
fi

malna_user=$1
malna_pass=$2
for i in $(seq 31 153); do ./img_cluster.sh $i $malna_user $malna_pass >> imgclust.csv; done
