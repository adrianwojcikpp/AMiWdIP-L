#!/usr/bin/python3
#**
#******************************************************************************
#* @file    /gpio_examples/python/gpio_input_example.py
#* @author  Adrian Wojcik
#* @version V1.0
#* @date    15-Mar-2020
#* @brief   Raspberry Pi digital input control: Python 3 with RPi.GPIO lib
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
cnt = 0
i = ''

# Pin Definitons:
buttonPin = 36 #< Push-button: Physical pin 36, BCM GPIO16

# Pin Setup:
GPIO.setmode(GPIO.BOARD)
GPIO.setup(buttonPin, GPIO.IN) # Button pin set as input 

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
		cnt = cnt + 1
		print("Push-button counter:",cnt)

	buttonStateLast = buttonState
