#!/usr/bin/python3
#**
#******************************************************************************
#* @file    /pwm_examples/python/pwm_example.py
#* @author  Adrian Wojcik
#* @version V1.0
#* @date    15-Mar-2020
#* @brief   Raspberry Pi PWM control: Python 3 with RPi.GPIO lib
#******************************************************************************

import time
import sys
import select
import numpy

try:
	import RPi.GPIO as GPIO
except RuntimeError:
	print("Error importing RPi.GPIO! Use 'sudo' to run your script")
  
timeout = 1	
i = ''	
duty = 0 # [%]
freq = 500 # [Hz]
	
# Pin Definitons:
pwmPin = 12    #< LED: Physical pin 12, BCM GPIO18	
	
GPIO.setmode(GPIO.BOARD)
GPIO.setup(pwmPin, GPIO.OUT)

p = GPIO.PWM(pwmPin, freq)  
p.start(duty)

print("Press ENTER to exit.")

while not i:
	# Waiting for I/O completion
	i, o, e = select.select( [sys.stdin], [], [], timeout )

	if (i):
		sys.stdin.readline();
		p.stop()
		GPIO.cleanup() # cleanup all GPIO
		exit()
		
	for d in numpy.arange(1, 100, 1):
		#print(d)
		p.ChangeDutyCycle(d)
		time.sleep(0.02)
	for d in numpy.arange(100, -1, -1):
		#print(d)
		p.ChangeDutyCycle(d)
		time.sleep(0.02)
