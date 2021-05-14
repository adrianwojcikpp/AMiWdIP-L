#!/bin/bash
# GPIO output reset example 

# Sets pin GPIO20 and GPIO21 to low
echo "0" > /sys/class/gpio/gpio20/value 
echo "1" > /sys/class/gpio/gpio21/value 