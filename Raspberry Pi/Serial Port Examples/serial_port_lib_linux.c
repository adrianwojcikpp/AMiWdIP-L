/**
  ******************************************************************************
  * @file    serial_port_lib_linux.c
  * @author  AW   adrian.wojcik@put.poznan.pl
  * @version V1.0
  * @date    24 May 2021
  * @page    https://man7.org/linux/man-pages
  *            /man2
  *              /open.2.html
  *              /close.2.html
  *              /write.2.html
  *              /read.2.html
  *            /man3
  *              /termios.3.html
  *
  * @brief   Simple serial port library for Linux, based on
  *          https://blog.mbedded.ninja/programming/operating-systems
  *            /linux/linux-serial-ports-using-c-cpp/
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
  // Virtual serial port with https://github.com/freemed/tty0tty
  *handle = open("/dev/tnt0", O_RDWR);
  
  if(*handle < 0)
    return -1;   // Can't open /dev/tnt0

  // Create new termios struc, we call it 'tty' for convention
  struct termios tty;

  // Read in existing settings, and handle any error
  if(tcgetattr(*handle , &tty) != 0)
  {
      // printf("Error %i from tcgetattr: %s\n", errno, strerror(errno));
      return -2;
  }

  tty.c_cflag &= ~PARENB;        // Clear parity bit, disabling parity (most common)
  tty.c_cflag &= ~CSTOPB;        // Clear stop field, only one stop bit used in communication (most common)
  tty.c_cflag &= ~CSIZE;         // Clear all bits that set the data size 
  tty.c_cflag |= CS8;            // 8 bits per byte (most common)
  tty.c_cflag |= CREAD | CLOCAL; // Turn on READ & ignore control lines (CLOCAL = 1)

  tty.c_lflag &= ~ICANON;        // Disable canonical mode 
  tty.c_lflag &= ~ECHO;          // Disable echo
  tty.c_lflag &= ~ECHOE;         // Disable erasure
  tty.c_lflag &= ~ECHONL;        // Disable new-line echo
  tty.c_lflag &= ~ISIG;          // Disable interpretation of INTR, QUIT and SUSP
  tty.c_iflag &= ~(IXON | IXOFF | IXANY); // Turn off s/w flow control
  tty.c_iflag &= ~(IGNBRK|BRKINT|PARMRK|ISTRIP|INLCR|IGNCR|ICRNL); // Disable any special handling of received bytes

  tty.c_oflag &= ~OPOST;      // Prevent special interpretation of output bytes (e.g. newline chars)
  tty.c_oflag &= ~ONLCR;      // Prevent conversion of newline to carriage return/line feed
  // tty.c_oflag &= ~OXTABS;  // Prevent conversion of tabs to spaces (NOT PRESENT ON LINUX)
  // tty.c_oflag &= ~ONOEOT;  // Prevent removal of C-d chars (0x004) in output (NOT PRESENT ON LINUX)

  tty.c_cc[VTIME] = 255;      // Wait for up to 25.5s (255 deciseconds), returning as soon as any data is received.
  tty.c_cc[VMIN] = 0;

  // Set in/out baud rate to be 9600
  cfsetispeed(&tty, B9600);
  cfsetospeed(&tty, B9600);

  // Save tty settings, also checking for error
  if(tcsetattr(*handle , TCSANOW, &tty) != 0)	  
  {
      // printf("Error %i from tcsetattr: %s\n", errno, strerror(errno));
      return -3;
  }
  
  return 0; // OK
}

int SERIAL_PORT_DeInit(SERIAL_PORT_Handle handle)
{
  return close(handle);
}

int SERIAL_PORT_WriteString(SERIAL_PORT_Handle handle, char* str)
{
   int rslt, len = strlen(str);	
   rslt = write(handle, str, strlen(str)); 
   
   if(rslt < 0)
     return -1; // Can't write to port
 
   if(rslt != len)
     return -2; // Incorrect message size
   
   return 0; // OK
}
 
int SERIAL_PORT_ReadLine(SERIAL_PORT_Handle handle, char* str)
{
  unsigned long rslt;
  int i = 0;
  
  do
  {
    rslt = read(handle, &str[i], 1);

    if(rslt == 1)
      i++;

    if(rslt < 0)
      return -1; // Can't read from port

  } while(str[i-1] != '\r'); // Until 'CR' (Enter)
  
  str[i] = '\n';             // Add 'LF'
  
  return 0; // OK
}
