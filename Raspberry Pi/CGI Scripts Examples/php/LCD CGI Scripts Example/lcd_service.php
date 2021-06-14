<?php
/**
 ******************************************************************************
 * @file    lcd_write.php
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version 1.0
 * @date    13 Jun 2021
 * @brief   GCI script for printing controlling system service
 ******************************************************************************
 */
include 'debug_exec.php';
 
$enable = -1;
if(isset($_GET["enable"]))
  $enable = $_GET["enable"];

$cmd = 0; 

if($enable == 1)
  $cmd = "sudo systemctl start lcdsense.service && echo \"STARTED\"";
if($enable == 0)
  $cmd = "sudo systemctl stop lcdsense.service  && echo \"STOPPED\"";

if(is_string($cmd))
{
  $output = null;
  $result = null;
  $error = null;

  debug_exec($cmd, $output, $result, $error);
  echo "Returned with status $result,<br>output:<br>";
  print_r($output);
  echo "<br>and error:<br>";
  print_r($error);
  echo "<br>";
}
 
?>