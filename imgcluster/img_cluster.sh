#!/bin/bash

if [ $# -lt 3 ]; then
    echo "Use from the 'imgcluster' dir like this: $0 <img set id> <malna username> <malna password>"
    exit -1
fi

if [ ! -f ../dataset/testset_topics.xml ]; then
    echo "please place testset_topics.xml file in ../dataset/ dir."
    exit -2
fi

name=$(xsltproc --param id $1 getNameById.xsl ../dataset/testset_topics.xml)

malna_user=$2
malna_pass=$3

base="http://$malna_user:$malna_pass@malna.tmit.bme.hu/mm/diversesocialimg/dataset/testset/descvis/img/"
postfixes="CM CM3x3 CN CN3x3 CSD GLRLM GLRLM3x3 HOG LBP LBP3x3"
for p in $postfixes; do
    wget -nc -P ../dataset/ --quiet -o log "$base/$name $p.csv"
done

python cluster.py $1 $name