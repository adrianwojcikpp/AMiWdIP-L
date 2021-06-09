#!/usr/bin/python
import socket
import random as rnd
#from sense_hat import SenseHat

#sense = SenseHat()

# Create a TCP/IP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Bind the socket to the port
server_address = ('localhost', 10000)
print('starting up on {} port {}'.format(*server_address))
sock.bind(server_address)

# Listen for incoming connections
sock.listen(1)

while True:
    # Wait for a connection
    #print('waiting for a connection')
    connection, client_address = sock.accept()
    try:
        #print('connection from', client_address)

        while True:
            cmd = connection.recv(8)
            if cmd == b'get_env':
                t = 32.123
                p = 1013.12
                h = 41.21321
                data = ('[{"name":"temperature","value":' + str(t) + ',"unit":"C"},' + 
                         '{"name":"pressure","value":' + str(p) + ',"unit":"hPa"},'  +
                         '{"name":"humidity","value":' + str(h) + ',"unit":"%"},' +
                         '{"name":"random","value":' + str(rnd.random()) + ',"unit":"-"}]')
                msg = data.encode('utf-8') 
                connection.sendall(msg)
            if cmd == b'get_rpy':
                r = 312.123
                p = 60.123
                y = 123.421
                data = ('[{"name":"roll","value":' + str(r) + ',"unit":"C"},' + 
                         '{"name":"pith","value":' + str(p) + ',"unit":"hPa"},'  +
                         '{"name":"yaw","value":' + str(y) + ',"unit":"%"},' +
                         '{"name":"random","value":' + str(rnd.random()) + ',"unit":"-"}]')
                msg = data.encode('utf-8') 
                connection.sendall(msg)
            else:
                break

    finally:
        # Clean up the connection
        connection.close()
