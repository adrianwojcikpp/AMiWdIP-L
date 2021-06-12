#!/bin/bash
# PWM C++ CLI program compilation with g++ and wiringPi & pthread libs
g++ -Wall -pedantic -l wiringPi -l pthread pwm_service_app.cpp -o pwm_service_app