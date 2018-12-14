<?php
define("_IP",    "localhost");
define("_PORT",  "65000");

$sock      = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
socket_connect($sock, _IP, _PORT);
#echo "CLIENT >> socket connect to "._IP.":"._PORT."\n";
$output = socket_read($sock, 4096);
#echo $output;

$result_array = explode("\n",$output);

if($result_array[0] == "blouse"){
	$result_array[0] = "Blouse";
}
else if($result_array[0] == "long shirt"){
	$result_array[0] = "Long_shirts";
}
else if($result_array[0] == "long sleeve"){
	$result_array[0] = "Long_Sleeve";
}
else if($result_array[0] == "sleeveless"){
	$result_array[0] = "Sleeveless";
}
else if($result_array[0] == "short shirts"){
	$result_array[0] = "Short_Shirt";
}
else if($result_array[0] == "short sleeve"){
	$result_array[0] = "Short_Sleeve";
}
else if($result_array[0] == "sweater"){
	$result_array[0] = "Sweater";
}
else if($result_array[0] == "blazer"){
	$result_array[0] = "Blazer";
}
else if($result_array[0] == "blouson"){
	$result_array[0] = "Blouson";
}
else if($result_array[0] == "coat"){
	$result_array[0] = "Coat";
}
else if($result_array[0] == "hoodie"){
	$result_array[0] = "Hoodie";
}
else if($result_array[0] == "padding"){
	$result_array[0] = "Padding";
}
else if($result_array[0] == "cardigan"){
	$result_array[0] = "Cardigan";
}
else if($result_array[0] == "cotton pants"){
	$result_array[0] = "Cotton_Pants";
}
else if($result_array[0] == "jeans"){
	$result_array[0] = "Jeans";
}
else if($result_array[0] == "long skirts"){
	$result_array[0] = "Long_Skirts";
}
else if($result_array[0] == "short pants"){
	$result_array[0] = "Short_Pants";
}
else if($result_array[0] == "short jeans"){
	$result_array[0] = "Short_Jeans";
}
else if($result_array[0] == "short skirts"){
	$result_array[0] = "Short_Skirts";
}
else if($result_array[0] == "slacks"){
	$result_array[0] = "Slacks";
}

if($result_array[1] == "dark blue"){
	$result_array[1] = "dark_blue";
}else if($result_array[1] == "olive green"){
	$result_array[1] = "olive_green";
}

echo json_encode([
	"Category" => $result_array[0],
	"Color" => $result_array[1]
]);

socket_close($sock);
#echo "CLIENT >> socket closed.\n";
?>
