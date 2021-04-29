/**
  ******************************************************************************
  * @file    lcd_config.c
  * @author  AW
  * @version V2.0
  * @date    24-Apr-2021
  * @brief   Simple HD44780 driver library for Raspberry Pi configuration file.
  *
  ******************************************************************************
  */
  
/* Includes ------------------------------------------------------------------*/
#include "lcd.h"
#include "lcd_config.h"

/* Typedef -------------------------------------------------------------------*/

/* Define --------------------------------------------------------------------*/

/* Macro ---------------------------------------------------------------------*/
#define LCD_D4_Pin
#define LCD_D5_Pin
#define LCD_D6_Pin
#define LCD_D7_Pin

/* Private variables ---------------------------------------------------------*/
LCD_PinType  LCD_DATA_Pins[]  = {
  LCD_D4_Pin, LCD_D5_Pin, LCD_D6_Pin, LCD_D7_Pin
};

/* Public variables ----------------------------------------------------------*/
LCD_HandleTypeDef hlcd1 = {
  .DATA_Pins = LCD_DATA_Pins,
  .RS_Pin = LCD_RS_Pin,
  .E_Pin = LCD_E_Pin,
  .Mode = LCD_4_BIT_MODE,
};

/* Private function ----------------------------------------------------------*/

/* Public function -----------------------------------------------------------*/
