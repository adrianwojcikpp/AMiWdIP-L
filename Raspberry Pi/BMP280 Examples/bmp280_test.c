/*!
 *  @brief Example shows basic application to configure and read the temperature.
 */
 #include <time.h>
// https://man7.org/linux/man-pages/man2/nanosleep.2.html
int nanosleep(const struct timespec *req, struct timespec *rem);

#include <stdio.h>
#include <stdlib.h>

#include "rpi_hal_spi.h" 
#include "rpi_hal_spi_config.h"

#include "bmp280.h"

#define BMP280_SPI_BUFFER_LEN 28
#define BMP280_DATA_INDEX     1

void delay_ms(uint32_t period_ms);
int8_t spi_reg_write(uint8_t cs, uint8_t reg_addr, uint8_t *reg_data, uint16_t length);
int8_t spi_reg_read(uint8_t cs, uint8_t reg_addr, uint8_t *reg_data, uint16_t length);
void print_rslt(const char api_name[], int8_t rslt);

int main(void)
{
    int status = HAL_SPI_Init(&hspi0);
    printf("Initialization status: %d\n", status);
  
    int8_t rslt;
    struct bmp280_dev bmp;
    struct bmp280_config conf;
    struct bmp280_uncomp_data ucomp_data;
    int32_t temp32;
    double temp;

    /* Map the delay function pointer with the function responsible for implementing the delay */
    bmp.delay_ms = delay_ms;

    /* To enable SPI interface: comment the above 4 lines and uncomment the below 4 lines */
    bmp.dev_id = 0;
    bmp.read = spi_reg_read;
    bmp.write = spi_reg_write;
    bmp.intf = BMP280_SPI_INTF;

    rslt = bmp280_init(&bmp);
    print_rslt(" bmp280_init status", rslt);

    /* Always read the current settings before writing, especially when
     * all the configuration is not modified
     */
    rslt = bmp280_get_config(&conf, &bmp);
    print_rslt(" bmp280_get_config status", rslt);
    printf("Result: %d\n", rslt);
    /* configuring the temperature oversampling, filter coefficient and output data rate */
    /* Overwrite the desired settings */
    conf.filter = BMP280_FILTER_COEFF_2;

    /* Temperature oversampling set at 4x */
    conf.os_temp = BMP280_OS_4X;

    /* Pressure over sampling none (disabling pressure measurement) */
    conf.os_pres = BMP280_OS_NONE;

    /* Setting the output data rate as 1HZ(1000ms) */
    conf.odr = BMP280_ODR_1000_MS;
    rslt = bmp280_set_config(&conf, &bmp);
    print_rslt(" bmp280_set_config status", rslt);
    printf("Result: %d\n", rslt);
    /* Always set the power mode after setting the configuration */
    rslt = bmp280_set_power_mode(BMP280_NORMAL_MODE, &bmp);
    print_rslt(" bmp280_set_power_mode status", rslt);
    printf("Result: %d\n", rslt);
    while (1)
    {
        /* Reading the raw data from sensor */
        rslt = bmp280_get_uncomp_data(&ucomp_data, &bmp);

        /* Getting the 32 bit compensated temperature */
        rslt = bmp280_get_comp_temp_32bit(&temp32, ucomp_data.uncomp_temp, &bmp);

        /* Getting the compensated temperature as floating point value */
        rslt = bmp280_get_comp_temp_double(&temp, ucomp_data.uncomp_temp, &bmp);
        printf("UT: %d, T32: %d, T: %f \r\n", ucomp_data.uncomp_temp, temp32, temp);

        /* Sleep time between measurements = BMP280_ODR_1000_MS */
        bmp.delay_ms(1000);
    }

    return 0;
}

/*!
 *  @brief Function that creates a mandatory delay required in some of the APIs such as "bmg250_soft_reset",
 *      "bmg250_set_foc", "bmg250_perform_self_test"  and so on.
 *
 *  @param[in] period_ms  : the required wait time in milliseconds.
 *  @return void.
 *
 */
