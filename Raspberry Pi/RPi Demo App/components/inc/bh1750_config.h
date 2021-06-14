/**
  ******************************************************************************
  * @file    bh1750_config.h
  * @author  AW
  * @version V2.0
  * @date    27-Mar-2021
  * @brief   Digital light sensor BH1750 driver in C++ for Raspberry Pi.
  *          Configuration header file.
  *
  ******************************************************************************
  */
  
#ifndef INC_BH1750_CONF_H_
#define INC_BH1750_CONF_H_

/* Config --------------------------------------------------------------------*/

/* Includes ------------------------------------------------------------------*/
#include "bh1750.h"
#include "rpi_hal_i2c_config.h"

/* Typedef -------------------------------------------------------------------*/

/* Define --------------------------------------------------------------------*/
#define BH1750_I2C (&hi2c1)

/* Macro ---------------------------------------------------------------------*/

/* Public variables ----------------------------------------------------------*/
extern BH1750_HandleTypeDef light_sensor;

/* Public function prototypes ------------------------------------------------*/

#endif /* INC_BH1750_CONF_H_ */