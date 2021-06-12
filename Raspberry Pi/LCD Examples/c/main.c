#include "lcd.h"
#include "lcd_config.h"

int main(int argc, char* argv[])
{
  if(argc > 1)
  {
    char str[LCD_LINE_BUF_LEN+1] = { 0 };
    int len = strlen(argv[1]);
    len = (len < LCD_LINE_BUF_LEN) ? len : LCD_LINE_BUF_LEN;
    memcpy(str, argv[1], len);
    LCD_Init(&hlcd1); 
    LCD_printStr(&hlcd1, str);
  }
  if(argc > 2)
  {
    char str[LCD_LINE_BUF_LEN+1] = { 0 };
    int len = strlen(argv[2]);
    len = (len < LCD_LINE_BUF_LEN) ? len : LCD_LINE_BUF_LEN;
    memcpy(str, argv[2], len);
    LCD_SetCursor(&hlcd1, 1, 0);
    LCD_printStr(&hlcd1, str);
  }

  return 0;
}