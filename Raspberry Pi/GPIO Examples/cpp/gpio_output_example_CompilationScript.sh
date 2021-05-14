#!/bin/bash
# GPIO output C++ CLI program compilation with g++ and wiringPi & pthread libs
g++ -Wall -pedantic -l wiringPi -l pthread gpio_output_example.cpp -o gpio_output_example && echo "Done"
