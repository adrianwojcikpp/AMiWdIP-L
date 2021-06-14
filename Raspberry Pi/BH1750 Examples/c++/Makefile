# Compiler: g++
CC      = g++
# Clean command
RM      = rm -f 
# Compiler flags
CFLAGS  = -Wall -pedantic 
# Libraries
LIBS    = -l pthread 
# Source files
SRC     = main.cpp \
          components/src/bh1750.c \
          components/src/bh1750_config.c \
          rpi_hal/src/rpi_hal_i2c.c \
          rpi_hal/src/rpi_hal_i2c_config.c
# Defines
DEF     = -D BH1750_RASPBERRY_PI
#Include paths
INC     = -I rpi_hal/inc \
          -I components/inc
          
default: all
all: build

build: $(SRC)
		$(CC) $(CFLAGS) $(LIBS) $(INC) -o app $(SRC) $(DEF)

clean:
		$(RM) app