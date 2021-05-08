<?php

/**
 ******************************************************************************
 * @file    LED Display Control Example server mock/led_display.php
 * @author  Adrian Wojcik
 * @version V1.1
 * @date    09-May-2020
 * @brief   Simple IoT server mock: writing control data to test file
 ******************************************************************************
 */

include 'led_display_model.php';
$dispControlFile = 'led_display.json';

switch($_SERVER['REQUEST_METHOD']) {
  
  case "GET": 
  // Parse input data and save as JSON array to text file
    $disp = new LedDisplay();
    file_put_contents($dispControlFile, $disp->postDataToJsonArray($_GET));
    echo file_get_contents($dispControlFile);
    break;
  
  case "POST": 
  // Parse input data and save as JSON array to text file
    $disp = new LedDisplay();
    file_put_contents($dispControlFile, $disp->postDataToJsonArray($_POST));
    echo "ACK";
    break;

  case "PUT": 
  // Save input JSON array directly to text file
    file_put_contents($dispControlFile, file_get_contents('php://input'));
    echo '["ACK"]';
    break;
}

?>