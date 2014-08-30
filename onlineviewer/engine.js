function showclusters(clustercsv,locid){
    $("#panel_result").html("");
    
    //parse csv (and filter by locid)
    var clusters=[];
    $.each(clustercsv.split("\n"),function(i,line){
        var items=line.split(";");
        
        //only parse full lines
        if (items.length!=3)
            return;
        
        //filter other locations
        if (items[0]!=locid)
            return;
        
        //create empty cluster
        if (!clusters[items[2]])
        {
            clusters[items[2]]=[];
        }
        
        clusters[items[2]].push(items[1]);
    });
    
    //create claster doms
    $.each(clusters,function(id,pics){
        var cdom=$("<div class='cluster'>");
        cdom.append($("<span></span>").text(id));
        
        $.each(pics,function(i,pic){
            
            cdom.append("<img src='pic.php?c="+locid+"&id="+pic+"'/>");
        });
        
        $("#panel_result").append(cdom);
    });
}