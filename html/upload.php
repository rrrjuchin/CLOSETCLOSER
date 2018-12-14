<?php
// requires php5
set_time_limit(0);
define('./images/', 'images/');
$img = $_POST['image'];
$img = str_replace('data:image/png;base64,', '', $img);
$img = str_replace(' ', '+', $img);
$data = base64_decode($img);
$file = ORIGIN.'.jpg';
#$success = file_put_contents($file, $data);
#print $success ? $file : 'Unable to save the file.';

if(file_put_contents($file, $data)){
	echo exec("php ./client.php");
} else {
	echo json_encode([
		"Message" => "there was an error uploading your file.",
		"Status" => "Error"
	]);
}
?>

