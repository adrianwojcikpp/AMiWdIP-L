<?php
if(isset($_GET['filename']))
{
	$jsondata=$_GET['filename'];
	$content = @file_get_contents($jsondata . ".json");
	echo $content;
}
else
{
	echo '{"data":null}';
}
