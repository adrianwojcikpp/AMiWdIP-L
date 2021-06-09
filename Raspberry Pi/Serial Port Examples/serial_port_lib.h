/**
  ******************************************************************************
  * @file    serial_port_lib.h
  * @author  AW   adrian.wojcik@put.poznan.pl
  * @version V1.0
  * @date    24 May 2021
  * @brief   Simple serial port library header
  *
  ******************************************************************************
  */
#ifndef INC_SERIAL_PORT_LIB_H_
#define INC_SERIAL_PORT_LIB_H_

/* Config --------------------------------------------------------------------*/

/* Includes ------------------------------------------------------------------*/

#if defined(_WIN32)

#include <windows.h>

#elif defined(RASPBERRY_PI)

#include <wiringPi.h>      // WiringPi library - requires installation
#include <wiringSerial.h>  

#elif defined(__linux__ )

#include <fcntl.h>    // Contains file controls like O_RDWR
#include <errno.h>    // Error integer and strerror() function
#include <termios.h>  // Contains POSIX terminal control definitions
#include <unistd.h>   // write(), read(), close()

#elif defined(STM32F746xx)

#include "stm32f7xx_hal.h" // STM32 F7 HAL library
#include "usart.h"         // Global UART handlers 

#endif	

/* Typedef -------------------------------------------------------------------*/

#if defined(_WIN32)

typedef HANDLE SERIAL_PORT_Handle; 

#elif defined(__linux__ )

typedef int SERIAL_PORT_Handle; 

#elif defined(STM32F746xx)

typedef UART_HandleTypeDef* SERIAL_PORT_Handle;

#endif

/* Define --------------------------------------------------------------------*/

/* Macro ---------------------------------------------------------------------*/

/* Public variables ----------------------------------------------------------*/

/* Public function prototypes ------------------------------------------------*/

/**
 * @brief Serial port initialization procedure
 * @param [out] handle : Serial port handle 
 * @return Initialization status: 0 if OK
 *                               -1 if can't open port
 *                               -2 if can't read settings
 *                               -3 if can't write settings
 */
int SERIAL_PORT_Init(SERIAL_PORT_Handle* handle);

/**
 * @brief Serial port deinitialization procedure
 * @param [in] handle : Serial port handle 
 * @return Initialization status: 0 if OK
 *                               -1 if can't close port
 */
int SERIAL_PORT_DeInit(SERIAL_PORT_Handle handle);

/**
 * @brief Serial port null-terminated character array writing procedure 
 * @param [in] handle : Serial port handle 
 * @param [in] str : Message to transmit (a null-terminated character array)
 * @return Writing status: 0 if OK
 *                        -1 if can't write to port
 *                        -2 if incorrect message size
 */
int SERIAL_PORT_WriteString(SERIAL_PORT_Handle handle, char* str);
 
/**
 * @brief Serial port line (EOF-terminated character array) reading procedure
 * @param [in] handle : Serial port handle 
 * @param [out] str : Received message (a EOF-terminated character array)
 * @return Writing status: 0 if OK
 *                        -1 if can't read from port
 */
int SERIAL_PORT_ReadLine(SERIAL_PORT_Handle handle, char* str);

#endif /* INC_SERIAL_PORT_LIB_H_ */
