<?php
$loc = array(
    /*
    1 => "angel_of_the_north",
    2 => "big_ben",
    3 => "hearst_castle",
    4 => "la_madeleine",
    5 => "pont_alexandre_iii",
    6 => "neues_museum",
    7 => "montezuma_castle",
    8 => "cn_tower",
    9 => "acropolis_athens",
    10 => "agra_fort",
    11 => "albert_memorial",
    12 => "altes_museum",
    13 => "amiens_cathedral",
    14 => "angkor_wat",
    15 => "ara_pacis",
    16 => "arc_de_triomphe",
    17 => "aztec_ruins",
    18 => "berlin_cathedral",
    19 => "bok_tower_gardens",
    20 => "brandenburg_gate",
    21 => "cabrillo",
    22 => "casa_batllo",
    23 => "casa_rosada",
    24 => "castillo_de_san_marcos",
    25 => "chartres_cathedral",
    26 => "chichen_itza",
    27 => "christ_the_redeemer_rio",
    28 => "civic_center_san_francisco",
    29 => "cologne_cathedral",
    30 => "colosseum",*/
    31 => "doge_s_palace",
    32 => "eiffel_tower",
    33 => "golden_gate_bridge",
    34 => "grand_palais",
    35 => "guggenheim_museum_bilbao",
    36 => "hagia_sophia_istanbul",
    37 => "hoover_dam",
    38 => "humayun_tomb",
    39 => "iguazu_falls",
    40 => "iolani_palace",
    41 => "la_mezquita",
    42 => "la_rambla_barcelona",
    43 => "la_sainte_chapelle",
    44 => "liberty_memorial",
    45 => "lincoln_home",
    46 => "machu_picchu",
    47 => "marble_arch_london",
    48 => "milan_cathedral",
    49 => "mole_antonelliana",
    50 => "mont_saint_michel",
    51 => "moulin_rouge",
    52 => "nelson_s_column",
    53 => "notre_dame_de_paris",
    54 => "obelisco",
    55 => "piccadilly_circus",
    56 => "place_de_la_concorde",
    57 => "place_de_la_republique",
    58 => "place_des_vosges",
    59 => "pont_du_gard",
    60 => "pont_neuf",
    61 => "ponte_vecchio",
    62 => "puerta_de_alcala",
    63 => "puerta_del_sol",
    64 => "red_fort",
    65 => "reims_cathedral",
    66 => "rialto_bridge",
    67 => "roman_forum_rome",
    68 => "sacre_coeur",
    69 => "sagrada_familia",
    70 => "santa_maria_novella",
    71 => "schonbrunn_palace",
    72 => "seville_cathedral",
    73 => "spanish_steps",
    74 => "st__basil_cathedral",
    75 => "statue_of_liberty",
    76 => "stonehenge",
    77 => "summer_palace_beijing",
    78 => "sydney_opera_house",
    79 => "teotihuacan",
    80 => "terracotta_army",
    81 => "the_blue_mosque",
    82 => "the_henry_ford",
    83 => "taj_mahal",
    84 => "tour_montparnasse",
    85 => "tower_bridge_london",
    86 => "trafalgar_square",
    87 => "leaning_tower_of_pisa",
    88 => "hermitage_museum_saint_petersburg",
    89 => "jantar_mantar_jaipur",
    90 => "lake_titicaca_peru",
    91 => "the_great_wall_of_china",
    92 => "grande_arche_paris",
    93 => "el_angel_mexico",
    94 => "bibliotheque_nationale_de_france",
    95 => "kronborg_castle",
    96 => "ajanta_caves",
    97 => "atomium",
    98 => "ellora_caves",
    99 => "potala_palace",
    100 => "jokhang",
    101 => "rila_monastery",
    102 => "louvre_pyramid",
    103 => "brooklyn_bridge",
    104 => "tower_of_london",
    105 => "british_museum_london",
    106 => "buckingham_palace",
    107 => "charles_bridge_prague",
    108 => "champs_elysees_paris",
    109 => "chrysler_building",
    110 => "moma_new_york",
    111 => "grand_central_terminal",
    112 => "houses_of_parliament",
    113 => "alexanderplatz_berlin",
    114 => "centre_pompidou",
    115 => "sydney_harbour_bridge",
    116 => "pier_39",
    117 => "coit_tower",
    118 => "st_paul_s_cathedral_london",
    119 => "cloud_gate",
    120 => "musee_d_orsay",
    121 => "pike_place_market",
    122 => "royal_palace_madrid",
    123 => "sony_center_berlin",
    124 => "red_square_moscow",
    125 => "prague_castle",
    126 => "musee_du_louvre",
    127 => "millennium_bridge_london",
    128 => "casa_mila",
    129 => "national_gallery_london",
    130 => "national_mall",
    131 => "london_city_hall",
    132 => "holocaust_memorial",
    133 => "columbus_circle",
    134 => "siegessaeule",
    135 => "jardin_du_luxembourg",
    136 => "merlion_singapore",
    137 => "harrods_london",
    138 => "metropolitan_museum_of_art_new_york",
    139 => "new_york_stock_exchange",
    140 => "stephansdom_vienna",
    141 => "hms_belfast",
    142 => "leicester_square",
    143 => "ciudad_de_las_artes_y_las_ciencias",
    144 => "topkapi_palace",
    145 => "royal_albert_hall",
    146 => "st_vitus_cathedral",
    147 => "castello_sforzesco",
    148 => "rijksmuseum_amsterdam",
    149 => "dam_square",
    150 => "rathaus_vienna",
    151 => "bath_abbey",
    152 => "salisbury_cathedral",
    153 => "templo_de_debod");

$usercred=array();

function getUserCred($user){
    global $usercred;
    
    if (!isset($usercred[$user]))
    {
        if (file_exists("../dataset/devset/desccred/".$user.".xml"))
        {
            $x=json_decode(json_encode(simplexml_load_string(file_get_contents("../dataset/devset/desccred/".$user.".xml"))),TRUE);
            $usercred[$user]=$x["credibilityDescriptors"]["visualScore"];
        }else if (file_exists("../dataset/testset/desccred/".$user.".xml"))
        {
            $x=json_decode(json_encode(simplexml_load_string(file_get_contents("../dataset/testset/desccred/".$user.".xml"))),TRUE);
            $usercred[$user]=$x["credibilityDescriptors"]["visualScore"];
        }else{
            echo("boo");
            die();
        }
    }
    
    //
    if ($usercred[$user]=="NULL"){
        $usercred[$user]="0";
    }
    
    return $usercred[$user];
}

$relevance=null;

//uncomment this if you need "clear" result
$relevance=explode("\n",file_get_contents("relevance_avg.csv"));

foreach($loc as $l1)
{
    //grab location data
    $d=json_decode(json_encode(simplexml_load_string(file_get_contents("../dataset/testset/xml/".$l1.".xml"))),TRUE);
    
    foreach($d["photo"] as $photo)
    {
        
        $c=getUserCred($photo["@attributes"]['userid']);
        
        if (!is_null($relevance))
        {
            if (isset($relevance[$photo["@attributes"]["rank"]]))
            {
                $c=(float)$c*(float)$relevance[$photo["@attributes"]["rank"]];
            }else{
                $c=(float)$c*(float)0.5;
            }
        }
        
        echo($photo["@attributes"]['id'].";".$c."\n");
    }
}

?>