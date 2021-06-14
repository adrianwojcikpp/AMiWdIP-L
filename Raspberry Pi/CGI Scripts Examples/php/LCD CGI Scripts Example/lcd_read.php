<?php
/**
 ******************************************************************************
 * @file    lcd_read.php
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version 1.0
 * @date    13 Jun 2021
 * @brief   GCI script for reading text from HD44780 LCD
 ******************************************************************************
 */

header('Content-Type: text/plain');
echo file_get_contents("lcd.dat");
 
?>