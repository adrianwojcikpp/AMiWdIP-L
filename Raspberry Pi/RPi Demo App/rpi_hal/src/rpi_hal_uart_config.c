/**
  ******************************************************************************
  * @file    rpi_hal_uart_config.c
  * @author  AW           Adrian.Wojcik@put.poznan.pl
  * @version 1.0
  * @date    Sat 19 Jun 17:48:05 CEST 2021
  * @brief   Simple hardware abstraction layer for Raspberry Pi serial port
  *          Configuration source file.
  *
  ******************************************************************************
  */
  
/* Includes ------------------------------------------------------------------*/
#include "rpi_hal_uart_config.h"

/* Typedef -------------------------------------------------------------------*/

/* Define --------------------------------------------------------------------*/

/* Macro ---------------------------------------------------------------------*/

/* Private variables ---------------------------------------------------------*/

/* Public variables ----------------------------------------------------------*/
UART_Handle_TypeDef huart0 = { -1, DEV_UART0, { 9600 /* bps */} };

/* Private function prototypes -----------------------------------------------*/

/* Private function ----------------------------------------------------------*/

/* Public function -----------------------------------------------------------*/
