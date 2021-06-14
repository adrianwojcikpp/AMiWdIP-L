# Compiler: gcc
CC      = gcc
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic 
# Libraries

# Source files
SRC     = bmp280_test.c \
          bmp280.c \
          rpi_hal_spi.c \
          rpi_hal_spi_config.c

# Defines
DEF     = -D BMP280_RASPBERRY_PI

default: all
all: build

echo: main.c rpi_hal_spi.c rpi_hal_spi_config.c
		$(CC) $(CFLAGS) -o echo main.c rpi_hal_spi.c rpi_hal_spi_config.c $(DEF)

build: $(SRC)
		$(CC) $(CFLAGS) -o app $(SRC) $(DEF)

clean:
		$(RM) app