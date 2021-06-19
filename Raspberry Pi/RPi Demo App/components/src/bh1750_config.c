/**
  ******************************************************************************
  * @file    bh1750_config.cpp
  * @author  AW
  * @version V2.0
  * @date    27-Mar-2021
  * @brief   Digital light sensor BH1750 driver in C++ for Raspberry Pi.
  *          Configuration source file.
  *
  ******************************************************************************
  */
  
/* Includes ------------------------------------------------------------------*/
#include "bh1750_config.h"

/* Typedef -------------------------------------------------------------------*/

/* Define --------------------------------------------------------------------*/

/* Macro ---------------------------------------------------------------------*/

/* Private variables ---------------------------------------------------------*/

/* Public variables ----------------------------------------------------------*/
BH1750_HandleTypeDef light_sensor = {
  BH1750_I2C, BH1750_ADDRESS_L, 0xffff
};

/* Private function prototypes -----------------------------------------------*/

/* Private function ----------------------------------------------------------*/

/* Public function -----------------------------------------------------------*/