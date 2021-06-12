/**
  ******************************************************************************
  * @file    rpi_hal_i2c.cpp
  * @author  AW
  * @version V2.0
  * @date    27-Mar-2021
  * @brief   Simple hardware abstraction layer for Raspberry Pi I2C bus
  *
  ******************************************************************************
  */
  
#ifndef RPI_HAL_I2C_H_
#define RPI_HAL_I2C_H_

/* Config --------------------------------------------------------------------*/

/* Includes ------------------------------------------------------------------*/
#include <cstdint>
#include <fcntl.h>
#include <unistd.h>
#include <linux/i2c-dev.h>
#include <sys/ioctl.h>

/* Typedef -------------------------------------------------------------------*/
typedef struct {
  int fd;
  const char* dev;
} I2C_Handle_TypeDef;

/* Define --------------------------------------------------------------------*/
#define DEV_I2C0 "/dev/i2c-0"
#define DEV_I2C1 "/dev/i2c-1"

/* Macro ---------------------------------------------------------------------*/

/* Public variables ----------------------------------------------------------*/

/* Public function prototypes ------------------------------------------------*/
/**
 * @brief Adapter /dev/i2c-x initialization 
 * @param[in,out] hi2c I2C handler
 */
void I2C_Init(I2C_Handle_TypeDef* hi2c);

/**
 * @brief Adapter /dev/i2c-x deinitialization 
 * @param[in] hi2c I2C handler
 */
void I2C_Deinit(I2C_Handle_TypeDef* hi2c);

/**
 * @brief Adapter /dev/i2c-x slave address selection 
 * @param[in] hi2c    I2C handler
 * @param[in] address Slave device 7-bit address 
 */
void I2C_SetSlaveAddress(I2C_Handle_TypeDef* hi2c, int address);

/**
 * @brief Adapter /dev/i2c-x master data transmission routine 
 * @param[in]  hi2c  I2C handler
 * @param[in]  tx    Data to transmit
 * @param[in]  len   Data length
 */
void I2C_Master_Transmit(I2C_Handle_TypeDef* hi2c, uint8_t* tx, uint32_t len);

/**
 * @brief Adapter /dev/i2c-x master data reception routine 
 * @param[in]  hi2c  I2C handler
 * @param[out] rx    Receive data buffer
 * @param[in]  len   Data length
 */
void I2C_Master_Receive(I2C_Handle_TypeDef* hi2c, uint8_t* rx, uint32_t len);

#endif /* RPI_HAL_I2C_H_ */