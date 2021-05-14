#!/bin/bash
# GPIO output init example 
# !! run with 'sudo'

# Exports pina to userspace
echo "20" > /sys/class/gpio/export
echo "21" > /sys/class/gpio/export

# Sets pin GPIO20 and GPIO21 as an outputa
echo "out" > /sys/class/gpio/gpio20/direction
echo "out" > /sys/class/gpio/gpio21/direction

# Sets pin GPIO20 and GPIO21 to low
echo "0" > /sys/class/gpio/gpio20/value 
echo "0" > /sys/class/gpio/gpio21/value 