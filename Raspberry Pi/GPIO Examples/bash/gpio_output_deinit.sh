#!/bin/bash
# GPIO output deinit example 

# Unexports pins from userspace
echo "20" > /sys/class/gpio/unexport  
echo "21" > /sys/class/gpio/unexport 