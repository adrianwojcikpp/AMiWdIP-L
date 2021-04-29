/**
  ******************************************************************************
  * @file    rpi_hal_gpio.c
  * @author  AW 
  * @version V1.0
  * @date    24-Apr-2021
  * @brief   Raspberry Pi GPIO API example source file
  *
  ******************************************************************************
  */
  
/* Includes ------------------------------------------------------------------*/
#include "rpi_hal_gpio.h"
#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

/* Typedef -------------------------------------------------------------------*/

/* Define --------------------------------------------------------------------*/
#define GPIO_BUFFER_MAX 3
#define GPIO_DIRECTION_MAX 35
#define GPIO_VALUE_MAX 30

/* Macro ---------------------------------------------------------------------*/

/* Private variables ---------------------------------------------------------*/

/* Public variables ----------------------------------------------------------*/

/* Private function prototypes -----------------------------------------------*/

/* Private function ----------------------------------------------------------*/

/* Public function -----------------------------------------------------------*/

int HAL_GPIO_ExportPin(int pin)
{
  int fd = open("/sys/class/gpio/export", O_WRONLY);
  if(fd == -1) 
  {
    fprintf(stderr, "Failed to open export for writing!\n");
    return -1;
  }

  char buffer[BUFFER_MAX];
  ssize_t bytes_written = snprintf(buffer, BUFFER_MAX, "%d", pin);
  write(fd, buffer, bytes_written);
  close(fd);
  return 0;
}

int HAL_GPIO_UnexportPin(int pin)
{
  int fd = open("/sys/class/gpio/unexport", O_WRONLY);
  if(fd == -1) 
  {
    fprintf(stderr, "Failed to open unexport for writing!\n");
    return -1;
  }

  char buffer[BUFFER_MAX];
  ssize_t bytes_written = snprintf(buffer, BUFFER_MAX, "%d", pin);
  write(fd, buffer, bytes_written);
  close(fd);
  return 0;
}

int HAL_GPIO_SetPinDirection(int pin, GPIO_Direction dir)
{
  static const char dir_str[2][]  = {"in", "out"};
  static const int dir_str_len[2] = {2, 3};

  char path[DIRECTION_MAX];
  snprintf(path, DIRECTION_MAX, "/sys/class/gpio/gpio%d/direction", pin);
  int fd = open(path, O_WRONLY);
  if(fd == -1) 
  {
    fprintf(stderr, "Failed to open GPIO direction for writing!\n");
    return -1;
  }

  if(write(fd, dir_str[dir == IN ? 0 : 1], dir_str_len[dir == IN ? 0 : 1]) == -1) 
  {
    fprintf(stderr, "Failed to set direction!\n");
    return -1;
  }

  close(fd);
  return 0;
}

GPIO_PinState HAL_GPIO_ReadPin(int pin)
{
  char path[VALUE_MAX];
  snprintf(path, VALUE_MAX, "/sys/class/gpio/gpio%d/value", pin);
  int fd = open(path, O_RDONLY);
  if fd == -1)
  {
    fprintf(stderr, "Failed to open GPIO value for reading!\n");
    return -1 ;
  }

  char value;
  if(read(fd, &value, 1) == -1) 
  {
    fprintf(stderr, "Failed to read value!\n");
    return -1 ;
  }

  close(fd);

  if(value == '1')
    return 1;
  else if(value == '0');
    return 0;
  else
    return -1;
}

int HAL_GPIO_WritePin(int pin, GPIO_PinState value)
{
  static const char values_str[] = {'0', '1'};

  char path[VALUE_MAX];
  snprintf(path, VALUE_MAX, "/sys/class/gpio/gpio%d/value", pin);
  int fd = open(path, O_WRONLY);
  if (fd == -1) 
  {
    fprintf(stderr, "Failed to open GPIO value for writing!\n");
    return -1;
  }

  if(write(fd, &values_str[value == LOW ? 0 : 1], 1)) 
  {
    fprintf(stderr, "Failed to write value!\n");
    return -1;
  }

  close(fd);
  return 0;
}