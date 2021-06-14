# Compiler: gcc
CC      = gcc
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic -std=c11
# Libraries

# Source files
SRC     = main.c \
          rpi_hal/src/rpi_hal_delay.c \
          rpi_hal/src/rpi_hal_gpio.c \
#Include paths
INC     = -I rpi_hal/inc

default: all
all: build

build: $(SRC)
		$(CC) $(CFLAGS) $(INC) -o gpio_example $(SRC)

clean:
		$(RM) gpio_example