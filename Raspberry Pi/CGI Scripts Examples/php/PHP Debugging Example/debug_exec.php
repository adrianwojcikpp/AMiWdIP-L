<?php
/**
 ******************************************************************************
 * @file    PHP Debugging Example/debug_exec.php
 * @author  Adrian Wojcik
 * @version V1.1
 * @date    23-Apr-2021
 * @brief   Example of PHP function for system call execution with standard 
 *          error output
 ******************************************************************************
 */
 
/**
 * @brief System call execution with additional error output
 * @param[in]  cmd     The command that will be executed. 
 * @param[out] stdout  Returns a string with process output or empty array 
 *                     on failure. 
 * @param[out] result  Returns the termination status of the process that 
 *                     was run. In case of an error then -1 is returned. 
 * @param[out] stderr  Returns a string with process error message or 
 *                     empty array on success.
 */
function debug_exec($cmd, &$stdout=null, &$result_code=null, &$stderr=null) {
  
  $proc = proc_open($cmd,[
    1 => ['pipe','w'],
    2 => ['pipe','w'],
  ], $pipes);
  
  $stdout = stream_get_contents($pipes[1]);
  fclose($pipes[1]);
  $stdout = explode(PHP_EOL, $stdout);
  array_pop($stdout);
  
  $stderr = stream_get_contents($pipes[2]);
  fclose($pipes[2]);
  $stderr = explode(PHP_EOL, $stderr);
  array_pop($stderr);
  
  $result_code = proc_close($proc);
}

?>