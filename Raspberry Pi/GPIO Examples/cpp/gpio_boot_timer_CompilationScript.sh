#!/bin/bash
# GPIO I/O C++ CLI program compilation with g++ and boost, wiringPi & pthread libs
g++ -o gpio_boost_timer gpio_boost_timer.cpp -lboost_system -lboost_date_time -lboost_thread -lpthread -l wiringPi -rdynamic &> gpio_boost_timer.log