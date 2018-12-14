<?php

$userID = $_POST['sex'];
$season = $_POST['season'];
$top_id_1 = $_POST['top_id_1'];
$top_id_2 = $_POST['top_id_2'];
$top_id_3 = $_POST['top_id_3'];

define("_IP",    "localhost");
define("_PORT",  "45000");

$sock = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
socket_connect($sock, _IP, _PORT);
#echo "CLIENT >> socket connect to "._IP.":"._PORT."\n";

$data = "man"." ".$season." ".$top_id_1." ".$top_id_2." ".$top_id_3;


socket_write($sock, $data, strlen($data));

$output = socket_read($sock, 4096);
#echo $output;

#$result_array = explode(",",$output);
$result_array = explode("\n", $output);


echo json_encode([
	"bottom_id_1" => $result_array[0],
	"bottom_id_2" => $result_array[1],
	"bottom_id_3" => $result_array[2],

	"outer_id_1" => $result_array[3],
	"outer_id_2" => $result_array[4],
	"outer_id_3" => $result_array[5]
]);

socket_close($sock);
?>
