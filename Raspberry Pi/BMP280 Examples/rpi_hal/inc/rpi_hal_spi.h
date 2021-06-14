/**
  ******************************************************************************
  * @file    rpi_hal_spi.h
  * @author  AW           Adrian.Wojcik@put.poznan.pl
  * @version 1.0
  * @date    13 Jun 2021
  * @brief   Simple hardware abstraction layer for Raspberry Pi SPI bus
  *
  ******************************************************************************
  */
  
#ifndef RPI_HAL_SPI_H_
#define RPI_HAL_SPI_H_

/* Config --------------------------------------------------------------------*/

/* Includes ------------------------------------------------------------------*/
#include <stdint.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <linux/types.h>
#include <linux/spi/spidev.h>

/* Typedef -------------------------------------------------------------------*/
typedef struct
{
  int fd;
  const char* dev;
  struct spi_ioc_transfer tr;
} SPI_Handle_TypeDef;

/* Define --------------------------------------------------------------------*/
#define DEV_SPI0 "/dev/spidev0.0"

/* Macro ---------------------------------------------------------------------*/

/* Public variables ----------------------------------------------------------*/

/* Public function prototypes ------------------------------------------------*/
/**
 * @brief
 * @param[in] hpsi : 
 * @return 
 */
int HAL_SPI_Init(SPI_Handle_TypeDef* hspi);

/**
 * @brief
 * @param[in] hpsi : 
 * @return 
 */
int HAL_SPI_Deinit(SPI_Handle_TypeDef* hspi);

/**
 * @brief
 * @param[in] hpsi : 
 * @param[in] tx : 
 * @param[in] len : 
 * @return 
 */
int HAL_SPI_Transmit(SPI_Handle_TypeDef* hspi, uint8_t* tx, uint32_t len);

/**
 * @brief
 * @param[in] hpsi : 
 * @param[in] tx : 
 * @param[in] rx : 
 * @param[in] len : 
 * @return 
 */
int HAL_SPI_TransmitReceive(SPI_Handle_TypeDef* hspi, uint8_t* tx, uint8_t* rx, uint32_t len);

#endif /* RPI_HAL_SPI_H_ */