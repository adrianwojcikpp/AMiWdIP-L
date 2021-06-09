<?php
error_reporting(E_ALL);

function getresource($id) {
    // create a TCP/IP socket
    $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
    if ($socket === false) {
        echo "socket_create() failed: reason: " . socket_strerror(socket_last_error()) . "\n";
    }
    // connect the socket to the 'sense_hat_service' port: 10000
    $result = socket_connect($socket, 'localhost', 10000);
    if ($result === false) {
        echo "socket_connect() failed.\nReason: ($result) " . socket_strerror(socket_last_error($socket)) . "\n";
    }
    // send command
    $cmd = "get_" . $id;
    socket_write($socket, $cmd, strlen($cmd));
    // receive resource
    $res = socket_read($socket, 256);
    socket_close($socket);
    return $res;
}

header('Content-Type: application/json');

if(isset($_GET['id'])) {
    // resources IDs
    $ids = array('env', 'rpy');
    // check selected ID
    $idno = array_search($_GET['id'], $ids);
    if($idno === false) {
        echo '[]';
    }
    else { 
        $id = $ids[$idno];
        echo getresource($id);
    }
}
else {
    echo '[]';
}
?>