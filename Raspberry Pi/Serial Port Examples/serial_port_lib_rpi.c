/**
  ******************************************************************************
  * @file    serial_port_lib_rpi.c
  * @author  AW   adrian.wojcik@put.poznan.pl
  * @version V1.0
  * @date    31 May 2021
  * @page    http://wiringpi.com/reference/serial-library/
  *
  * @brief   Simple serial port library for Raspberry Pi, based on
  *          https://www.electronicwings.com/raspberry-pi
              /raspberry-pi-uart-communication-using-python-and-c
  *
  ******************************************************************************
  */

/* Includes ------------------------------------------------------------------*/
#include "serial_port_lib.h"
#include <string.h>

/* Typedef -------------------------------------------------------------------*/

/* Define --------------------------------------------------------------------*/

/* Macro ---------------------------------------------------------------------*/

/* Private variables ---------------------------------------------------------*/

/* Public variables ----------------------------------------------------------*/

/* Private function prototypes -----------------------------------------------*/

/* Private function ----------------------------------------------------------*/

/* Public function -----------------------------------------------------------*/

int SERIAL_PORT_Init(SERIAL_PORT_Handle* handle)
{
  // Create handle for a serial communications device.
  // https://www.raspberrypi.org/documentation/configuration/uart.md
  *handle = serialOpen ("/dev/ttyS0", 9600);
  
  if(*handle < 0)
    return -1;   // Can't open /dev/ttyS0

  // Initialize wiringPi library
  if(wiringPiSetup () == -1)
  {
      // printf("Unable to start wiringPi [%i]: %s\n", errno, strerror(errno));
      return -1;
  }

  return 0; // OK
}

int SERIAL_PORT_DeInit(SERIAL_PORT_Handle handle)
{
  serialClose(handle);
  return 0;
}

int SERIAL_PORT_WriteString(SERIAL_PORT_Handle handle, char* str)
{
  serialPuts(handle, str);
  return 0; // OK
}
 
int SERIAL_PORT_ReadLine(SERIAL_PORT_Handle handle, char* str)
{
  unsigned long rslt;
  int i = 0;
  
  do
  {
    rslt = serialDataAvail(handle);

    if(rslt >= 1)
    {
      for(int n = 0; n < rslt; n++)
      {
        str[i] = (char)serialGetchar(handle) ;
        i++;
      }
    }

    if(rslt < 0)
      return -1; // Can't read from port

  } while(str[i-1] != '\r'); // Until 'CR' (Enter)
  
  str[i] = '\n';             // Add 'LF'
  
  return 0; // OK
}
