/**
  ******************************************************************************
  * @file    main.c 
  * @author  AW           Adrian.Wojcik@put.poznan.pl
  * @version 1.0
  * @date    13 Jun 2021
  * @brief   Displaying measurement data on LCD 
  *
  ******************************************************************************
  */
#include <stdio.h>
#include <stdlib.h>

#include "pushbtn.h"
#include "pushbtn_config.h"

#include "bmp280.h"
#include "bmp280_config.h"

#include "bh1750.h"
#include "bh1750_config.h"

#include "lcd.h"
#include "lcd_config.h"

#include "menu.h"
#include "menu_config.h"

int main(void)
{  
  int loop_counter = 0; // main loop counter

  /** LIGHT SENSOR **********************************************************/
  // Initialize I2C bus
  int i2c_status = HAL_I2C_Init(&hi2c1);
  // Set slave device address
  i2c_status = HAL_I2C_SetSlaveAddress(&hi2c1, light_sensor.Address);

  if(i2c_status < 0)
  {
    printf("I2C initialization error\n");
    return -1; 
  }
  
    // Initialize digital light sensor
  BH1750_Init(&light_sensor);
  
  /** TEMP / PRESSURE SENSOR ************************************************/
  // Initialize SPI bus
  int spi_status = HAL_SPI_Init(&hspi0);

  if(spi_status < 0)
  {
    printf("SPI initialization error\n");
    return -1; 
  }

  // Initialize digital temp/pressure sensor
  BMP280_Init(&temp_press_sensor);

  /** LCD *******************************************************************/
  LCD_Init(&hlcd1); 
  menu_item = &menu_pres1; //< start from pressure sensor
  
  /** Push-button ***********************************************************/
  int next_item_flag = 0;
  HAL_GPIO_ExportPin(hbtn.Pin);
  HAL_GPIO_SetPinDirection(hbtn.Pin, GPIO_INPUT);
  
  while (1)
  {
    /** LCD menu ************************************************************/
    if(loop_counter%100 == 0 || next_item_flag) // update LCD every hundredth loop (1 sec)
    {
      // Input file 
      FILE* input_file = fopen("/home/pi/server/btn.dat", "r"); // Read ["0"/"1"]
      fscanf(input_file, "%d", &next_item_flag);
      fclose(input_file);
      input_file = fopen("/home/pi/server/btn.dat", "w");       // Write "0"
      fprintf(input_file, "0");
      fclose(input_file);
      
      // Change menu item to the next one
      if(next_item_flag)
        menu_item = menu_item->next;
      
      next_item_flag = 0;  
      loop_counter = 0; 
      
      // #1 line - current item
      LCD_SetCursor(&hlcd1, 0, 0);
      MENU_ClearDisplayBuffer(menu_item);
      MENU_CallFunction(menu_item);
      LCD_printStr(&hlcd1, menu_item->display_str);
      // #2 line - next item
      LCD_SetCursor(&hlcd1, 1, 0);
      MENU_ClearDisplayBuffer(menu_item->next);
      MENU_CallFunction(menu_item->next);
      LCD_printStr(&hlcd1, menu_item->next->display_str);
      
      // Output file
      FILE* output_file = fopen("/home/pi/server/lcd.dat", "w");
      fprintf(output_file, "%s\n%s", menu_item->display_str, menu_item->next->display_str);
      fclose(output_file);
    }
    
    /** Push-button *********************************************************/
    if(BTN_EdgeDetected(&hbtn))
      next_item_flag = 1;

    /** Loop delay: 10 ms delay *********************************************/
    loop_counter++;
    HAL_Delay(10);
  }
  
  return 0;
}