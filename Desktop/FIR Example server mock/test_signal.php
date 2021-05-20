<?php
/**
 ******************************************************************************
 * @file    FIR Example server mock/test_signal.php
 * @author  Adrian Wojcik
 * @version V1.1
 * @date    17-Apr-2021
 * @brief   Simple IoT server mock: harmonic signal
 ******************************************************************************
 */
 
  header("Content-Type: application/json");
  $x = 2*M_PI*intval($_GET['k'])/10;
  $s = sin(0.1*$x)+sin(0.2*$x)+sin(0.7*$x)+sin(1.0*$x);
  echo $s;
?>