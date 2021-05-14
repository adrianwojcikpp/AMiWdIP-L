#!/bin/bash
# GPIO I/O C++ CLI program compilation with g++ and wiringPi & pthread libs
g++ -Wall -pedantic -l wiringPi -l pthread gpio_example.cpp -o gpio_example && echo "Done"