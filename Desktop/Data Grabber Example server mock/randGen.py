"""
 ******************************************************************************
 * @file    Data Grabber Example server mock/randGen.py
 * @author  Adrian Wojcik
 * @version V1.1
 * @date    17-Apr-2021
 * @brief   Simple IoT server mock: random signal
 ******************************************************************************
"""

# generate random floating point values
from random import seed
from random import random
import json
import time
import signal
# data point
class DataPoint:
	def __init__(self, data):
		self.data = data

# seed random number generator
seed(2)

try:
	while True:
		# generate random numbers between <min,max>
		min = 105.0
		max = -20.0
		v = min + (random() * (max - min))
		dp = DataPoint(v)
		
		# get json string
		jsonStr = json.dumps(dp.__dict__)
		
		#save to file
		try:
			datafile = open("chartdata.json","w")
			datafile.write(jsonStr)
		except:
			print("Write Error")
		finally:
			datafile.close()
		
		#print(jsonStr)
		
		time.sleep(0.1)
		
except KeyboardInterrupt:
	pass