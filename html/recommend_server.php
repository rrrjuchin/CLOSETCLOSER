<?php
define("_IP",    "localhost");
define("_PORT",  "35000");

$sSock      = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
socket_bind($sSock, _IP, _PORT);
socket_listen($sSock);
while($cSock = socket_accept($sSock))
{
	$data = socket_read($cSock, 2048);
	socket_getpeername($cSock, $addr, $port);
	#echo "SERVER >> client connected $addr:$port \n";
	$command = 'python3 /home/ubuntu/recommendation/rule.py'." ".$data;

	$data = shell_exec($command);

	print($data);

	#print($result);
	socket_write($cSock, $data);
	socket_close($cSock);
	#echo "SERVER >> client Close.\n";
}
?>
