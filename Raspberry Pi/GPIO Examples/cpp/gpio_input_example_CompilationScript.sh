#!/bin/bash
# GPIO input C++ CLI program compilation with g++ and wiringPi & pthread libs
g++ -Wall -pedantic -l wiringPi -l pthread gpio_input_example.cpp -o gpio_input_example && echo "Done"