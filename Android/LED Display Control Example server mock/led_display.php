<?php
/**
 ******************************************************************************
 * @file    Data Grabber Example server mock/resource.php
 * @author  Adrian Wojcik
 * @version V1.0
 * @date    09-Apr-2020
 * @brief   Simple IoT server mock: writing control data to test file
 ******************************************************************************
 */
 
function ledIndexToTag($x, $y) {
	// TODO: add validation
	return "LED" .$x .$y;
}

$ledDisplay = array();
$ledDispleyTestFile = 'led_dipslay_test_file.json';

$n = 0;

for ($i = 0; $i < 8; $i++) {
	for ($j = 0; $j < 8; $j++) {
		$ledTag = ledIndexToTag($i, $j);
		if(isset($_POST[$ledTag])){
			$ledDisplay[$n] = json_decode($_POST[$ledTag]);
			$n=$n+1;
		}
	}
}

/* DEBUG print_r($ledDisplay); */
$ledDisplayJson = json_encode($ledDisplay);
file_put_contents($ledDispleyTestFile, $ledDisplayJson);

echo "ACK";

?>