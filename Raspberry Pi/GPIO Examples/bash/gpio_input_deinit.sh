#!/bin/bash
# GPIO input deinit example 

# Exports pin to userspace
echo "16" > /sys/class/gpio/unexport  
                