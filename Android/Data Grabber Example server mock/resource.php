<?php
/**
 ******************************************************************************
 * @file    Data Grabber Example server mock/resource.php
 * @author  Adrian Wojcik
 * @version V1.0
 * @date    09-Apr-2020
 * @brief   Simple IoT server mock with RESTful API
 ******************************************************************************
 */

header("Content-Type: application/json");

$id = null;
if(isset($_GET['id']))
  $id = $_GET['id'];

if($id != null) {
  $sensor_data = [ "data" => rand(0,100)/100.0 ];
}
else {
  $sensor_data = [ "data" => null ];
}

echo json_encode($sensor_data);