<?php
/**
 ******************************************************************************
 * @file    MATLAB GET Example sever mock/sine.php
 * @author  Adrian Wojcik
 * @version V1.2
 * @date    09-May-2020
 * @brief   Simple MATLAB web client example: mock server script
 ******************************************************************************
 */
  header('Content-Type: application/json');
  $file_name = "sine.dat";
  echo file_get_contents($file_name);
?>