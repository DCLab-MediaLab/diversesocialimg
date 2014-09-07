<!DOCTYPE html>
<html>
    <head>
        <title>Cluster viewer</title>
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">

        <script src="//code.jquery.com/jquery.js"></script>
        <style>
            .panel{
                border-bottom: 1px solid #ccc;
                padding:2px;
            }

            .subpanel{
                float:left;
                margin-right: 20px;
            }

            .clearfix{
                clear:both;
                float:none;
            }
            
            #panel_result img:hover{
                height: auto;
                width:300px;
                position: absolute;
            }
            #panel_result img{
                height: 100px;
                width: 100px;
            }
            
            #panel_result div{
                border:1px solid blue;
                margin-bottom:10px;
                clear:both;
            }
            
            #panel_result span{
                display: block;
                font-size:20px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div id="panel_input" class="panel">
            <div class="subpanel">
                Result txt:<br/>
                <textarea id="text_result"><?php echo(file_get_contents("result-sample.txt")); ?></textarea>
            </div>
            <div class="subpanel">
                Cluster input (locid;imgid;cluster):<br/>
                <textarea id="text_clustercsv"><?php echo(file_get_contents("cluster-sample.csv")); ?></textarea>
            </div>
            <div class="subpanel">
                Location id:<br/>
                <input id="text_locid" type="text" value="31"/>
            </div>
            <div class="subpanel">
                <button id="show_button">Show clusters (result txt ignored)</button>
                <button id="show_result">Show result (cluster file ignored)</button>
            </div>
            <div class="clearfix"></div>
        </div>
        <div id="panel_result" class="panel">
        </div>
        <script src="engine.js"></script>
        <script>
            $(function(){
                
                //if has some predefined data, do it
                if ($("#text_locid").val()!="")
                {
                    showclusters($("#text_clustercsv").val(),$("#text_locid").val());
                }
                
                //bind click
                $("#show_button").click(function(){
                    showclusters($("#text_clustercsv").val(),$("#text_locid").val());
                    });
                    
                $("#show_result").click(function(){
                   showresult($("#text_result").val(),$("#text_locid").val());
                });
            })
        </script>
    </body>
</html>