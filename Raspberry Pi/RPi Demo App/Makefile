# Compiler: gcc
CC      = gcc
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic 
# Libraries

# Source files
SRC     = main.c \
          components/src/pushbtn.c \
          components/src/pushbtn_config.c \
          components/src/bmp280.c \
          components/src/bmp280_config.c \
          components/src/bh1750.c \
          components/src/bh1750_config.c \
          components/src/lcd.c \
          components/src/lcd_config.c \
          components/src/menu.c \
          components/src/menu_config.c \
          rpi_hal/src/rpi_hal_spi.c \
          rpi_hal/src/rpi_hal_spi_config.c \
          rpi_hal/src/rpi_hal_i2c.c \
          rpi_hal/src/rpi_hal_i2c_config.c \
          rpi_hal/src/rpi_hal_uart.c \
          rpi_hal/src/rpi_hal_uart_config.c \
          rpi_hal/src/rpi_hal_gpio.c \
          rpi_hal/src/rpi_hal_gpio_config.c \
          rpi_hal/src/rpi_hal_delay.c
# Defines
DEF     = -D RASPBERRY_PI \
          -D BMP280_RASPBERRY_PI \
          -D BH1750_RASPBERRY_PI \
#Include paths
INC     = -I rpi_hal/inc \
          -I components/inc \

default: all
all: build

build: $(SRC)
		$(CC) $(CFLAGS) $(INC) -o app $(SRC) $(DEF)
    
debug: $(SRC)
		$(CC) $(CFLAGS) $(INC) -o app $(SRC) $(DEF) -D DEBUG

clean:
		$(RM) app