<?php
//generate a sample cluster csv
/*
$xml=json_decode(json_encode(simplexml_load_string(file_get_contents("../dataset/testset/testset_topics.xml"))),TRUE);

foreach($xml['topic'] as $onetopic)
{
    $locinfo=json_decode(json_encode(simplexml_load_string(file_get_contents("../dataset/testset/xml/".$onetopic['title'].".xml"))),TRUE);
    foreach($locinfo['photo'] as $onepic)
    {
        echo($onetopic['number'].";".$onepic["@attributes"]['id'].";");

        //fake cluster
        echo(floor($onepic["@attributes"]['rank']/20));

        echo("\n");
    }
}
*/

//generate cluster id -> cluster name list
$xml=json_decode(json_encode(simplexml_load_string(file_get_contents("../dataset/devset/devset_topics.xml"))),TRUE);
foreach($xml['topic'] as $onetopic)
{
    echo($onetopic['number']." => \"".$onetopic['title']."\",\n");
}
$xml=json_decode(json_encode(simplexml_load_string(file_get_contents("../dataset/testset/testset_topics.xml"))),TRUE);
foreach($xml['topic'] as $onetopic)
{
    echo($onetopic['number']." => \"".$onetopic['title']."\",\n");
}
?>