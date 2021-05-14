#include <iostream>
#include <boost/asio.hpp>
#include <wiringPi.h>

boost::asio::io_service io_service;
boost::posix_time::milliseconds interval(1000);  // 1 second
boost::asio::deadline_timer timer(io_service, interval);

const int led = 28; //< Red LED: Physical pin 38, BCM GPIO20, and WiringPi pin 28.
bool led_state = 0;

void tick(const boost::system::error_code& /*e*/) {

  //std::cout << led_state << std::endl;

  led_state ^= 0x01;
  digitalWrite(led, led_state);

  // Reschedule the timer for 1 second in the future:
  timer.expires_at(timer.expires_at() + interval);
  // Posts the timer event
  timer.async_wait(tick);
}

int main(void) 
{

  wiringPiSetup();
  pinMode(led, OUTPUT);

  // Schedule the timer for the first time:
  timer.async_wait(tick);
  // Enter IO loop. The timer will fire for the first time 1 second from now:
  io_service.run();
    
  return 0;
}