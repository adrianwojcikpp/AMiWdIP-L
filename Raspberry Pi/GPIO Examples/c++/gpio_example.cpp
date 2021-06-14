/**
 ******************************************************************************
 * @file    /gpio_examples/cpp/gpio_example.cpp
 * @author  Adrian Wojcik
 * @version V1.0
 * @date    15-Mar-2020
 * @brief   Raspberry Pi GPIO control: C++ with wiringPi lib
 ******************************************************************************
 */

#include <iostream>
#include <future>
#include <thread>
#include <chrono>
#include <wiringPi.h>

int main()
{
	const int led = 28; //< Red LED: Physical pin 38, BCM GPIO20, and WiringPi pin 28.
	const int button = 27; //< Push-button: Physical pin 36, BCM GPIO16, and WiringPi pin 27.
	
	bool btn_state = true, btn_state_last = true;
	bool led_state = false;
	
	std::chrono::milliseconds timeout(100);
	std::future<int> async_getchar = std::async(std::getchar);

	wiringPiSetup();

	pinMode(button, INPUT);
	pinMode(led, OUTPUT);
	
	digitalWrite(led, LOW);
	
	std::cout << "Press ENTER to exit." << std::endl;
	
	while(1) 
	{
		btn_state = (digitalRead(button) == HIGH);

		if( !btn_state && btn_state_last){
			led_state = !led_state;
			digitalWrite(led, led_state);
			std::cout << "LED state: ";
			if(led_state)			
				std::cout<< "ON" << std::endl;
			else
				std::cout << "OFF" << std::endl;
		}

		btn_state_last = btn_state;

		if(async_getchar.wait_for(timeout) == std::future_status::ready)
		{
			async_getchar.get();	
			break;
		}			
	}
    
	return 0;
}