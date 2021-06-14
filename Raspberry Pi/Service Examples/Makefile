# Compiler: g++
CC      = g++
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic -std=c++11
# Libraries
LIBS    = -l wiringPi -l pthread
# Source files
SRC     = pwm_service_app.cpp

default: all
all: build

build: $(SRC)
		$(CC) $(CFLAGS) $(LIBS) -o pwm_service_app $(SRC)

clean:
		$(RM) pwm_service_app