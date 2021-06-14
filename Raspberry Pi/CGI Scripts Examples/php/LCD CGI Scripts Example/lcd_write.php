<?php
/**
 ******************************************************************************
 * @file    lcd_write.php
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version 1.0
 * @date    13 Jun 2021
 * @brief   GCI script for printing text on HD44780 LCD
 ******************************************************************************
 */
include 'debug_exec.php';
 
$lines = ["", ""];
if(isset($_GET["line1"]))
  $lines[0] = $_GET["line1"];
if(isset($_GET["line2"]))
  $lines[1] = $_GET["line2"];

$cmd = "/home/pi/lcd_examples/lcd_example \"$lines[0]\" \"$lines[1]\" && echo \"OK\"";

$output = null;
$result = null;
$error = null;

debug_exec($cmd, $output, $result, $error);
echo "Returned with status $result,<br>output:<br>";
print_r($output);
echo "<br>and error:<br>";
print_r($error);
echo "<br>";
 
 
?>