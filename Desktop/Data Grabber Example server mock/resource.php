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

if(isset($_POST['filename']))
{
	$jsondata=$_POST['filename'];
	$content = @file_get_contents($jsondata . ".json");
	echo $content;
}
else
{
	echo '{"data":null}';
}
