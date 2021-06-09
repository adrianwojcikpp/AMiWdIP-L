/**
  ******************************************************************************
  * @file    serial_port_lib_stm32.c
  * @author  AW   adrian.wojcik@put.poznan.pl
  * @version V1.0
  * @date    31 May 2021
  * @page    https://www.st.com/resource/en/user_manual
  *           /dm00189702-description-of-stm32f7-hal-and-lowlayer-drivers-stmicroelectronics.pdf
  *
  *           R
  *
  * @brief   Simple serial port library for STM32 F7 microcontroller
  *
  ******************************************************************************
  */

/* Includes ------------------------------------------------------------------*/
#include "serial_port_lib.h"
#include <string.h>

/* Typedef -------------------------------------------------------------------*/

/* Define --------------------------------------------------------------------*/
#define huart huart2
#define TIMEOUT 100 // ms

/* Macro ---------------------------------------------------------------------*/

/* Private variables ---------------------------------------------------------*/

/* Public variables ----------------------------------------------------------*/

/* Private function prototypes -----------------------------------------------*/

/* Private function ----------------------------------------------------------*/

/* Public function -----------------------------------------------------------*/

int SERIAL_PORT_Init(SERIAL_PORT_Handle* handle)
{
  // Get global UARTx handler
  *handle = &huart;
  
  return 0; // OK
}

int SERIAL_PORT_DeInit(SERIAL_PORT_Handle handle)
{
  return 0; // OK
}

int SERIAL_PORT_WriteString(SERIAL_PORT_Handle handle, char* str)
{
  uint16_t len = (uint16_t)strlen(str);
  
  HAL_StatusTypeDef Status = HAL_UART_Transmit(handle,        // Handle to the Serial port
                                               (uint8_t*)str, // Data to be written to the port
                                               len,           // Number of bytes to write
                                               TIMEOUT);      // Timeout in ms
   
   if(!Status)
     return -1; // Can't write to port
   
   return 0; // OK
}
 
int SERIAL_PORT_ReadLine(SERIAL_PORT_Handle handle, char* str)
{
  unsigned long rslt;
  int i = 0;
  
  do
  {
    // Read single character
    HAL_StatusTypeDef Status = HAL_UART_Receive(handle,            // Handle to the Serial port
                                                (uint8_t*)&str[i], // Data to be read from the port
                                                1,                 // Number of bytes to read
    if(Status == HAL_OK)
      i++;

    if(Status == HAL_ERROR)
      return -1; // Can't read from port
   
  } while(str[i-1] != '\r'); // Until 'CR' (Enter)
  
  str[i] = '\n';             // Add 'LF'
  
  return 0; // OK
}
