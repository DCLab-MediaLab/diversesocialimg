<?php
  $xml=json_decode(json_encode(simplexml_load_string(file_get_contents($argv[1]))),TRUE);
	//yes, I just did that
  foreach($xml["photo"] as $photo)
  {
  echo(str_replace(".",",",$photo["@attributes"]['longitude'].";".$photo["@attributes"]['latitude']."\n"));
  }
?>