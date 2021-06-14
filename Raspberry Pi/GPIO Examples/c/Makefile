# Compiler: gcc
CC      = gcc
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic 
# Libraries
LIBS    = -l pthread
# Source files
SRC     = main.c \
          rpi_hal_gpio.c

default: all
all: build

build: $(SRC)
		$(CC) $(CFLAGS) $(LIBS) -o gpio_example $(SRC)

clean:
		$(RM) gpio_example