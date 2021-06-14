# Compiler: g++
CC      = g++
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic -std=c++11
# Libraries
LIBS    = -l wiringPi -l pthread
# Source files
SRC     = gpio_input_example.cpp

default: all
all: build

build: $(SRC)
		$(CC) $(CFLAGS) $(LIBS) -o gpio_input_example $(SRC)

clean:
		$(RM) gpio_input_example