<?php
/**
 ******************************************************************************
 * @file    MVVM ListView Example server mock/measurements.php
 * @author  Adrian Wojcik
 * @version V1.0
 * @date    15-Apr-2021
 * @brief   Simple IoT server mock: measurements data with REST API
 ******************************************************************************
 */

function get_measurement($id) {
  switch ($id) {
    case 0:
        return [ "name" => "random", "value" => rand(0,100)/100.0, "unit" => "-" ];
    case 1:
        return [ "name" => "temperature", "value" => rand(0,10000)/100.0, "unit" => "C" ];
    case 2:
        return [ "name" => "pressure", "value" => rand(98000,102000)/100.0, "unit" => "hPa" ];
    case 3:
        return [ "name" => "humidity", "value" => rand(0,10000)/100.0, "unit" => "%" ];
    case 4:
        return [ "name" => "roll", "value" => rand(-18000,18000)/100.0, "unit" => "deg" ];
    case 5:
        return [ "name" => "pitch ", "value" => rand(-9000,9000)/100.0, "unit" => "deg" ];
    case 6:
        return [ "name" => "yaw ", "value" => rand(-18000,18000)/100.0, "unit" => "deg" ];
    default: 
        return null;
  }
}
 
$id = 'ALL';
if(isset($_GET['id'])) {
  $id = json_decode($_GET['id']);
}

$response = array();
if(is_array($id)) {
  foreach($id as $i)
    array_push($response, get_measurement($i));
}
else if(is_integer($id)) {
  array_push($response, get_measurement($id));
}
else if($id == 'ALL') {
  for($i = 0; $i <= 6; $i++)
    array_push($response, get_measurement($i));
}

//var_dump($id, $response);  

header('Content-Type: application/json');
echo json_encode($response);

?>