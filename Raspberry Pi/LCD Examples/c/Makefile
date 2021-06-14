# Compiler: gcc
CC      = gcc
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic -std=c11
# Libraries

# Source files
SRC     = main.c \
          lcd.c \
          lcd_config.c \
          rpi_hal_gpio.c

default: all
all: build

build: $(SRC)
		$(CC) $(CFLAGS) -o lcd_example $(SRC)

clean:
		$(RM) lcd_example