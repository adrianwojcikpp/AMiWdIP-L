#include <stdio.h>
#include <stdlib.h>

#include "rpi_hal_spi.h" 
#include "rpi_hal_spi_config.h"

int main(int argc, char *argv[])
{
	int status = HAL_SPI_Init(&hspi0);
  printf("Initialization status: %d\n", status);

  uint8_t tx1[] = "ABC";
  uint8_t tx2[] = "Hello, SPI";
  uint8_t rx[20] = { 0 };
  
  status = HAL_SPI_Transmit(&hspi0, tx1, 3);
  printf("Transmit status: %d\n", status);
  status = HAL_SPI_TransmitReceive(&hspi0, tx2, rx, 11);
  printf("TransmitReceive status: %d\n", status);
  
  printf("TX: %s \n", tx2);
  printf("RX: %s \n", rx);

	status = HAL_SPI_Deinit(&hspi0);
  printf("Initialization status: %d\n", status);
  
	return 0;
}
