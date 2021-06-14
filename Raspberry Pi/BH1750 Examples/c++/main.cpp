#include <stdio.h>
#include <cstdio>
#include <thread>
#include <chrono>
#include <future>

#include "rpi_hal_i2c_config.h"  // I2C bus configuration file
#include "bh1750_config.h"       // BH1750 sensor configuration file

typedef union 
{
  std::uint8_t bytes[sizeof(float)];
  float fp; 
} LightMeasurement_TypeDef; //! Light intensity measurement data type

/**
 * @brief Wait for input character.
 * @param[in] timeout Timeout in milliseconds.
 * @return Input character. EOF if timeout.
 */
inline int wait_for_char(int timeout)
{
  static std::future<int> async_getchar = std::async(std::getchar);
  if(async_getchar.wait_for(std::chrono::milliseconds(timeout)) == std::future_status::ready)
    return async_getchar.get();
  else
    return EOF;
}

int main (void) 
{
  // Initialize I2C bus
  HAL_I2C_Init(&hi2c1);
  // Set slave device address
  HAL_I2C_SetSlaveAddress(&hi2c1, light_sensor.Address);
  // Initialize digital light sensor
  BH1750_Init(&light_sensor);
  // Light intensity measurement result
  LightMeasurement_TypeDef light;
  // Result file 
  std::FILE* light_file = std::fopen("light", "wb");

  /* Main loop */
  while(1)
  {
    // Wait for user input
    if(wait_for_char(200) != EOF)
      break;
    // Read measurement from sensor
    light.fp = BH1750_ReadLux(&light_sensor);
    // Write measurement to file 
    std::fwrite(light.bytes, 1, sizeof(float), light_file);
    std::rewind(light_file);
    // Print result
    printf("Light intensity: %.4f Lux (0x%02x%02x%02x%02x)\n", 
           light.fp, light.bytes[3], light.bytes[2], light.bytes[1], light.bytes[0]);
  }
  
  // Close result file
  std::fclose(light_file);
  // Deinitilize I2C bus
  HAL_I2C_Deinit(&hi2c1);
  
  return 0;
}