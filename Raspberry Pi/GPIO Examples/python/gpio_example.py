#!/usr/bin/python3
#**
#******************************************************************************
#* @file    /gpio_examples/python/gpio_example.py
#* @author  Adrian Wojcik
#* @version V1.0
#* @date    15-Mar-2020
#* @brief   Raspberry Pi GPIO control: Python 3 with RPi.GPIO lib
#******************************************************************************

import time
import sys
import select
try:
	import RPi.GPIO as GPIO
except RuntimeError:
	print("Error importing RPi.GPIO! This is probably because you need superuser privileges. You can achieve this by using 'sudo' to run your script")
	
timeout = 0.1
buttonState = True
buttonStateLast = True
ledState = False
ledStateName = ["OFF", "ON"]
i = ''

# Pin Definitons:
ledPin = 38    #< LED: Physical pin 38, BCM GPIO28
buttonPin = 36 #< Push-button: Physical pin 36, BCM GPIO16

# Pin Setup:
GPIO.setmode(GPIO.BOARD)
GPIO.setup(ledPin, GPIO.OUT) # LED pin set as output
GPIO.setup(buttonPin, GPIO.IN) # Button pin set as input 

GPIO.output(ledPin, ledState)

print("Press ENTER to exit.")

while not i:
	# Waiting for I/O completion
	i, o, e = select.select( [sys.stdin], [], [], timeout )

	if (i):
		sys.stdin.readline();
		GPIO.cleanup() # cleanup all GPIO
		exit()

	buttonState = (GPIO.input(buttonPin) == GPIO.HIGH)
	
	if (buttonState is False) and (buttonStateLast is True):
		ledState = not ledState
		GPIO.output(ledPin, ledState)
		print("LED state:",ledStateName[ledState])
		
	buttonStateLast = buttonState
