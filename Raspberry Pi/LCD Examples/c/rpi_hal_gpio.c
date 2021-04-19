/* blink.c
 *
 * Raspberry Pi GPIO example using sysfs interface.
 * Guillermo A. Amaral B. <g@maral.me>
 *
 * This file blinks GPIO 4 (P1-07) while reading GPIO 24 (P1_18).
 */

#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define IN  0
#define OUT 1

#define LOW  0
#define HIGH 1

#define PIN  24 /* P1-18 */
#define POUT 4  /* P1-07 */

#define BUFFER_MAX 3  
#define DIRECTION_MAX 35
#define VALUE_MAX 30

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

int HAL_GPIO_SetPinDirection(int pin, int dir)
{
  static const char dir_str[2][]  = {"in", "out"};
  static const int dir_str_len[2] = {2, 3};

  char path[DIRECTION_MAX];
  snprintf(path, DIRECTION_MAX, "/sys/class/gpio/gpio%d/direction", pin);
  int fd = open(path, O_WRONLY);
  if(fd == -1) 
  {
    fprintf(stderr, "Failed to open gpio direction for writing!\n");
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

int HAL_GPIO_ReadPin(int pin)
{
  char path[VALUE_MAX];
  snprintf(path, VALUE_MAX, "/sys/class/gpio/gpio%d/value", pin);
  int fd = open(path, O_RDONLY);
  if fd == -1)
  {
    fprintf(stderr, "Failed to open gpio value for reading!\n");
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

int HAL_GPIO_WritePin(int pin, int value)
{
  static const char values_str[] = {'0', '1'};

  char path[VALUE_MAX];
  snprintf(path, VALUE_MAX, "/sys/class/gpio/gpio%d/value", pin);
  int fd = open(path, O_WRONLY);
  if (fd == -1) 
  {
    fprintf(stderr, "Failed to open gpio value for writing!\n");
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

int
main(int argc, char *argv[])
{
  int repeat = 10;

  /*
   * Enable GPIO pins
   */
  if (-1 == GPIOExport(POUT) || -1 == GPIOExport(PIN))
    return(1);

  /*
   * Set GPIO directions
   */
  if (-1 == GPIODirection(POUT, OUT) || -1 == GPIODirection(PIN, IN))
    return(2);

  do {
    /*
     * Write GPIO value
     */
    if (-1 == GPIOWrite(POUT, repeat % 2))
      return(3);

    /*
     * Read GPIO value
     */
    printf("I'm reading %d in GPIO %d\n", GPIORead(PIN), PIN);

    usleep(500 * 1000);
  }
  while (repeat--);

  /*
   * Disable GPIO pins
   */
  if (-1 == GPIOUnexport(POUT) || -1 == GPIOUnexport(PIN))
    return(4);

  return(0);
}