/**
  ******************************************************************************
  * @file    serial_port_lib_win32.c
  * @author  AW   adrian.wojcik@put.poznan.pl
  * @version V1.0
  * @date    24 May 2021
  * @page    https://docs.microsoft.com/en-us/windows/win32/api
  *            /fileapi
  *              /nf-fileapi-createfilea
  *              /nf-fileapi-writefile
  *              /nf-fileapi-readfile
  *            /handleapi
  *              /nf-handleapi-closehandle
  *            /winbase
  *              /ns-winbase-dcb   
  *
  * @brief   Simple serial port library for Windows, based on: 
  *          https://www.xanthium.in/Serial-Port-Programming-using-Win32-API
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
  *handle = CreateFileA("\\\\.\\COM1",              // Port name
                      GENERIC_READ | GENERIC_WRITE, // Read(Rx)/Write(Tx)
                      0,             // No sharing
                      NULL,          // No security
                      OPEN_EXISTING, // Open existing port only
                      0,             // Non Overlapped I/O
                      NULL);         // Null for Comm Devices

  if (*handle == INVALID_HANDLE_VALUE)
      return -1; // Can't open COM1
    
  //  Initialize the DCB structure.
  DCB dcb;
  SecureZeroMemory(&dcb, sizeof(DCB));
  dcb.DCBlength = sizeof(DCB);

  // Build on the current configuration 
  // by first retrieving all current settings.
  _Bool Status = GetCommState(*handle, &dcb);
  
  if(!Status)
    return -2; // Can't read current settings

  //  Fill in some DCB values and set the com state: 
  //  9600 bps, 8 data bits, no parity, and 1 stop bit.
  dcb.BaudRate = CBR_9600;      //  baud rate
  dcb.ByteSize = 8;             //  data size, xmit and rcv
  dcb.Parity   = NOPARITY;      //  parity bit
  dcb.StopBits = ONESTOPBIT;    //  stop bit

  Status = SetCommState(*handle, &dcb);
  
  if(!Status)
    return -3; // Can't write new settings
  
  return 0; // OK
}

int SERIAL_PORT_DeInit(SERIAL_PORT_Handle handle)
{
  if(!CloseHandle(handle))
    return -1; // Can't close serial port

  return 0; // OK
}

int SERIAL_PORT_WriteString(SERIAL_PORT_Handle handle, char* str)
{
  unsigned long rslt, len = (unsigned long)strlen(str);
  
  _Bool Status = WriteFile(handle,  // Handle to the Serial port
                           str,     // Data to be written to the port
                           len,     // Number of bytes to write
                           &rslt,   // Number of bytes written
                           NULL); 
   
   if(!Status)
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
    // Read single character
    _Bool Status = ReadFile(handle,   // Handle to the Serial port
                            &str[i],  // Data to be read from the port
                            1,        // Number of bytes to read
                            &rslt,    // Number of bytes read
                            NULL);
  
    if(!Status)
      return -1; // Can't read from port
  
    i++;
   
  } while(str[i-1] != '\r'); // Until 'CR' (Enter)
  
  str[i] = '\n';             // Add 'LF'
  
  return 0; // OK
}
