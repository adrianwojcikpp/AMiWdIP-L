# Compiler: gcc
CC      = gcc
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic 
# Libraries

# Source files
SRC     = main.c \
          components/src/bmp280.c \
          components/src/bmp280_config.c \
          rpi_hal/src/rpi_hal_spi.c \
          rpi_hal/src/rpi_hal_spi_config.c \
          rpi_hal/src/rpi_hal_delay.c
SRC_ECHO= spi_echo_test.c \
          rpi_hal/src/rpi_hal_spi.c \
          rpi_hal/src/rpi_hal_spi_config.c \
          rpi_hal/src/rpi_hal_delay.c 
# Defines
DEF     = -D BMP280_RASPBERRY_PI
#Include paths
INC     = -I rpi_hal/inc \
          -I components/inc
          
default: all
all: build

echo: spi_echo_test.c rpi_hal/src/rpi_hal_spi.c rpi_hal/src/rpi_hal_spi_config.c
		$(CC) $(CFLAGS) $(INC) -o spi_echo_test $(SRC_ECHO) $(DEF)

build: $(SRC)
		$(CC) $(CFLAGS) $(INC) -o app $(SRC) $(DEF)

clean:
		$(RM) app