#!/bin/bash
# GPIO input init example 
# !! run with 'sudo'

# Exports pin to userspace
echo "16" > /sys/class/gpio/export                  

# Sets pin GPIO16 as an input
echo "in" > /sys/class/gpio/gpio16/direction
