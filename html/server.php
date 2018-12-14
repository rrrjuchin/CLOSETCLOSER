<?php
define("_IP",    "localhost");
define("_PORT",  "65000");

$sSock      = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
socket_bind($sSock, _IP, _PORT);
socket_listen($sSock);
while($cSock = socket_accept($sSock))
{
    socket_getpeername($cSock, $addr, $port);
    echo "SERVER >> client connected $addr:$port \n";
    $command = 'python3 /home/ubuntu/project/inception-v3/predict.py /var/www/html/ORIGIN.jpg';
    $output = shell_exec($command);

    $bottom = 0;
    if(!strcmp($output, "contton pants\n")){
	    $bottom = 1;
    }else if(!strcmp($output, "jeans\n")){
	    $bottom = 1;
    }else if(!strcmp($output, "long skirts\n")){
	    $bottom = 1;
    }else if(!strcmp($output, "short pants\n")){
	    $bottom = 1;
    }else if(!strcmp($output, "short jeans\n")){
	    $bottom = 1;
    }else if(!strcmp($output, "short skirts\n")){
	    $bottom = 1;
    }else if(!strcmp($output, "slacks\n")){
	    $bottom = 1;
    }


    #echo 'testest : '.$output.'+'.$bottom;

    # color test
    $command_2 = 'python3 /home/ubuntu/color_recognition/src/color_classification_image.py '.$bottom;
    $output_2 = shell_exec($command_2);

    #$result = $output + $output_2;

    #$test = "TEST";
    $result = $output.$output_2;

    print($result);
    socket_write($cSock, $result);
    socket_close($cSock);
    #echo "SERVER >> client Close.\n";
}
?>
