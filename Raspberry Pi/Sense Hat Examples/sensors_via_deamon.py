#!/usr/bin/python
import cgi
#import cgitb; cgitb.enable()  # for troubleshooting
import socket
import json

def getresource(id):
    # create a TCP/IP socket
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # connect the socket to the 'sense_hat_service' port: 10000
    server_address = ('localhost', 10000)
    sock.connect(server_address)
    res = '[null]'
    try:
        # send command
        cmd = 'get_' + id
        cmd = cmd.encode('utf-8') 
        sock.sendall(cmd)
        # receive resource
        res = sock.recv(256)
        res = res.decode('utf-8')
    finally:
        sock.close()
    return res

print("Content-Type: application/json\n\n")
# script input arguments
arg = cgi.FieldStorage()

if 'id' in arg.keys():
    # resources IDs
    ids = ['env', 'rpy']
    # check selected ID
    id = [id for id in ids if id == arg['id'].value]
    if id:
        print(getresource(id[0]))
    else:
        print('[]')
else:
    print('[]')

