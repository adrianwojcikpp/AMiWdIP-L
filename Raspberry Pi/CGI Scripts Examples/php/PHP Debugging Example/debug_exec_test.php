<?php
/**
 ******************************************************************************
 * @file    PHP Debugging Example/debug_exec_test.php
 * @author  Adrian Wojcik
 * @version V1.1
 * @date    23-Apr-2021
 * @brief   Test and use case of PHP function for system call execution 
 *          with standard error output
 ******************************************************************************
 */

include 'debug_exec.php';

$cli_app = "python cli_app.py";
$cli_app_with_error = "python cli_app_with_error.py";

header("Content-Type: text/plain");

echo "--- Standard PHP 'exec' function: ---------------------------\n";
echo "-- 'cli_app.py' call:";

$output = null;
$result = null;

exec($cli_app, $output, $result);
echo "\nReturned with status $result and output:\n";
print_r($output);

echo "\n\n";

echo "-- 'cli_app_with_error.py' call:";

$output = null;
$result = null;

exec($cli_app_with_error, $output, $result);
echo "\nReturned with status $result and output:\n";
print_r($output);

echo "\n\n";

echo "--- Custom 'debug_exec' function: ---------------------------\n";
echo "-- 'cli_app.py' call:";

$output = null;
$result = null;
$error = null;

debug_exec($cli_app, $output, $result, $error);
echo "\nReturned with status $result, output:\n";
print_r($output);
echo "\nand error:\n";
print_r($error);

echo "\n\n";

echo "-- 'cli_app_with_error.py' call:";

$output = null;
$result = null;
$error = null;

debug_exec($cli_app_with_error, $output, $result, $error);
echo "\nReturned with status $result, output:\n";
print_r($output);
echo "\nand error:\n";
print_r($error);
echo "\n";

?>