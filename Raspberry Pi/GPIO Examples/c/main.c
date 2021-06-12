#include "rpi_hal_gpio.h"
#include <stdio.h>

int main(void)
{
  int led = 20;
  int btn = 16;
  
  HAL_GPIO_ExportPin(led);
  HAL_GPIO_ExportPin(btn);
  
  HAL_GPIO_SetPinDirection(led, GPIO_OUTPUT);
  HAL_GPIO_SetPinDirection(btn, GPIO_INPUT);
  
  HAL_GPIO_WritePin(led, GPIO_PIN_SET);
  
  GPIO_PinState led_state = GPIO_PIN_SET;
  GPIO_PinState btn_state, btn_state_prev = GPIO_PIN_RESET;
  
  puts("Press Ctrl+C to exit.");
  
  while(1)
  {
    btn_state = HAL_GPIO_ReadPin(btn);
    if(btn_state == GPIO_PIN_SET && btn_state_prev == GPIO_PIN_RESET)
      HAL_GPIO_WritePin(led, led_state ^= 1);
    btn_state_prev = btn_state;
    HAL_Delay(10);
  }
  
  return 0;
}