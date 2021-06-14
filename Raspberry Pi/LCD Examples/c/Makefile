# Compiler: gcc
CC      = gcc
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic -std=c11
# Libraries

# Source files
SRC     = main.c \
          components/src/lcd.c \
          components/src/lcd_config.c \
          rpi_hal/src/rpi_hal_gpio.c \
          rpi_hal/src/rpi_hal_delay.c
#Include paths
INC     = -I rpi_hal/inc \
          -I components/inc

default: all
all: build

build: $(SRC)
		$(CC) $(CFLAGS) $(INC) -o lcd_example $(SRC)

clean:
		$(RM) lcd_example