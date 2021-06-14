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
          bh1750.cpp \
          bh1750_config.cpp \
          rpi_hal_i2c.cpp \
          rpi_hal_i2c_config.cpp
# Defines
DEF     = -D BH1750_RASPBERRY_PI

default: all
all: build

build: $(SRC)
		$(CC) $(CFLAGS) $(LIBS) -o app $(SRC) $(DEF)

clean:
		$(RM) app