# Compiler: g++
CC      = g++
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic -std=c++11 -rdynamic
# Libraries
LIBS    = -l wiringPi -l pthread \
          -l boost_system -l boost_date_time -l boost_thread
# Source files
SRC     = gpio_boost_timer.cpp

default: all
all: build

build: $(SRC)
		$(CC) $(CFLAGS) $(LIBS) -o gpio_boost_timer $(SRC)

clean:
		$(RM) gpio_input_example