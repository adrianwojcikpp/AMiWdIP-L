#!/bin/bash
# GPIO output set example 

# Sets pin GPIO20 and GPIO21 to high
echo "1" > /sys/class/gpio/gpio20/value 
echo "0" > /sys/class/gpio/gpio21/value 