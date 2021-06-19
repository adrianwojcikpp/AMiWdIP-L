# Compiler: gcc
CC      = gcc
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic -std=c11
# Libraries
LIB     = -l wiringPi
# Source files
SRC     = main.c \
          serial_port_lib_rpi.c
# Defines
DEF     = -D RASPBERRY_PI
          
default: all
all: build

build: $(SRC)
		$(CC) $(CFLAGS) $(LIB) -o app $(SRC) $(DEF)
    
linux: $(SRC)
		$(CC) $(CFLAGS) $(LIB) -o app2 main.c serial_port_lib_linux.c 

clean:
		$(RM) app
		$(RM) app2