#!usr/bin/python3

"""
 ******************************************************************************
 * @file    FIR Example server mock/test_signal.py
 * @author  Adrian Wojcik
 * @version V1.1
 * @date    17-Apr-2021
 * @brief   Simple IoT server mock: harmonic signal
 ******************************************************************************
"""

import cgi
import cgitb; cgitb.enable()  # for troubleshooting
import math

print("Content-Type: application/json\n")

x = 2*math.pi*int(cgi.FieldStorage().getvalue('k'))/10
s = math.sin(0.1*x)+math.sin(0.2*x)+math.sin(0.7*x)+math.sin(1.0*x)

print(s)