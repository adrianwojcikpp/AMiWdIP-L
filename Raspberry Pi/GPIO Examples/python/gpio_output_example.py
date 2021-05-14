#!/usr/bin/python3
#**
#******************************************************************************
#* @file    /gpio_examples/python/gpio_output_example.py
#* @author  Adrian Wojcik
#* @version V1.0
#* @date    15-Mar-2020
#* @brief   Raspberry Pi digital output control: Python 3 with RPi.GPIO lib
#******************************************************************************

import time
import sys
import select
try:
	import RPi.GPIO as GPIO
except RuntimeError:
	print("Error importing RPi.GPIO! This is probably because you need superuser privileges. You can achieve this by using 'sudo' to run your script")
	
timeout = 0.1
ledState = False
i = ''

# Pin Definitons:
ledPin = 38    #< LED: Physical pin 38, BCM GPIO28

# Pin Setup:
GPIO.setmode(GPIO.BOARD)
GPIO.setup(ledPin, GPIO.OUT) # LED pin set as output

print("Press ENTER to exit.")

GPIO.output(ledPin, GPIO.LOW)
while not i:
	# Waiting for I/O completion
	i, o, e = select.select( [sys.stdin], [], [], timeout )

	if (i):
		sys.stdin.readline();
		GPIO.cleanup() # cleanup all GPIO
		exit()

	ledState = not ledState
	
	if (ledState):
		GPIO.output(ledPin, GPIO.HIGH)
	else:
		GPIO.output(ledPin, GPIO.LOW)
