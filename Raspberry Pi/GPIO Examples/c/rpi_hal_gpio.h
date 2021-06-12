/**
  ******************************************************************************
  * @file    rpi_hal_gpio.h
  * @author  AW 
  * @version V1.0
  * @date    24-Apr-2021
  * @brief   Raspberry Pi GPIO API example header file
  *
  ******************************************************************************
  */
#ifndef RPI_HAL_GPIO_H_
#define RPI_HAL_GPIO_H_

/* Config --------------------------------------------------------------------*/

/* Includes ------------------------------------------------------------------*/
#include <time.h>

/* Typedef -------------------------------------------------------------------*/
typedef enum { GPIO_PIN_RESET = 0, GPIO_PIN_SET } GPIO_PinState;
typedef enum { GPIO_INPUT = 0, GPIO_OUTPUT } GPIO_Direction;

/* Define --------------------------------------------------------------------*/
#define HAL_Delay(n) nanosleep((const struct timespec[]){{0, n*1000000L}}, NULL);
/* Macro ---------------------------------------------------------------------*/

/* Public variables ----------------------------------------------------------*/

/* Public function prototypes ------------------------------------------------*/

// https://man7.org/linux/man-pages/man2/nanosleep.2.html
int nanosleep(const struct timespec *req, struct timespec *rem);

/**
 * @brief
 * @param[in] pin
 * @return 
 */
int HAL_GPIO_ExportPin(int pin);

/**
 * @brief
 * @param[in] pin
 * @return 
 */
int HAL_GPIO_UnexportPin(int pin);

/**
 * @brief
 * @param[in] pin
 * @param[in] dir
 * @return 
 */
int HAL_GPIO_SetPinDirection(int pin, GPIO_Direction dir);

/**
 * @brief
 * @param[in] pin
 * @return 
 */
GPIO_PinState HAL_GPIO_ReadPin(int pin);

/**
 * @brief
 * @param[in] pin  
 * @param[in] value
 * @return 
 */
int HAL_GPIO_WritePin(int pin, GPIO_PinState value);

#endif /* RPI_HAL_GPIO_H_ */