void delay_ms(uint32_t period_ms)
{
  nanosleep((const struct timespec[]){{period_ms / 1000, (period_ms % 1000)*1000000L}}, NULL);
}

/*!
 *  @brief Function for writing the sensor's registers through SPI bus.
 *
 *  @param[in] cs           : Chip select to enable the sensor.
 *  @param[in] reg_addr     : Register address.
 *  @param[in] reg_data : Pointer to the data buffer whose data has to be written.
 *  @param[in] length       : No of bytes to write.
 *
 *  @return Status of execution
 *  @retval 0 -> Success
 *  @retval >0 -> Failure Info
 *
 */
int8_t spi_reg_write(uint8_t cs, uint8_t reg_addr, uint8_t *reg_data, uint16_t length)
{

  /* Implement the SPI write routine according to the target machine. */
  uint8_t txarray[BMP280_SPI_BUFFER_LEN];
  uint8_t stringpos;

  txarray[0] = reg_addr;
  for (stringpos = 0; stringpos < length; stringpos++) {
    txarray[stringpos+BMP280_DATA_INDEX] = reg_data[stringpos];
  }
  /* Software slave selection procedure */
  // TODO: single slave!

  /* Data exchange */
  int status = HAL_SPI_Transmit(&hspi0, (uint8_t*)(&txarray), length+1);

  /* Disable all slaves */
  // TODO: single slave!;
    
  return (int8_t)status;
}

/*!
 *  @brief Function for reading the sensor's registers through SPI bus.
 *
 *  @param[in] cs       : Chip select to enable the sensor.
 *  @param[in] reg_addr : Register address.
 *  @param[out] reg_data    : Pointer to the data buffer to store the read data.
 *  @param[in] length   : No of bytes to read.
 *
 *  @return Status of execution
 *  @retval 0 -> Success
 *  @retval >0 -> Failure Info
 *
 */
int8_t spi_reg_read(uint8_t cs, uint8_t reg_addr, uint8_t *reg_data, uint16_t length)
{
  /* Implement the SPI read routine according to the target machine. */
  uint8_t txarray[BMP280_SPI_BUFFER_LEN] = {0,};
  uint8_t rxarray[BMP280_SPI_BUFFER_LEN] = {0,};
  uint8_t stringpos;

  txarray[0] = reg_addr;

  /* Software slave selection procedure */
  // TODO: single slave!;

  /* Data exchange */
  int status = HAL_SPI_TransmitReceive(&hspi0, (uint8_t*)(&txarray), (uint8_t*)(&rxarray), length+1);

  /* Disable all slaves */
  // TODO: single slave!;

  for (stringpos = 0; stringpos < length; stringpos++) {
    *(reg_data + stringpos) = rxarray[stringpos + BMP280_DATA_INDEX];
  }

  return (int8_t)status;
}

/*!
 *  @brief Prints the execution status of the APIs.
 *
 *  @param[in] api_name : name of the API whose execution status has to be printed.
 *  @param[in] rslt     : error code returned by the API whose execution status has to be printed.
 *
 *  @return void.
 */
void print_rslt(const char api_name[], int8_t rslt)
{
    if (rslt != BMP280_OK)
    {
        printf("%s\t", api_name);
        if (rslt == BMP280_E_NULL_PTR)
        {
            printf("Error [%d] : Null pointer error\r\n", rslt);
        }
        else if (rslt == BMP280_E_COMM_FAIL)
        {
            printf("Error [%d] : Bus communication failed\r\n", rslt);
        }
        else if (rslt == BMP280_E_IMPLAUS_TEMP)
        {
            printf("Error [%d] : Invalid Temperature\r\n", rslt);
        }
        else if (rslt == BMP280_E_DEV_NOT_FOUND)
        {
            printf("Error [%d] : Device not found\r\n", rslt);
        }
        else
        {
            /* For more error codes refer "*_defs.h" */
            printf("Error [%d] : Unknown error code\r\n", rslt);
        }
    }
}
