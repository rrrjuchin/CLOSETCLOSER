<?php

#$command_test = '. /var/www/html/test/bin/activate';
#shell_exec($command_test);

#echo(shell_exec('python --version 2>&1'));

$command = 'python3 /var/www/html/ptest.py 2>&1';
$output = shell_exec($command);

#echo "php hellow\n";
echo $output;

#echo json_encode([
#	"TEST" => $output
#]);


?>
