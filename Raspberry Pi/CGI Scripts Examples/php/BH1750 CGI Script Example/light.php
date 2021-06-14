<?php

header('Content-Type: application/json');
$filename = "/home/pi/i2c_examples/light";
$handle = fopen($filename, "r");
$contents = fread($handle, filesize($filename));
fclose($handle);

$array = unpack("fdata", $contents);
echo json_encode($array);

?>