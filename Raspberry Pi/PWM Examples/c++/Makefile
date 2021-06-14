# Compiler: g++
CC      = g++
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic 
# Libraries
LIBS    = -l pthread -l wiringPi

default: all
all: build

build: pwm_example.cpp
		$(CC) $(CFLAGS) $(LIBS) -o pwm_example pwm_example.cpp $(DEF)
    
setduty: pwm_setDuty_example.cpp
		$(CC) $(CFLAGS) $(LIBS) -o pwm_setDuty_example pwm_setDuty_example.cpp $(DEF)

clean:
		$(RM) pwm_example
		$(RM) pwm_setDuty_example
    