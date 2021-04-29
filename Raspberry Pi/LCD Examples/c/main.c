#inlcude "rpi_hal_gpio.h"
#include <unistd.h>

int main(void)
{
  int led = 20;
  int btn = 16;
  
  HAL_GPIO_Export(led);
  HAL_GPIO_Export(btn);
  
  HAL_GPIO_SetDirection(led, GPIO_OUTPUT);
  HAL_GPIO_SetDirection(btn, GPIO_INPUT);
  
  HAL_GPIO_WritePin(led, GPIO_PIN_SET);
  
  GPIO_PinState led_state = GPIO_PIN_SET;
  GPIO_PinState btn_state, btn_state_prev = GPIO_PIN_RESET;
  
  while(1)
  {
    btn_state = GPIO_ReadPin(btn);
    if(btn_state == GPIO_PIN_SET && btn_state_prev == GPIO_PIN_RESET)
      HAL_GPIO_WritePin(led, led_state ^= 1);
    btn_state_prev = btn_state;
    usleep(10000);
  }
  
  return 0;
}