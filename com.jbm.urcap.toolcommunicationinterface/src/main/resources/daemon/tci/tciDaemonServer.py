#!/usr/bin/env python

import xmlrpclib
from SimpleXMLRPCServer import SimpleXMLRPCServer

import serial
from time import sleep

tci = serial.Serial()

#port = '/dev/ttyTool'
port = '/dev/tty0'
timeout = 1     # Seconds for reading

tci.port = port
tci.timeout = timeout

def ping():
    print "Got a ping"
    return "pong"

def isOpen():
    print "Querying serial open state"
    return tci.isOpen()

def open():
    print "Opening port"
    try:
        tci.open() 
    except:
        print "Error opening resource"
    return tci.isOpen()

def close():
    print "Closing port"
    try:
        tci.close() 
    except:
        print "Error closing resource"
    return not tci.isOpen()

def write(message):
    success = False
    if(not tci.isOpen()):
        print "Port is not open"
    else:
        print "Writing: ", message
        tci.write(message)
        success = True
    return success
 
def read(byteCount):
    reply = ""
    if(not tci.isOpen()):
        print "Port is not open"
    else:
        print "Bytes to read: ", byteCount
        reply = tci.read(byteCount)
        print "Read: ", reply
    return reply

#print tci
#print isOpen()
#print "Opening"
#open()
#print isOpen()
#print tci
#write("Hello World")
#print read(4)
#print "Closing"
#close()

print "Opening XML-RPC Server"
server = SimpleXMLRPCServer(("", 25000), allow_none=True)
server.register_function(write, "write")
server.register_function(read, "read")
server.register_function(ping, "ping")
server.register_function(open, "open")
server.register_function(close, "close")
server.register_function(isOpen, "isOpen")
server.serve_forever()