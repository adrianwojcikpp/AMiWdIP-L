/**
  ******************************************************************************
  * @file    main.c 
  * @author  AW           Adrian.Wojcik@put.poznan.pl
  * @version 1.0
  * @date    13 Jun 2021
  * @brief   Printing measurement data
  *
  ******************************************************************************
  */
#include <stdio.h>
#include <stdlib.h>

#include "bmp280.h"
#include "bmp280_config.h"

int main(void)
{   
  /** TEMP / PRESSURE SENSOR ************************************************/
  // Initialize SPI bus
  int spi_status = HAL_SPI_Init(&hspi0);
  // Initialize digital temp/pressure sensor
  BMP280_Init(&temp_press_sensor);
  // Temperature measurement result
  struct bmp280_uncomp_data ucomp_data;
  int32_t temp32;

  while (1)
  {
      /* Reading the raw data from BMP280 sensor */
      bmp280_get_uncomp_data(&ucomp_data, &temp_press_sensor);

      /* Getting the 32 bit compensated temperature */
      bmp280_get_comp_temp_32bit(&temp32, ucomp_data.uncomp_temp, &temp_press_sensor);

      /* Print results */
      printf("T: %d.%d *C \n", temp32/100, temp32%100);

      /* Loop delay */
      temp_press_sensor.delay_ms(1000);
  }

  return 0;
}